import net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "ensime-sbt"

organization := "org.ensime"

version := "0.1.5-SNAPSHOT"

sbtPlugin := true

scalacOptions in Compile ++= Seq(
  "-encoding", "UTF-8", "-target:jvm-1.6", "-feature", "-deprecation",
  "-Xfatal-warnings",
  "-language:postfixOps", "-language:implicitConversions"
  //"-P:wartremover:only-warn-traverser:org.brianmckenna.wartremover.warts.Unsafe"
  //"-P:wartremover:traverser:org.brianmckenna.wartremover.warts.Unsafe"
)

// WORKAROUND https://github.com/sbt/sbt/issues/1439
def plugin(m: ModuleID) =
  Defaults.sbtPluginExtra(m, "0.13", "2.10") excludeAll ExclusionRule("org.scala-lang")

// we actually depend at runtime on scalariform
libraryDependencies ++= Seq(
  // TODO: when ENSIME itself is ready for a reformat, depend on the recent
  //       scalariform
  plugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")// excludeAll ExclusionRule("org.scalariform")),
//  "com.danieltrinh" %% "scalariform" % "0.1.5"
)

publishMavenStyle := true

licenses := Seq("BSD 3 Clause" -> url("http://opensource.org/licenses/BSD-3-Clause"))

homepage := Some(url("http://github.com/ensime/ensime-server"))

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := false

credentials += Credentials(
  "Sonatype Nexus Repository Manager", "oss.sonatype.org",
  sys.env.get("SONATYPE_USERNAME").getOrElse(""),
  sys.env.get("SONATYPE_PASSWORD").getOrElse("")
)

graphSettings

//scalariformSettings
