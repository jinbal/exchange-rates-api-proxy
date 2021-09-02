package com.jinbal.exchangerates.client

import cats.effect.IO
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.ExchangeRates
import scalacache._
import scalacache.caffeine._
import scalacache.memoization._

import scala.concurrent.duration.DurationInt

class CachingExchangeRateApiClient extends ExchangeRateApiClient {
  implicit val cache: Cache[ExchangeRates] = CaffeineCache[ExchangeRates]

  implicit val mode: Mode[IO] = scalacache.CatsEffect.modes.async

  override def getExchangeRates(baseCurrency: String): IO[ExchangeRates] = memoizeF(Some(60.seconds)) {
    super.getExchangeRates(baseCurrency)
  }

}
