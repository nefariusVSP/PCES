import java.nio.file.Path

import unfiltered.Cookie
import unfiltered.request._
import unfiltered.response._
import unfiltered.Cycle

case class Auth(users: Users) {
  def apply[A,B](intent: Cycle.Intent[A,B]) =
    Cycle.Intent[A,B] {
      case req@BasicAuth(user, pass) if(users.auth(user, pass)) =>
        Cycle.Intent.complete(intent)(req)
      case _ =>
        Unauthorized ~> WWWAuthenticate("""Basic realm="/"""")
    }
}

trait Users {
  def auth(u: String, p: String): Boolean
}

case class App(users: Users) extends
  unfiltered.filter.Plan {
  def intent = Auth(users) {
    case _ => ResponseString("Shhhh!")
  }
}

object JimsAuth extends Users {
  def auth(u: String, p: String) =
    u == "jim" && p == "j@m"
}
object d {
  def main(args: Array[String]) {

    println("Starting Scala Unfiltered web framework demo on Jetty http server...")
    unfiltered.jetty.Http.apply(8080).run()
  }
}