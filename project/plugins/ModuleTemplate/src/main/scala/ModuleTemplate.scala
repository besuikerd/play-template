import sbt._
import sbt.complete.DefaultParsers._
import Keys._
import scalax.io._
import Resource._

object ModuleTemplate extends AutoPlugin{
  final val OUTPUT_PATH = "modules"
  final val INPUT_PATH = "project/plugins/ModuleTemplate/templates/module"

  object autoImport{
    val createModule = inputKey[Unit]("Create a new module")
  }

  override def trigger = allRequirements

  override def globalSettings = super.globalSettings ++ Seq(
    commands += Command("createModule")(_ => Space.+ ~> NotQuoted)(createModule)
  )

  def createModule(state:State, name:String): State = {

    val modulePath = s"$OUTPUT_PATH/$name"
    val moduleFile = file(modulePath)

    if (moduleFile.exists()) {
      state.log.error("module already exists: " + name)
      state.fail
    } else {
      file(modulePath).mkdirs()
      state.log.info(s"creating module ${name}...")
      processFolder(state, file(INPUT_PATH), modulePath, name)
    }
  }

  def processFolder(state:State, folder:File, currentPath:String, moduleName: String): State = {
    for(f <- folder.listFiles()){
      if(f.isFile){
        val fileName = s"$currentPath/${applyTemplate(f.getName, moduleName)}"
        state.log.info(s"copying $currentPath/${f.getName} to $fileName")

        for{
          writer <- fromFile(file(fileName)).writer
          line <- fromFile(f).lines()
        }{
          writer.write(applyTemplate(line, moduleName) + '\n')

        }

        state
      } else{

        val folderPath = s"$currentPath/${f.getName}"
        file(folderPath).mkdir()
        state.log.info(s"making directory $folderPath")
        processFolder(state, f, folderPath, moduleName)
      }
    }
    state
  }

  def processFile(state:State, f:File, moduleName: String): State ={
    state
  }

  def applyTemplate(input:String, replacement:String): String = """\$\{moduleName\}""".r.replaceAllIn(input, replacement)
}