import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys._
import sbt._
import Keys._

object Modules extends Build{
  lazy val modules:Map[String, Project] = (for(f <- file("modules").listFiles() if f.isDirectory) yield {
    val p = (project in f).enablePlugins(PlayScala).settings(
      name := f.getName,
      scalaVersion := "2.11.6",
      routesGenerator := InjectedRoutesGenerator
    )
    (p.id, p)
  }).toMap

//  lazy val modules = Map[String, Project]("template" -> template)
  println(s"modules: $modules")


  def dependAndAggregate(root:Project):Project = modules.foldLeft(root){case (root, (_, project)) => root.dependsOn(project).aggregate(project)}
}