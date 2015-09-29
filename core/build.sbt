lazy val core = Modules.dependAndAggregate((project in file(".")).enablePlugins(PlayScala))

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

routesGenerator := InjectedRoutesGenerator