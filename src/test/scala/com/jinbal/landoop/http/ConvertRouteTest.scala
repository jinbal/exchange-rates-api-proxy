package com.jinbal.landoop.http

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FunSuite, Matchers}

class ConvertRouteTest extends FunSuite
  with ScalatestRouteTest
  with Matchers
  with HttpRoutes {

  test("Should return a response") {
    Post("/api/convert") ~> routes ~> check {
      responseAs[String] shouldBe "OK"
    }
  }
}
