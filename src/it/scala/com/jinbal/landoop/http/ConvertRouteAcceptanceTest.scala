package com.jinbal.landoop.http

import io.restassured.RestAssured._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._

class ConvertRouteAcceptanceTest extends AcceptanceTest {

  test("Convert route should return a response") {
    given()
      .contentType("application/json")
      .body(requestJson("EUR", "GBP", 1.0))
      .when()
      .post("http://localhost:8080/api/convert")
      .prettyPeek()
      .Then()
      .statusCode(200)
      .body("original", is(1.0f))
  }

  def requestJson(fromCurrency: String, toCurrency: String, amount: Double) = {
    s"""
       |{
       | "fromCurrency": "$fromCurrency",
       | "toCurrency" : "$toCurrency",
       | "amount" : $amount
       |}
      """.stripMargin
  }
}


