package com.jinbal.landoop.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.jinbal.landoop.domain.ConvertCurrencyResult
import com.jinbal.landoop.fxrates.CachedExchangeRateService
import org.scalatest.mockito.MockitoSugar._
import org.scalatest.{FunSuite, Matchers}

class ConvertRouteTest extends FunSuite
  with ScalatestRouteTest
  with Matchers
  with HttpRoutes {
  val cachedExchangeRateService: CachedExchangeRateService = mock[CachedExchangeRateService]

  test("Should return a response") {
    Post("/api/convert") ~> routes ~> check {
      responseAs[ConvertCurrencyResult] shouldBe ConvertCurrencyResult(0, 0, 0)
    }
  }
}
