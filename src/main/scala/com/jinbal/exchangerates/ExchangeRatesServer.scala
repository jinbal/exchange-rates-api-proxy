package com.jinbal.exchangerates

import cats.effect.{ConcurrentEffect, ExitCode, IO, Timer}
import com.jinbal.exchangerates.client.CachingExchangeRateApiClient
import com.jinbal.exchangerates.conversion.CurrencyConverter
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.global

object ExchangeRatesServer {

  def create()(implicit concurrentEffect: ConcurrentEffect[IO], timer: Timer[IO]): IO[ExitCode] = {
    val routes = ExchangeRatesRoutes.currencyConversionRoutes(
      CurrencyConverter(new CachingExchangeRateApiClient())
    )
    BlazeServerBuilder[IO](global)
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.orNotFound)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
