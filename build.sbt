name := "ImageClassifier"

version := "1.0"

scalaVersion := "2.12.8"

// https://mvnrepository.com/artifact/com.sksamuel.scrimage/scrimage-core

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.8"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-filters" % "2.1.8"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-io-extra" % "2.1.8"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12" % Test

mainClass in (Compile,run) := Some("Classifier")
