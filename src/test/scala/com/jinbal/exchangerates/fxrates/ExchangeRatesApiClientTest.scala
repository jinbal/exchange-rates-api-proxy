package com.jinbal.exchangerates.fxrates

import java.time.LocalDate

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.jinbal.exchangerates.domain.ExchangeRateApiException
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import org.scalatest.{FunSuite, Matchers}

class ExchangeRatesApiClientTest extends FunSuite
  with ScalaFutures
  with Matchers {
  implicit override val patienceConfig = PatienceConfig(timeout = scaled(Span(10, Seconds)), interval = scaled(Span(1, Seconds)))

  val wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
  wireMockServer.start()
  val wireMockPort = wireMockServer.port()
  WireMock.configureFor(wireMockPort)

  private val fxRatesURl = s"http://localhost:$wireMockPort/latest"
  val underTest = new ExchangeRatesApiClient(fxRatesURl)


  test("Should fetch exchange rates for currency") {
    // Given
    val currencyBase = "EUR"
    val expectedRate = ("MXN", 3.55)
    val expectedDate = LocalDate.now()
    givenThat(get(urlPathMatching("/latest"))
              .withQueryParam("base", equalTo(currencyBase))
              .willReturn(aResponse
                          .withHeader("content-type", "application/json")
                          .withBody(responseJson(currencyBase, expectedRate, expectedDate)))
             )

    // When
    val exchangeRatesF = underTest.fetchRates(currencyBase)

    // Then
    whenReady(exchangeRatesF) { exchangeRates =>
      exchangeRates.base shouldBe currencyBase
      exchangeRates.rates should contain(expectedRate)
      exchangeRates.date shouldBe expectedDate
    }
  }

  test("should fail when rates api fails") {
    // Given
    val currencyBase = "EUR"
    givenThat(get(urlPathMatching("/latest"))
              .withQueryParam("base", equalTo(currencyBase))
              .willReturn(aResponse.withStatus(400)))

    // When
    val exchangeRatesF = underTest.fetchRates(currencyBase)

    // Then
    whenReady(exchangeRatesF.failed) { exception =>
      exception shouldBe a[ExchangeRateApiException]
      exception.getMessage should include("Bad Request")
    }

  }

  private def responseJson(base: String, rate: (String, Double), date: LocalDate): String =
    s"""
       |{
       |    "rates": {
       |        "${rate._1}": ${rate._2}
       |    },
       |    "base": "$base",
       |    "date": "$date"
       |}
       |""".stripMargin

}
