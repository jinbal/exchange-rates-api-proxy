package com.jinbal.landoop.http

import io.restassured.RestAssured._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse

class ConvertRouteAcceptanceTest extends AcceptanceTest {

  test("Convert route should return a response") {
    given()
    .when()
    .post("http://localhost:8080/api/convert")
    .Then()
    .statusCode(200)
  }
}


