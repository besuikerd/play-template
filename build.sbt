name := "template-project"

lazy val core = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(common).aggregate(common)

lazy val common: Project = (project in file("modules/common"))
  .enablePlugins(PlayScala)
  .settings(
    aggregateReverseRoutes := Seq()
  )

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)


sbtPlugin := true

routesGenerator := InjectedRoutesGenerator