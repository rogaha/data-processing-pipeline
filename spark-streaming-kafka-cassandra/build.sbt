
name := "DockerCLIProcessingPipeLine"

version := "0.1"

scalaVersion := "2.10.4"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

// kafka streaming related dependencies
libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-sql"  % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.6.1"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.1"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.11"
libraryDependencies += "com.eaio.uuid" % "uuid" % "3.2"

// geocode related dependencies
libraryDependencies ++= Seq(
      "com.maxmind.geoip2" % "geoip2" % "2.6.0",
      "com.typesafe.akka"   %%  "akka-actor"        % "2.3.6",
      "net.databinder.dispatch" %% "dispatch-core" % "0.11.2",
      "io.spray" %% "spray-json" % "1.2.6",
      "org.specs2" %% "specs2-core" % "3.6.1" % "test"
    )

assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("com.fasterxml.jackson.core.**"       -> "shadedjackson.core.@1").inAll,
  ShadeRule.rename("com.fasterxml.jackson.annotation.**" -> "shadedjackson.annotation.@1").inAll,
  ShadeRule.rename("com.fasterxml.jackson.databind.**"   -> "shadedjackson.databind.@1").inAll
)

mergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.discard
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}
