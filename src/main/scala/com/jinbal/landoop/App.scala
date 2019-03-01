package com.jinbal.landoop

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.jinbal.landoop.http.HttpRoutes

object App extends App with HttpRoutes {
  implicit val system = ActorSystem("CTP-actor-system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher
  val HttpService = Http().bindAndHandle(routes, "0.0.0.0", 8080)

}
