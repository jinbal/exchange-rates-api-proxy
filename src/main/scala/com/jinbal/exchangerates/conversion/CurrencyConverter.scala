package com.jinbal.exchangerates.conversion

import cats.effect.Async
import cats.MonadError
import cats.implicits._
import com.jinbal.exchangerates.client.ExchangeRateApiClient
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.{ConvertCurrency, ConvertCurrencyResult, ExchangeRateApiException}


trait CurrencyConverter[F[_]] {
  def convert(convertCurrency: ConvertCurrency): F[ConvertCurrencyResult]
}

object CurrencyConverter {
  def apply[F[_]: Async](exchangeRateApiClient: ExchangeRateApiClient[F]): CurrencyConverter[F] = new CurrencyConverter[F] {
    def convert(convertCurrency: ConvertCurrency): F[ConvertCurrencyResult] = {
      for {
        rates <- exchangeRateApiClient.getExchangeRates(convertCurrency.fromCurrency)
        rate <- MonadError[F, Throwable].fromOption(rates.rates.get(convertCurrency.toCurrency), new ExchangeRateApiException("to currency not found"))
        converted = convertCurrency.amount * rate
      } yield {
        ConvertCurrencyResult(rate, converted, convertCurrency.amount)
      }
    }
  }
}
