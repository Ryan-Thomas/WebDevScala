name := """scala-web-project"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

pipelineStages := Seq(digest)

libraryDependencies ++= Seq(
    jdbc,
    cache,
    ws,
    "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test,
    "com.softwaremill.macwire" %% "macros" % "2.2.0" % "provided",
    "com.softwaremill.macwire" %% "util" % "2.2.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
