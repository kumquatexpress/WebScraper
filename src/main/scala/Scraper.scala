/**
 * Created by boyan_000 on 4/17/2015.
 */

import java.net.URL

import akka.actor.{Actor, Props}

class Scraper(startUrl: URL) extends Actor with akka.actor.ActorLogging {
  def receive = {
    case Message("GET", data) => log.info(s"Got $data, parsing $startUrl")
      sender ! new Message("RESULT", Map("hello" -> context.toString))
    case Message("EXIT", data) => log.info("Received exit, ending...")
      context.stop(self)
    case _ => log.info("Unknown message")
  }
}

object Scraper {
  def props(startUrl: URL) : Props = Props(new Scraper(startUrl))
}