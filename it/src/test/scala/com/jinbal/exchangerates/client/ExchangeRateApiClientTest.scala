package com.jinbal.exchangerates.client

import cats.effect.IO
import munit.CatsEffectSuite
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

class ExchangeRateApiClientTest extends CatsEffectSuite  {
  given LoggerFactory[IO] = Slf4jFactory.create[IO]
  val underTest  = new ExchangeRateApiClient[IO]

  test("should get rates from remote server") {
    val baseCurrency = "BTC"
    assertIO(underTest.getExchangeRates(baseCurrency).map(_.currency), baseCurrency, "Currency should match")
  }
}
