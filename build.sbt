val Http4sVersion = "1.0.0-M44"
val CirceVersion = "0.14.14"
val MunitVersion = "1.0.4"
val LogbackVersion = "1.5.18"
val MunitCatsEffectVersion = "2.1.0"
val ScalaCacheVersion = "1.0.0-M6"
val Log4CatsVersion = "2.7.0"

lazy val root = (project in file("."))
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
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect" % MunitCatsEffectVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "org.typelevel" %% "log4cats-slf4j" % Log4CatsVersion,
      "com.github.blemale" %% "scaffeine" % "5.3.0"
    ),
    testFrameworks += new TestFramework("munit.Framework"),
    Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat,
    scalacOptions ++= Seq("-Wconf:msg=deprecated:s"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    }
  )

lazy val it = (project in file("it"))
  .dependsOn(root % "compile->compile;test->test")
  .settings(
    organization := "com.jinbal",
    name := "exchange-rates-api-proxy-it",
    scalaVersion := "3.3.6",
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % MunitVersion % Test,
      "org.typelevel" %% "munit-cats-effect" % MunitCatsEffectVersion % Test,
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "io.rest-assured" % "rest-assured" % "5.5.5" % Test,
      "io.rest-assured" % "scala-support" % "5.5.5" % Test,
      "ch.qos.logback" % "logback-classic" % LogbackVersion % Test
    ),
    testFrameworks += new TestFramework("munit.Framework"),
    Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat
  )
