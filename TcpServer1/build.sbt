name := "TcpServer1"

version := "0.1"

scalaVersion := "2.12.8"


libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % "2.5.25",
    "net.liftweb" %% "lift-json" % "3.3.0"
  )
}