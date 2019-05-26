
name := "green-cm"

version := "20190526"

scalaVersion := "2.12.8"

organization := "se.chimps.green"

enablePlugins(PackPlugin)

packMain := Map("start" -> "se.chimps.green.cm.Start")

packGenerateWindowsBatFile := false

resolvers += "se.chimps.green" at "https://yamr.kodiak.se/maven"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.5.22",
	"com.typesafe.akka" %% "akka-cluster" % "2.5.22",
	"com.typesafe.akka" %% "akka-cluster-tools" % "2.5.22",
	"se.chimps.green" %% "green-api" % "20190519"
)