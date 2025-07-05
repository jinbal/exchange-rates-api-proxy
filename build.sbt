val Http4sVersion = "0.23.30"
val CirceVersion = "0.14.10"
val MunitVersion = "1.0.3"
val LogbackVersion = "1.4.14"
val MunitCatsEffectVersion = "2.0.0"
val ScalaCacheVersion = "1.0.0-M6"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    organization := "com.jinbal",
    name := "exchange-rates-api-proxy",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "3.3.6",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % Http4sVersion,
      "org.http4s" %% "http4s-ember-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.scalameta" %% "munit" % MunitVersion % "test,it",
      "org.typelevel" %% "munit-cats-effect" % MunitCatsEffectVersion % "test,it",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test,it",
      "io.rest-assured" % "rest-assured" % "5.5.0" % "test,it",
      "io.rest-assured" % "scala-support" % "5.5.0" % "test,it",
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "com.github.blemale" %% "scaffeine" % "5.3.0"

    ),
    Defaults.itSettings,
    testFrameworks += new TestFramework("munit.Framework"),
    Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    IntegrationTest / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    scalacOptions ++= Seq("-Wconf:msg=deprecated:s"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    }
  )
