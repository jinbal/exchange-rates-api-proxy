package com.jinbal.exchangerates.http

import com.jinbal.exchangerates.App
import org.scalatest.{BeforeAndAfterAll, FunSuite}

trait AcceptanceTest extends FunSuite
  with BeforeAndAfterAll {
  override protected def afterAll() = {
    super.afterAll()
  }

  override protected def beforeAll() = {
    App.main(Array.empty[String])
  }
}
