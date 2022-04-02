import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.11"

  lazy val mockitoScala = "org.mockito" %% "mockito-scala" % "1.16.55" % "test"
  lazy val mockitoScalaTest = "org.mockito" %% "mockito-scala-scalatest" % "1.16.55" % "test"

  val all = Seq(scalaTest, mockitoScala, mockitoScalaTest)
}

