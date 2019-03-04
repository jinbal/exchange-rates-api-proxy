package com.jinbal.landoop.domain

import java.time.format.DateTimeFormatter
import java.time.{LocalDate, LocalDateTime}

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, RootJsonFormat}

trait CustomJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val LocalDateTimeFormat = new RootJsonFormat[LocalDateTime] {
    private val iso_date_time = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    def write(x: LocalDateTime) = JsString(iso_date_time.format(x))

    def read(value: JsValue) = value match {
      case JsString(x) => LocalDateTime.parse(x, iso_date_time)
      case x => throw new RuntimeException(s"Unexpected type ${x.getClass.getName} when trying to parse LocalDateTime")
    }
  }

  implicit val LocalDateFormat = new RootJsonFormat[LocalDate] {
    private val iso_local_date = DateTimeFormatter.ISO_LOCAL_DATE

    def write(x: LocalDate) = JsString(iso_local_date.format(x))

    def read(value: JsValue) = value match {
      case JsString(x) => LocalDate.parse(x, iso_local_date)
      case x => throw new RuntimeException(s"Unexpected type ${x.getClass.getName} when trying to parse LocalDateTime")
    }
  }


}


