package controllers

import java.text.SimpleDateFormat
import java.util.Date

import play.api._
import play.api.mvc._

import scala.concurrent.Future

class Application extends Controller {
    def index = Action.async {
        val date = new Date()
        val dateStr = new SimpleDateFormat().format(date)
        Future.successful { Ok(views.html.index(dateStr)) }
    }
}
