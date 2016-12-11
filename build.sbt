name := "mauth"

organization := "com.vitold"

version := "0.0.1"

scalaVersion := "2.11.8"

bintrayOrganization := Some("vkocherga")
bintrayPackage := "mauth"

lazy val akkaVersion = "2.4.14"
lazy val akkaHttpVersion = "10.0.0"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))


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
