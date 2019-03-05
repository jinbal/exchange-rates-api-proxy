package com.jinbal.landoop.fxrates

import akka.actor.ActorSystem
import akka.http.caching.LfuCache
import akka.http.caching.scaladsl.{Cache, CachingSettings}
import com.jinbal.landoop.domain.{ConvertCurrency, ConvertCurrencyResult, ExchangeRateApiException, ExchangeRates}

import scala.concurrent.Future
import scala.concurrent.duration._

class CachedExchangeRateService(exchangeRatesApiClient: ExchangeRatesApiClient) {
  implicit val system = ActorSystem()
  implicit val executionContext = system.dispatcher

  val defaultCachingSettings = CachingSettings(system)
  val lfuCacheSettings = defaultCachingSettings.lfuCacheSettings
                         .withInitialCapacity(25)
                         .withMaxCapacity(50)
                         .withTimeToLive(60.seconds)
  val cachingSettings = defaultCachingSettings.withLfuCacheSettings(lfuCacheSettings)
  val cache: Cache[String, ExchangeRates] = LfuCache[String, ExchangeRates](cachingSettings)

  def convert(convertCurrency: ConvertCurrency): Future[ConvertCurrencyResult] = {
    val ratesF: Future[ExchangeRates] = cache.getOrLoad(convertCurrency.fromCurrency, currency => exchangeRatesApiClient.fetchRates(currency))

    ratesF.map { exchangeRates =>
      exchangeRates.rates.get(convertCurrency.toCurrency)
    }.flatMap {
      case Some(exRate) =>
        Future.successful(ConvertCurrencyResult(exRate, convertCurrency.amount * exRate, convertCurrency.amount))
      case None =>
        Future.failed(new ExchangeRateApiException(s"no exchange rates for target currency ${convertCurrency.toCurrency} "))
    }
  }
}
