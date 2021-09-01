package com.jinbal.exchangerates

import io.restassured.RestAssured._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse
import org.hamcrest.Matchers._

class ConvertRouteAcceptanceTest extends AcceptanceTest {

  test("Convert route should return a response") {
    given()
      .contentType("application/json")
      .queryParam("fromCurrency", "BTC")
      .queryParam("toCurrency", "USD")
      .queryParam("amount", "1")
      .when()
      .get("http://localhost:8080/convert")
      .prettyPeek()
      .Then()
      .statusCode(200)
      .body("original", is(1.0f))
  }
}


