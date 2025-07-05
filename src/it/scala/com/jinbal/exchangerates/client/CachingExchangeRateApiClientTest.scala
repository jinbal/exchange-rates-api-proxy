package com.jinbal.exchangerates.client

import cats.effect.IO
import munit.CatsEffectSuite

class CachingExchangeRateApiClientTest extends CatsEffectSuite {
  val underTest  = new CachingExchangeRateApiClient[IO]

  test("should get rates from remote server") {
    val baseCurrency = "BTC"
    assertIO(underTest.getExchangeRates(baseCurrency).map(_.currency), baseCurrency)
  }
}
