import sbt._
import Keys._

object Common {
  val coreSettings = Seq(
    crossScalaVersions := Seq(/* TODO "2.11.8", */"2.12.1"),
    scalaVersion := "2.12.1",
    scalacOptions := Seq("-unchecked", "-deprecation"),
    organization := "com.github.angiolep",
    version := "0.1.0",
    description := "Universal Binaru JSON library for Scala/Java8"
  )

  val exampleSettings: Seq[Setting[_]] = coreSettings ++ Seq(
    //    scalacOptions += "-Ymacro-debug-lite",
    publishArtifact := false
    //    libraryDependencies ++= Seq(
    //      "org.scala-lang" % "scala-compiler" % scalaVersion.value
    //    )
  )

}
