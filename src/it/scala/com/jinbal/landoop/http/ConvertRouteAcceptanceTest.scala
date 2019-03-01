package com.jinbal.landoop.http

import io.restassured.RestAssured._
import io.restassured.module.scala.RestAssuredSupport.AddThenToResponse

class ConvertRouteAcceptanceTest extends AcceptanceTest {

  test("AppStatusService should serve the application status") {
    given()
    .when()
    .post("http://localhost:8080/api/convert")
    .Then()
    .statusCode(200)
  }
}


