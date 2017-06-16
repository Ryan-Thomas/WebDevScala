package filters

import akka.stream.Materializer
import play.api.Logger
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.Future

class StatsFilter(implicit val mat: Materializer) extends Filter {
    // The apply method is a curried function, and inside of it you have access to the request
    // header as well as the next action in the chain. Invoking nextFilter(header) transfers control
    // to the next action, and request processing continues as usual.
    override def apply(nextFilter: RequestHeader => Future[Result])
                      (header: RequestHeader): Future[Result] ={
        Logger.info(s"Serving another request: ${header.path}")
        nextFilter(header)
    }
}
