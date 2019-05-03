name := "ImageClassifier"

version := "1.0"

scalaVersion := "2.12.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12" % Test

// PureConfig
libraryDependencies += "com.github.pureconfig" %% "pureconfig" % "0.10.2"

//Typesafe
libraryDependencies += "com.typesafe" % "config" % "1.3.0"

mainClass in (Compile,run) := Some("Classify.Main")
