package com.jinbal.exchangerates.client

import cats.effect.Async
import cats.implicits._
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.ExchangeRates
import com.github.blemale.scaffeine.{Scaffeine, Cache}
import org.typelevel.log4cats.LoggerFactory

import scala.concurrent.duration._

class CachingExchangeRateApiClient[F[_]: Async: LoggerFactory] extends ExchangeRateApiClient[F] {
  private val cache: Cache[String, ExchangeRates] = Scaffeine()
    .recordStats()
    .expireAfterWrite(60.seconds)
    .maximumSize(500)
    .build[String, ExchangeRates]()

  override def getExchangeRates(baseCurrency: String): F[ExchangeRates] = {
    cache.getIfPresent(baseCurrency) match {
      case Some(rates) => Async[F].pure(rates)
      case None =>
        for {
          rates <- super.getExchangeRates(baseCurrency)
          _ = cache.put(baseCurrency, rates)
        } yield rates
    }
  }

}
