package com.jinbal.landoop.http

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

trait ConvertRoute extends FailFastCirceSupport {

  val convertRoute = path("convert") {
    post {
      complete {
        "OK"
      }
    }
  }

}
