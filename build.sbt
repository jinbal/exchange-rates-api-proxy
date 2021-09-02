val Http4sVersion = "0.21.26"
val CirceVersion = "0.13.0"
val MunitVersion = "0.7.27"
val LogbackVersion = "1.2.5"
val MunitCatsEffectVersion = "1.0.5"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    organization := "com.jinbal",
    name := "exchange-rates-api-proxy",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.6",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.scalameta" %% "munit" % MunitVersion % "it,test",
      "org.typelevel" %% "munit-cats-effect-2" % MunitCatsEffectVersion % "it,test",
      "org.scalatest" %% "scalatest" % "3.2.9" % "it,test",
      "io.rest-assured" % "rest-assured" % "4.4.0" % "it,test",
      "io.rest-assured" % "scala-support" % "4.4.0" % "it,test",
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "org.scalameta" %% "svm-subs" % "20.2.0",
      "com.github.cb372" %% "scalacache-caffeine" % "0.28.0",
      "com.github.cb372" %% "scalacache-cats-effect" % "0.28.0"

    ),
    Defaults.itSettings,
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.0" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework")
  )
