lazy val baseSettings: Seq[Setting[_]] = Seq(
  scalaVersion       := "2.12.7",
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions", "-language:existentials",
    "-unchecked",
    "-Yno-adapted-args",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  resolvers += Resolver.sonatypeRepo("releases")
)

lazy val doobie = project.in(file("."))
  .settings(moduleName := "doobie")
  .settings(baseSettings: _*)
  .aggregate(core, slides)
  .dependsOn(core, slides)

lazy val core = project
  .settings(moduleName := "doobie-core")
  .settings(baseSettings: _*)
  .settings(
    libraryDependencies ++= List(
      "co.fs2" %% "fs2-io" % "1.0.0",
      "org.typelevel" %% "cats-core" % "1.5.0",
      "org.typelevel" %% "cats-effect" % "1.0.0",
      "org.tpolecat" %% "doobie-specs2" % "0.6.1-SNAPSHOT",
      "org.postgresql" % "postgresql" % "42.2.5",
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )


lazy val slides = project
  .settings(moduleName := "doobie-slides")
  .settings(baseSettings: _*)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "../docs",
    watchSources ++= (tutSourceDirectory.value ** "*.html").get
  ).dependsOn(core)
  .enablePlugins(TutPlugin)
