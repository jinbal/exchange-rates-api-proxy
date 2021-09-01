package com.jinbal.exchangerates

import cats.effect.IOApp

object Main extends IOApp {
  def run(args: List[String]) =
    ExchangeRatesServer.create()
}
