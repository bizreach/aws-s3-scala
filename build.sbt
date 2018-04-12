name := "aws-s3-scala"
organization := "jp.co.bizreach"
scalaVersion := "2.12.5"
crossScalaVersions := Seq("2.11.12", scalaVersion.value)

libraryDependencies ++= Seq(
  "com.github.seratch" %% "awscala"   % "0.6.3",
  "org.scalatest"      %% "scalatest" % "3.0.5" % "test"
)

scalacOptions := Seq("-deprecation")

