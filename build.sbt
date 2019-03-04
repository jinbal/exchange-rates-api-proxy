
name := "exchange-rates-api-proxy"

version := "0.1"

scalaVersion := "2.12.8"
lazy val root = project.in(file("."))
                .configs(IntegrationTest)
                .settings(Defaults.itSettings: _*)


val IntegrationTest: Configuration = config("it") extend Test


lazy val dependencies =
  Seq(
       "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
       "ch.qos.logback" % "logback-classic" % "1.2.3",
       "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7",
       "com.typesafe.akka" %% "akka-http-core" % "10.1.7",
       "com.typesafe.akka" %% "akka-http-caching" % "10.1.7",
       "de.heikoseeberger" %% "akka-http-circe" % "1.25.2",
     )

lazy val testDependencies =
  Seq(
       "org.scalatest" %% "scalatest" % "3.0.5",
       "org.scalacheck" %% "scalacheck" % "1.13.4",
       "com.typesafe.akka" %% "akka-http-testkit" % "10.1.7",
       "com.github.tomakehurst" % "wiremock" % "2.12.0",
       "org.mockito" % "mockito-all" % "1.10.19",
       "io.rest-assured" % "rest-assured" % "3.3.0",
       "io.rest-assured" % "scala-support" % "3.3.0"
     ) map (_ % "test,it")

libraryDependencies ++= dependencies ++ testDependencies


fork := true