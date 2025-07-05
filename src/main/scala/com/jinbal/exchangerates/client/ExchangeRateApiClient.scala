package com.jinbal.exchangerates.client

import cats.effect.Async
import cats.implicits._
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.{ExchangeRates, ExchangeRatesData}
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.implicits._
import org.typelevel.log4cats.LoggerFactory

class ExchangeRateApiClient[F[_]: Async: LoggerFactory](apiUrl: Uri = uri"https://api.coinbase.com/v2/exchange-rates") {
  def getExchangeRates(baseCurrency: String): F[ExchangeRates] = {
    EmberClientBuilder.default[F].build.use { client =>
      val url = apiUrl.withQueryParam("currency", baseCurrency)
      println(url)
      client.expect[ExchangeRatesData](url).map(_.data)
    }
  }
}
