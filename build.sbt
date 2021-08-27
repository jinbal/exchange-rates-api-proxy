
name := "exchange-rates-api-proxy"

version := "0.1"

scalaVersion := "2.13.6"
lazy val root = project.in(file("."))
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)


val IntegrationTest: Configuration = config("it") extend Test


lazy val dependencies =
  Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
  )

lazy val testDependencies =
  Seq(
    "org.scalatest" %% "scalatest" % "3.2.9",
    "org.scalacheck" %% "scalacheck" % "1.14.1",
    "com.github.tomakehurst" % "wiremock" % "2.12.0",
    "org.mockito" % "mockito-all" % "1.10.19",
    "io.rest-assured" % "rest-assured" % "3.3.0",
    "io.rest-assured" % "scala-support" % "3.3.0"
  ) map (_ % "test,it")

libraryDependencies ++= dependencies ++ testDependencies

fork := true

assembly / assemblyOption := (assembly  / assemblyOption).value.withIncludeBin(true )
assembly / test := {}
assembly / assemblyMergeStrategy := {
  //  case PathList("META-INF", "*") => MergeStrategy.discard
  //  case PathList("META-INF", "io.netty.versions.properties") => MergeStrategy.discard
  //  case PathList("org", "apache", "spark", "unused", xs@_*) => MergeStrategy.discard
  //  case PathList("org", "apache", xs@_*) => MergeStrategy.last
  //  case PathList("org", "slf4j", xs@_*) => MergeStrategy.last
  //  case PathList("com", "datastax", "driver", "core", "Driver.properties") => MergeStrategy.first
  //  case PathList("com", "esotericsoftware", xs@_*) => MergeStrategy.last
  //  case PathList("com", "google", "common", xs@_*) => MergeStrategy.last
  //  case PathList("javax", "xml", xs@_*) => MergeStrategy.last
  //  case PathList("org", "joda", "time", xs@_*) => MergeStrategy.first
  //  case PathList("plugin.properties") => MergeStrategy.discard
  //  case PathList(ps@_*) if ps.last.contains("BuildInfo") => MergeStrategy.first

  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}
