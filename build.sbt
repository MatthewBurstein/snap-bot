ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

resolvers ++= Seq(
  "Confluent" at "https://packages.confluent.io/maven/",
  "jitpack" at "https://jitpack.io"
)

lazy val root = (project in file("."))
  .settings(
    name := "snap-bot",
    libraryDependencies ++= Dependencies.all
  )

