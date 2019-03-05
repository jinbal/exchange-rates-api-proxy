package com.jinbal.landoop.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.jinbal.landoop.domain._
import com.jinbal.landoop.fxrates.CachedExchangeRateService
import org.mockito.BDDMockito._
import org.scalatest.mockito.MockitoSugar._
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent.Future

class ConvertRouteTest extends FunSuite
  with ScalatestRouteTest
  with Matchers
  with HttpRoutes {
  val cachedExchangeRateService: CachedExchangeRateService = mock[CachedExchangeRateService]

  test("Should return a convert result") {
    // Given
    val convertRequest = ConvertCurrency("EUR", "GBP", 2.0)
    val expectedResult = ConvertCurrencyResult(1, 2, 3)
    given(cachedExchangeRateService.convert(convertRequest)).willReturn(Future.successful(expectedResult))

    Post("/api/convert", convertRequest) ~> routes ~> check {
      responseAs[ConvertCurrencyResult] shouldBe expectedResult
    }
  }
}
