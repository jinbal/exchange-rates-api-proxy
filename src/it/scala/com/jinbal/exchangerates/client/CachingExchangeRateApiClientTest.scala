package com.jinbal.exchangerates.client

import munit.CatsEffectSuite

class CachingExchangeRateApiClientTest extends CatsEffectSuite {
  val underTest  = new CachingExchangeRateApiClient

  test("should get rates from remote server") {
    val baseCurrency = "BTC"
    val rates = underTest.getExchangeRates(baseCurrency).unsafeRunSync()
    println(rates)
    assert(rates.currency == baseCurrency)
  }
}
