name := "spark-luoli"

version := "1.0"

scalaVersion := "2.12.10"

val versions = new {
  val hoodie = "0.4.5"
  val spark = "3.0.1"
  val hbase = "1.4.9"
  val phoenix = "4.14.0-HBase-1.4"
}

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % versions.spark % "provided",
  "org.apache.spark" %% "spark-yarn" % versions.spark % "provided",
  "org.apache.spark" %% "spark-sql" % versions.spark % "provided",
  "org.apache.spark" %% "spark-sql-kafka-0-10" % versions.spark,
  "org.apache.spark" %% "spark-streaming" % versions.spark % "provided",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % versions.spark,
  "org.apache.spark" %% "spark-hive" % versions.spark % "provided",
  "org.apache.hadoop" % "hadoop-client" % "2.9.2",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case PathList("org", "mortbay", xs @ _*) => MergeStrategy.first
  case x => MergeStrategy.first
}
