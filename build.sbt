ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"


lazy val root = (project in file("."))
  .settings(
    name := "week1",
    libraryDependencies ++= Seq(
      "com.github.tototoshi" %% "scala-csv" % "1.3.10",
      "io.circe" %% "circe-core" % "0.14.5",
      "io.circe" %% "circe-generic" % "0.14.5",
      "io.circe" %% "circe-parser" % "0.14.5",
      "com.opencsv" % "opencsv" % "5.7.1",
      "org.scalaj" %% "scalaj-http" % "2.4.2",
      "com.lihaoyi" %% "requests" % "0.8.0"
    ),
    resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
  )