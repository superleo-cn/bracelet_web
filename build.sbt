name := """bracelet"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.12",
  "commons-io" % "commons-io" % "2.2",
  "commons-collections" % "commons-collections" % "3.2.1"
)
