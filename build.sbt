name := "aws-s3-scala"
organization := "jp.co.bizreach"
scalaVersion := "2.12.8"
crossScalaVersions := Seq("2.11.12", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.github.seratch" %% "awscala"   % "0.8.2",
  "org.scalatest"      %% "scalatest" % "3.0.7" % "test"
)

scalacOptions := Seq("-deprecation")

pomExtra := (
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
  </developers>
)

publishArtifact in Test := false
pomIncludeRepository := { _ => false }
publishTo := sonatypePublishTo.value
homepage := Some(url(s"https://github.com/bizreach/aws-s3-scala"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

sonatypeProfileName := organization.value
releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseTagName := (version in ThisBuild).value
releaseCrossBuild := true

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  releaseStepCommand("sonatypeRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
