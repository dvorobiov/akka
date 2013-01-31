/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package docs.routing

import akka.actor.{ Actor, Props, ActorSystem, ActorLogging }
import com.typesafe.config.ConfigFactory
import akka.routing.FromConfig
import akka.routing.RoundRobinRouter
import akka.routing.ConsistentHashingRouter.ConsistentHashable
import akka.testkit.AkkaSpec
import akka.testkit.ImplicitSender

object RouterViaProgramDocSpec {
  case class Message1(nbr: Int)

  class ExampleActor1 extends Actor {
    def receive = {
      case Message1(nbr) ⇒ println(s"Received $nbr in router ${self.path.name}")
    }
  }
}

class RouterViaProgramDocSpec extends AkkaSpec with ImplicitSender {

  import RouterViaProgramDocSpec._

  "demonstrate routees from paths" in {
    //#programmaticRoutingRouteePaths
    val actor1 = system.actorOf(Props[ExampleActor1], "actor1")
    val actor2 = system.actorOf(Props[ExampleActor1], "actor2")
    val actor3 = system.actorOf(Props[ExampleActor1], "actor3")
    val routees = Vector[String]("/user/actor1", "/user/actor2", "/user/actor3")
    val router = system.actorOf(Props().withRouter(
      RoundRobinRouter(routees = routees)))
    //#programmaticRoutingRouteePaths
    1 to 6 foreach { i ⇒ router ! Message1(i) }
  }

}
