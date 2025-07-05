package com.jinbal.exchangerates

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  def run: IO[Unit] =
    ExchangeRatesServer.create().void
}
