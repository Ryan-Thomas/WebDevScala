package controllers

import play.api._
import play.api.mvc._

class Application extends Controller {
    // The action companion object is a DSL-like construct that helps define 
    // controller methods.
    def index = Action {
        // The index() method is a controller method which takes a compiled Scala 
        // HTML template called index and returns it to the browser, also specifying
        // 200 OK as a response code
        Ok(views.html.index())
        // Basically, this just returns the index html template to the browser
    }
}