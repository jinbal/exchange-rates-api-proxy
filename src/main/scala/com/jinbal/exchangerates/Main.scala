package com.jinbal.exchangerates

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    ExchangeRatesServer.stream[IO].compile.drain.as(ExitCode.Success)
}
