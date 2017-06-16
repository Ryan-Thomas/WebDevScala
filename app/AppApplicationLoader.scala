import com.softwaremill.macwire._
import controllers.Assets
import filters.StatsFilter
import play.api._
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.Filter
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
        
        // Because we are using compile-time DI, we need to tell Play to include our filters in
        // its chain. This can be achieved by overriding the httpFilters field from the
        // BuiltInComponents trait.
        // These will add a log message every time the app serves a request
        lazy val statsFilter: Filter = wire[StatsFilter]
        override val httpFilters: Seq[Filter] = Seq(statsFilter)
        
        applicationLifecycle.addStopHook { () => 
            Logger.info("The app is about to stop")
            Future.successful(Unit)
        }
        
        val onStart = {
            Logger.info("The app is about to start")
        }
    }
}
