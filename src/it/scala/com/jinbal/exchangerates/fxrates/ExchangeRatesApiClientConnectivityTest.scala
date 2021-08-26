package com.jinbal.exchangerates.fxrates

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FunSuite, Matchers}

class ExchangeRatesApiClientConnectivityTest extends FunSuite
  with ScalaFutures
  with Matchers {
  implicit override val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  val underTest = new ExchangeRatesApiClient("https://api.exchangeratesapi.io/latest")

  test("should connect to remote exchange rates api and read results") {
    // Given
    val currency = "EUR"

    // When
    val ratesF = underTest.fetchRates(currency)

    whenReady(ratesF) { rates =>
      rates.base shouldBe currency
    }
  }

}
