import sbt._
import sbt.complete.DefaultParsers._

object ModuleTemplate extends Build{
  lazy val createModule = inputKey[Unit]("Create a new module")
  final val OUTPUT_PATH = "modules"
  final val INPUT_PATH = "project/templates/module"



  override def settings = super.settings ++ Seq(
    createModule := {
      val name = (Space.+ ~> NotQuoted).parsed
      val modulePath = s"$OUTPUT_PATH/$name"
      processFolder(file(INPUT_PATH), modulePath, name)
      println(s"creating module ${name}...")
      println("interleaved")
    }
  )

  def processFolder(folder:File, currentPath:String, moduleName: String): Unit = {
    for(f <- folder.listFiles()){
      if(f.isFile){
        val fileName = applyTemplate(f.getName, moduleName)
        println(s"copying $fileName to $currentPath/$fileName")
      } else{

        val folderPath = s"$currentPath/${f.getName}"



        //file(s"$OUTPUT_PATH/$folderPath").mkdir()
        println(s"making directory $folderPath")
        processFolder(f, folderPath, moduleName)
      }
    }
  }

  def processFile(f:File, moduleName: String): Unit ={
  }

  def applyTemplate(input:String, replacement:String): String = """\$\{moduleName\}""".r.replaceAllIn(input, replacement)
}