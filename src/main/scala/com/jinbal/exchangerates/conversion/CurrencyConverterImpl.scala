package com.jinbal.exchangerates.conversion

import cats.effect.IO
import com.jinbal.exchangerates.client.ExchangeRateApiClient
import com.jinbal.exchangerates.domain.ExchangeRatesDomain.{ConvertCurrency, ConvertCurrencyResult, ExchangeRateApiException}


trait CurrencyConverter {
  def convert(convertCurrency: ConvertCurrency): IO[ConvertCurrencyResult]
}

class CurrencyConverterImpl(exchangeRateApiClient: ExchangeRateApiClient) extends CurrencyConverter {
  def convert(convertCurrency: ConvertCurrency): IO[ConvertCurrencyResult] = {
    for {
      rates <- exchangeRateApiClient.getExchangeRates(convertCurrency.fromCurrency)
      rate <- IO.fromOption(rates.rates.get(convertCurrency.toCurrency))(new ExchangeRateApiException("to currency not found"))
      converted = convertCurrency.amount * rate
    } yield {
      ConvertCurrencyResult(rate, converted, convertCurrency.amount)
    }
  }

}
