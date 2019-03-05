package com.jinbal.landoop.fxrates

import java.time.LocalDate

import com.jinbal.landoop.domain._
import org.mockito.BDDMockito._
import org.mockito.Mockito.{verify,times}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar._
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.Future

class CachedExchangeRateServiceTest extends FunSuite
  with ScalaFutures
  with Matchers {
  implicit override val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))


  test("should perform remote lookup when cache misses") {
    // Given
    val mockExchangeRateApiClient = mock[ExchangeRatesApiClient]
    val underTest = new CachedExchangeRateService(mockExchangeRateApiClient)
    val rate = 2.0
    val conversionRequest = ConvertCurrency("EUR", "GBP", 1.0)
    val expectedConversionAmount = conversionRequest.amount * rate
    val exchangeRates = ExchangeRates(conversionRequest.fromCurrency, LocalDate.now(), Map((conversionRequest.toCurrency, rate)))
    given(mockExchangeRateApiClient.fetchRates(conversionRequest.fromCurrency))
      .willReturn(Future.successful(exchangeRates))

    // When
    val conversion: Future[ConvertCurrencyResult] = underTest.convert(conversionRequest)

    // Then
    whenReady(conversion) { conversion =>
      conversion.amount shouldBe expectedConversionAmount
      conversion.exchange shouldBe rate
      conversion.original shouldBe 1.0
    }
  }

  test("should fail when cache does not contain target currency") {
    // Given
    val mockExchangeRateApiClient = mock[ExchangeRatesApiClient]
    val underTest = new CachedExchangeRateService(mockExchangeRateApiClient)
    val conversionRequest = ConvertCurrency("EUR", "GBP", 1.0)
    val exchangeRates = ExchangeRates(conversionRequest.fromCurrency, LocalDate.now(), Map(("RAND", 2.0)))
    given(mockExchangeRateApiClient.fetchRates(conversionRequest.fromCurrency))
      .willReturn(Future.successful(exchangeRates))

    // When
    val conversion: Future[ConvertCurrencyResult] = underTest.convert(conversionRequest)

    // Then
    whenReady(conversion.failed) { ex =>
      ex shouldBe a[ExchangeRateApiException]
    }
  }

  test("Should not make remote lookup when data cache is valid") {
    // Given
    val mockExchangeRateApiClient = mock[ExchangeRatesApiClient]
    val underTest = new CachedExchangeRateService(mockExchangeRateApiClient)
    val rate = 2.0
    val conversionRequest = ConvertCurrency("EUR", "GBP", 1.0)
    val expectedConversionAmount = conversionRequest.amount * rate
    val exchangeRates = ExchangeRates(conversionRequest.fromCurrency, LocalDate.now(), Map((conversionRequest.toCurrency, rate)))
    given(mockExchangeRateApiClient.fetchRates(conversionRequest.fromCurrency))
      .willReturn(Future.successful(exchangeRates))

    // When
    val conversion: Future[ConvertCurrencyResult] = underTest.convert(conversionRequest)

    // Then
    whenReady(conversion) { conversion =>
      conversion.amount shouldBe expectedConversionAmount
      conversion.exchange shouldBe rate
      conversion.original shouldBe conversionRequest.amount
    }

    val conversion2 = underTest.convert(conversionRequest)
    whenReady(conversion2) { conversion2 =>
      conversion2.amount shouldBe expectedConversionAmount
      conversion2.exchange shouldBe rate
      conversion2.original shouldBe conversionRequest.amount
      verify(mockExchangeRateApiClient, times(1)).fetchRates(conversionRequest.fromCurrency)
    }
  }


}
