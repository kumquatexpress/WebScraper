import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import java.net.URL

/**
 * Created by boyan_000 on 4/17/2015.
 */
object WebScraper extends App {
  import ExecutionContext.Implicits.global

  val system = ActorSystem("WebScraper")
  val urls = List(new URL("http://google.com"), new URL("http://yahoo.com"), new URL("http://baidu.com"))

  system.scheduler.schedule(0 seconds, 30 seconds){
    system.actorOf(Manager.props(urls)) ! StartManager
  }
}
