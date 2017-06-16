import _root_.sbtassembly.AssemblyPlugin.autoImport._
import _root_.sbtassembly.PathList

name := "play-spark-base"
version := "1.0"
scalaVersion := "2.11.8"
startYear := Some(2017)

publishMavenStyle := true

// To local publish
publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

publishArtifact in Test := false

resolvers ++= {
  Seq(
    Resolver.sonatypeRepo("public"),
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  )
}

libraryDependencies+= "org.apache.spark" %% "spark-core"  % "1.6.0" //% "provided,test" excludeAll (ExclusionRule(organization = "org.slf4j")) exclude(org="com.google.guava",name="guava") // exclude("org.apache.hadoop", "hadoop-client")
libraryDependencies+= "org.apache.spark" %% "spark-mllib" % "1.6.0" //% "provided,test"  excludeAll (ExclusionRule(organization = "org.slf4j")) exclude(org="com.google.guava",name="guava")
libraryDependencies+= "org.apache.spark" %% "spark-hive"  % "1.6.0" //% "provided,test"  excludeAll (ExclusionRule(organization = "org.slf4j")) exclude(org="com.google.guava",name="guava")

libraryDependencies ++= {
  Seq(
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",// excludeAll (ExclusionRule(organization = "org.slf4j")),
    "joda-time" % "joda-time" % "2.9.4",
    "org.joda" % "joda-convert" % "1.8",
    "org.slf4j" % "slf4j-api" % "1.7.10",
    "org.apache.logging.log4j"% "log4j-core" % "2.6",
    "com.novocode" % "junit-interface" % "0.11" % "test->default",
    "de.javakaffee" % "kryo-serializers" % "0.38",
    "com.esotericsoftware" % "kryo" % "4.0.0",
    "com.typesafe" % "config" % "1.2.1",
    "com.github.scopt" %% "scopt" % "3.5.0",
    "com.github.nscala-time" %% "nscala-time" % "2.16.0"
  )
}

assemblyMergeStrategy in assembly :={
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.startsWith("META-INF") => MergeStrategy.discard
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
  case PathList("org", "apache", xs@_*) => MergeStrategy.first
  case PathList("org", "jboss", xs@_*) => MergeStrategy.first
  case PathList("org", "slf4j", xs@_*) => MergeStrategy.first
  case "about.html" => MergeStrategy.rename
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}