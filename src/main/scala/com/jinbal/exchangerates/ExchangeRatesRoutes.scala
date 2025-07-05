package com.jinbal.exchangerates

import cats.effect.Async
import com.jinbal.exchangerates.conversion.CurrencyConverter
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.ConvertCurrency
import io.circe.generic.auto._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.impl.QueryParamDecoderMatcher

object ExchangeRatesRoutes {
  object FromCurrencyQueryParamMatcher extends QueryParamDecoderMatcher[String]("fromCurrency")

  object ToCurrencyQueryParamMatcher extends QueryParamDecoderMatcher[String]("toCurrency")

  object AmountQueryParamMatcher extends QueryParamDecoderMatcher[Double]("amount")

  def currencyConversionRoutes[F[_]: Async](currencyConverter: CurrencyConverter[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "convert"
        :? FromCurrencyQueryParamMatcher(fromCurrency)
        +& ToCurrencyQueryParamMatcher(toCurrency)
        +& AmountQueryParamMatcher(amount) =>
        Ok(currencyConverter.convert(ConvertCurrency(fromCurrency, toCurrency, amount)))
    }
  }

}