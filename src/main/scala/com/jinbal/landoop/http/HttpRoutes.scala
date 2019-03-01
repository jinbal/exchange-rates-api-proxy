package com.jinbal.landoop.http

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, pathPrefix}
import akka.http.scaladsl.server.ExceptionHandler
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

trait HttpRoutes extends FailFastCirceSupport
  with LazyLogging
  with ConvertRoute {

  val routes =
    pathPrefix("api") {
      convertRoute
    }

  implicit def routeExceptionHandler: ExceptionHandler =
    ExceptionHandler {
      case ex: Exception => {
        logger.error(s"Exception occured while processing request: $ex", ex)
        complete(HttpResponse(StatusCodes.InternalServerError))
      }
    }

}
