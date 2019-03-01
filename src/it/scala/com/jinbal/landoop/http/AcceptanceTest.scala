package com.jinbal.landoop.http

import com.jinbal.landoop.App
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
