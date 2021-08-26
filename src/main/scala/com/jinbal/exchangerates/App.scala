package com.jinbal.exchangerates

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.jinbal.exchangerates.fxrates.{CachedExchangeRateService, ExchangeRatesApiClient}
import com.jinbal.exchangerates.http.HttpRoutes

object App extends App with HttpRoutes {
  implicit val system = ActorSystem("actor-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  override val cachedExchangeRateService: CachedExchangeRateService = new CachedExchangeRateService(new ExchangeRatesApiClient("https://api.exchangeratesapi.io/latest"))
  val HttpService = Http().bindAndHandle(routes, "0.0.0.0", 8080)

}
