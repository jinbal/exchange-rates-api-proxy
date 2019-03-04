package com.jinbal.landoop.fxrates

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar._
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FunSuite, Matchers}

class CachedExchangeRateServiceTest extends FunSuite
  with ScalaFutures
  with Matchers {
  implicit override val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  val mockExchangeRateApiClient = mock[ExchangeRatesApiClient]
  val underTest = new CachedExchangeRateService(mockExchangeRateApiClient)


  test("should perform remote lookup when cache misses") {
    pending
  }

  test("should return from cache when cache hits and not expired") {
    pending
  }

  test("should perform remote lookup when cache expired") {
    pending
  }

}
