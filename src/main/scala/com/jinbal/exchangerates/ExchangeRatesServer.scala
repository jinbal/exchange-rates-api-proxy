package com.jinbal.exchangerates

import cats.effect.{ExitCode, IO}
import com.jinbal.exchangerates.client.CachingExchangeRateApiClient
import com.jinbal.exchangerates.conversion.CurrencyConverter
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.ember.server.EmberServerBuilder
import com.comcast.ip4s.*
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory

object ExchangeRatesServer {

  def create(): IO[ExitCode] = {
    given LoggerFactory[IO] = Slf4jFactory.create[IO]
    val routes = ExchangeRatesRoutes.currencyConversionRoutes[IO](
      CurrencyConverter[IO](new CachingExchangeRateApiClient[IO])
    )
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(routes.orNotFound)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
  }
}
