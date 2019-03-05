package com.jinbal.landoop.http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import com.jinbal.landoop.domain._
import com.jinbal.landoop.fxrates.CachedExchangeRateService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import spray.json.DefaultJsonProtocol._

trait ConvertRoute extends FailFastCirceSupport with SprayJsonSupport {
  val cachedExchangeRateService: CachedExchangeRateService

  implicit val convertResponseFormat = jsonFormat3(ConvertCurrencyResult)

  val convertRoute = path("convert") {
    post {
      complete {
        ConvertCurrencyResult(0, 0, 0)
      }
    }
  }

}
