package com.jinbal.landoop.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.jinbal.landoop.domain.ConvertResponse
import org.scalatest.{FunSuite, Matchers}

class ConvertRouteTest extends FunSuite
  with ScalatestRouteTest
  with Matchers
  with HttpRoutes {

  test("Should return a response") {
    Post("/api/convert") ~> routes ~> check {
      responseAs[ConvertResponse] shouldBe ConvertResponse(0, 0, 0)
    }
  }
}
