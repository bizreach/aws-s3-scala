name := "aws-s3-scala"
organization := "jp.co.bizreach"
scalaVersion := "2.12.6"
crossScalaVersions := Seq("2.11.12", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.github.seratch" %% "awscala"   % "0.7.1",
  "org.scalatest"      %% "scalatest" % "3.0.5" % "test"
)

scalacOptions := Seq("-deprecation")

