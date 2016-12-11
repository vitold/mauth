name := "mauth"

lazy val buildNumber = sys.props.getOrElse("build.number", default = "0")

lazy val revision = sys.props.getOrElse("build.vcs.number", default = "0")

version := buildNumber + "." + revision

scalaVersion := "2.11.8"

lazy val akkaVersion = "2.4.14"
lazy val akkaHttpVersion = "10.0.0"


lazy val runtimeScopeDependencies = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,
  "com.github.t3hnar" %% "scala-bcrypt" % "2.6",
  "org.scaldi" %% "scaldi-akka" % "0.5.7",
  "com.livestream" %% "scredis" % "2.0.6",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.json4s" %% "json4s-jackson" % "3.4.0",
  "org.json4s" %% "json4s-core" % "3.4.0"
)

lazy val testScopeDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion
)

libraryDependencies ++= runtimeScopeDependencies ++ testScopeDependencies
