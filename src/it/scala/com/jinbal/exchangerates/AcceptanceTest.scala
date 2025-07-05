package com.jinbal.exchangerates

import cats.effect.unsafe.implicits.global
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

trait AcceptanceTest extends AnyFunSuite
  with BeforeAndAfterAll {

  override protected def afterAll() = {
    super.afterAll()
  }

  override protected def beforeAll() = {
    ExchangeRatesServer.create().unsafeRunAndForget()
  }
}
