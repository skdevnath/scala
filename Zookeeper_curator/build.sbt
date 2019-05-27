name := "Zookeeper_curator"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "com.loopfor.zookeeper" % "zookeeper-client_2.12" % "1.4"

// https://mvnrepository.com/artifact/org.apache.curator/apache-curator
libraryDependencies += "org.apache.curator" % "curator-framework" % "4.0.1"

libraryDependencies += "org.apache.curator" % "curator-recipes" % "4.0.1"

libraryDependencies += "org.apache.curator" % "curator-x-async" % "4.0.1"

// For json4s
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.5.3"
libraryDependencies += "com.beachape" %% "enumeratum-json4s" % "1.5.13"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.5.10"

