import com.softwaremill.macwire._
import controllers.Assets
import play.api._
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.routing.Router.Routes
import services.{SunService, WeatherService}

import scala.concurrent.Future

class AppApplicationLoader extends ApplicationLoader {
    override def load(context: ApplicationLoader.Context): Application = {
        LoggerConfigurator(context.environment.classLoader).foreach { configurator =>
            configurator.configure(context.environment)
        }
        (new BuiltInComponentsFromContext(context) with AppComponents).application
    }
    
    trait AppComponents extends BuiltInComponents with AhcWSComponents {
        lazy val assets: Assets = wire[Assets]
        lazy val prefix: String = "/"
        lazy val router: Routes = wire[Routes]
        lazy val applicationController: Application = wire[Application]
        // wsClient is defined in the AhcWSComponents trait
        lazy val sunService: SunService = wire[SunService]
        lazy val weatherService: WeatherService = wire[WeatherService]
        applicationLifecycle.addStopHook { () => 
            Logger.info("The app is about to stop")
            Future.successful(Unit)
        }
        
        val onStart = {
            Logger.info("The app is about to start")
        }
    }
}
