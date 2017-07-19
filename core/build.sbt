import Dependencies._

name := "ubjson"

libraryDependencies ++= Seq (
  // none
) ++ Seq(
  scalatest
).map(_ % Test)

parallelExecution in Test := true

publishMavenStyle := true

isSnapshot := false

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}


publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("Apache 2" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))

homepage := Some(url("http://angiolep.github.io/projects/ubjson/index.html"))

pomExtra :=
  <scm>
    <url>git://github.com/angiolep/akka-wamp.git</url>
    <connection>scm:git:git@github.com:angiolep/scala-ubjson.git</connection>
  </scm>
    <developers>
      <developer>
        <name>Paolo Angioletti</name>
        <email>paolo.angioletti@gmail.com</email>
        <url>http://angiolep.github.io</url>
      </developer>
    </developers>



apiURL := Some(url("http://angiolep.github.io/projects/ubjson/api/index.html"))

credentials += Credentials(Path.userHome / ".ivy2" / "sonatype")
