package controllers.${moduleName}
import play.api.mvc._

class Application extends Controller{
  def index = Action{ implicit req =>
    Ok("index")
  }
}