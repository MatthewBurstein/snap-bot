import sbt._

object Dependencies {
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "3.4-389-3862cf0"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11"
  lazy val mockitoScala = "org.mockito" %% "mockito-scala" % "1.16.55" % "test"
  lazy val mockitoScalaTest = "org.mockito" %% "mockito-scala-scalatest" % "1.16.55" % "test"


  val all = Seq(catsEffect, scalaTest, mockitoScala, mockitoScalaTest)
}

