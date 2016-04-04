name := "dsl-project-2016"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "javax.mail" % "mail" % "1.4.1"

mainClass in (Compile, run) := Some("example.SendMailExample")