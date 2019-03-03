package com.jinbal.landoop.fxrates

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.jinbal.landoop.domain.{CustomJsonSupport, ExchangeRates}

import scala.concurrent.Future

class ExchangeRatesApiClient(fxRatesUrl: String) extends CustomJsonSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  implicit val exchangeRatesFormat = jsonFormat3(ExchangeRates)

  def fetchRates(currency: String): Future[ExchangeRates] = {
    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest()
                           .withUri(Uri(fxRatesUrl)
                                    .withQuery(Query(("base" -> currency)))
                                   ))

    responseFuture.flatMap { response =>
      if (response.status.isSuccess()) {
        Unmarshal(response.entity).to[ExchangeRates]
      } else {
        Future.failed(new RuntimeException(s"exchange rate api request failed: ${response.status.value} "))
      }
    }
  }

}
