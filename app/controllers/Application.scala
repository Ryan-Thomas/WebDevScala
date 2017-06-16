package controllers

import play.api.mvc._
import services.{SunService, WeatherService}

// you need to import this or you will get a compile error stating "Cannot find an 
// implicit ExecutionContext. You might pass or import 
// scala.concurrent.ExecutionContext.Implicits.global"
// That's actually a really helpful error message
import scala.concurrent.ExecutionContext.Implicits.global

class Application(sunService: SunService,
                  weatherService: WeatherService) extends Controller {
    
    def index = Action.async {
        val lat = -33.8830
        val lon = 151.2167
        val sunInfoF = sunService.getSunInfo(lat, lon)
        val temperatureF = weatherService.getTemperature(lat, lon)
        for {
            sunInfo <- sunInfoF
            temperature <- temperatureF
        } yield {
            Ok(views.html.index(sunInfo, temperature))
        }
    }
}
