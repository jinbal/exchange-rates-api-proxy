package com.jinbal.exchangerates

import cats.effect.{ContextShift, IO, Timer}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funsuite.AnyFunSuite

import scala.concurrent.ExecutionContext.global

trait AcceptanceTest extends AnyFunSuite
  with BeforeAndAfterAll {
  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  override protected def afterAll() = {
    super.afterAll()
  }

  override protected def beforeAll() = {
    ExchangeRatesServer.create().unsafeRunAsyncAndForget()
  }
}
