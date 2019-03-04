package com.jinbal.landoop.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.jinbal.landoop.domain.ConvertCurrencyResult
import org.scalatest.{FunSuite, Matchers}

class ConvertRouteTest extends FunSuite
  with ScalatestRouteTest
  with Matchers
  with HttpRoutes {

  test("Should return a response") {
    Post("/api/convert") ~> routes ~> check {
      responseAs[ConvertCurrencyResult] shouldBe ConvertCurrencyResult(0, 0, 0)
    }
  }
}
