name := "aws-s3-scala"

organization := "jp.co.bizreach"

version := "0.0.11-SNAPSHOT"

scalaVersion := "2.12.0"

crossScalaVersions := Seq("2.11.8", "2.12.0")

libraryDependencies ++= Seq(
  "com.github.seratch" %% "awscala"   % "0.5.9",
  "org.scalatest"      %% "scalatest" % "3.0.0" % "test"
)

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

scalacOptions := Seq("-deprecation")

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/bizreach/aws-s3-scala</url>
    <licenses>
      <license>
        <name>The Apache Software License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <url>https://github.com/bizreach/aws-s3-scala</url>
      <connection>scm:git:https://github.com/bizreach/aws-s3-scala.git</connection>
    </scm>
    <developers>
      <developer>
        <id>takezoe</id>
        <name>Naoki Takezoe</name>
        <email>naoki.takezoe_at_bizreach.co.jp</email>
        <timezone>+9</timezone>
      </developer>
      <developer>
        <id>tanacasino</id>
        <name>Tomofumi Tanaka</name>
        <email>tomofumi.tanaka_at_bizreach.co.jp</email>
        <timezone>+9</timezone>
      </developer>
    </developers>)
