
val scalatest = "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

lazy val root = (project in file(".")).
  settings(
    name := "ubjson",
    version := "0.1.0",
    scalaVersion := "2.11.5",
    libraryDependencies ++= Seq(scalatest)
  )

