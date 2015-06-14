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
  "commons-collections" % "commons-collections" % "3.2.1",
  "cn.jpush.api" % "jpush-client" % "3.2.3"
)

TaskKey[Unit]("stop") := {
  val pidFile = target.value / "universal" / "stage" / "RUNNING_PID"
  if (!pidFile.exists) throw new Exception("App not started!")
  val pid = IO.read(pidFile)
  s"kill $pid".!
  println(s"Stopped application with process ID $pid")
}

fork in run := true