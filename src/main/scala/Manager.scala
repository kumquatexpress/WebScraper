/**
 * Created by boyan_000 on 4/17/2015.
 */

import java.net.URL
import akka.actor.{Props, ReceiveTimeout, Actor}
import scala.concurrent.duration._

class Manager(urlList: List[URL]) extends Actor with akka.actor.ActorLogging {
  context.setReceiveTimeout(29 seconds)

  val actorMap = urlList.map(url => context.actorOf(Scraper.props(url)) -> url).toMap

  def receive = {
    case StartManager =>
      actorMap foreach {
        case (actor, url) => actor ! new Message("GET", Map())
      }
    case Message("RESULT", data) =>
      log.info(s"Received $data")
    case ReceiveTimeout =>
      context.stop(self)
      actorMap foreach {
        case (actor, _) => context.stop(actor)
      }
  }
}

object Manager {
  def props(urlList: List[URL]) : Props = Props(new Manager(urlList))
}
