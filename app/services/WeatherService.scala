package services

import play.api.libs.ws.WS
import scala.concurrent.Future
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

class WeatherService {
    def getTemperature(lat: Double, lon: Double): Future[Double] = {
        val weatherResponseF = WS.url(
            "http://api.openweathermap.org/data/2.5/weather?" +
                s"lat=$lat&lon=$lon&units=metric").get()
        weatherResponseF.map { weatherResponse =>
            val weatherJson = weatherResponse.json
            val temperature = (weatherJson \ "main" \ "temp").as[Double]
            temperature
        }
    }
}
