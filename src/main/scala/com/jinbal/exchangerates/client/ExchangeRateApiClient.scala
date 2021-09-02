package com.jinbal.exchangerates.client

import cats.effect.{ContextShift, IO, Timer}
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.{ExchangeRates, ExchangeRatesData}
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe.jsonOf
import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.implicits.http4sLiteralsSyntax

import scala.concurrent.ExecutionContext.global
class ExchangeRateApiClient(apiUrl: Uri = uri"https://api.coinbase.com/v2/exchange-rates") {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)
  implicit val dec = jsonOf[IO, ExchangeRatesData]

  def getExchangeRates(baseCurrency: String): IO[ExchangeRates] = {
    BlazeClientBuilder[IO](global).resource.use { client =>
      val url = apiUrl.withQueryParam("currency", baseCurrency)
      println(url)
      client.expect[ExchangeRatesData](url).map(_.data)
    }
  }
}
