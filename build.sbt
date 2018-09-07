organization := "com.github.biopet"
organizationName := "Biopet"

startYear := Some(2014)

name := "squishbed"
biopetUrlName := "squishbed"

biopetIsTool := true

mainClass in assembly := Some("nl.biopet.tools.squishbed.SquishBed")

developers := List(
  Developer(id = "ffinfo",
            name = "Peter van 't Hof",
            email = "pjrvanthof@gmail.com",
            url = url("https://github.com/ffinfo"))
)

scalaVersion := "2.11.12"

libraryDependencies += "com.github.biopet" %% "tool-utils" % "0.6"
libraryDependencies += "com.github.biopet" %% "tool-test-utils" % "0.3"
libraryDependencies += "com.github.biopet" %% "ngs-utils" % "0.6"
libraryDependencies += "com.github.biopet" %% "common-utils" % "0.8"
