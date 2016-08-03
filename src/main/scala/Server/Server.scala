package com.pces.Server

import unfiltered.request.{Charset => _, _}
import unfiltered.response._
import com.pces.KnowledgeBaseEditor.KnowledgeBaseEditor
import unfiltered.Cycle

object Server {

}

object UnfilteredBasicDemo {


  object Decode {

    import java.net.URLDecoder
    import java.nio.charset.Charset

    trait Extract {
      def charset: Charset

      def unapply(raw: String) =
        scala.util.Try(URLDecoder.decode(raw, charset.name())).toOption
    }

    object utf8 extends Extract {
      val charset = Charset.forName("utf8")
    }

  }

  val handlePath = unfiltered.filter.Planify {

    case Path(Seg("inline_html" :: Nil)) => {
      println("inline")
      Ok ~> PlainTextContent ~> ResponseString(
        "<html>\n          <head>\n            <title>Scala Unfiltered framework basic demo</title>\n            <meta charset=\"UTF-8\"/>\n          </head>\n          <body>\n            <div>\n              ДЕмонстрация работы фреймворка Unfiltered для языка Scala:\n              это HTML-страница, встроенная в код файла scala.\n            </div>\n          </body>\n        </html>")
    }

    case Path(Seg(Nil)) => {
      println("static_html.html")
      Ok ~> HtmlContent ~> ResponseString(
        scala.io.Source.fromInputStream(getClass.getResourceAsStream("/html/static_html.html"),
          "UTF-8").mkString)
    }
    case Path(Seg("main.css" :: Nil)) => {
      println("main.css")
      Ok ~> CssContent ~> ResponseString(
        scala.io.Source.fromInputStream(
          getClass.getResourceAsStream("/css/main.css"), "UTF-8").mkString)
    }
    case Path(Seg("jquery.js" :: Nil)) => {
      println("jquery.js")
      Ok ~> JsContent ~> ResponseString(
        scala.io.Source.fromInputStream(
          getClass.getResourceAsStream("/js/jquery.js"), "UTF-8").mkString)
    }
    case Path(Seg("inquiry.js" :: Nil)) => {
      println("inquiry.js")
      Ok ~> JsContent ~> ResponseString(
        scala.io.Source.fromInputStream(
          getClass.getResourceAsStream("/js/inquiry.js"), "UTF-8").mkString)
    }
    case Path(Seg("enter.js" :: Nil)) => {
      println("enter.js")
      Ok ~> JsContent ~> ResponseString(
        scala.io.Source.fromInputStream(
          getClass.getResourceAsStream("/js/enter.js"), "UTF-8").mkString)
    }

    case Params(params) => {
      println("params= " + params.toString())
      for ((k, v) <- params) println("kye:" + k + ", value:" + v)

      val login: String = params.get("login").getOrElse(Seq(""))(0)
      val password: String = params.get("password").getOrElse(Seq(""))(0)
      if (login.length > 0 && password.length > 0 && KnowledgeBaseEditor.UserVerification(login, password)) {
        ResponseString("YES!")

      }
      else {
        ResponseString("false")
      }
    }
  }

  //==============================================================================
  import unfiltered.Cookie



  case class Auth(users: Users) {
    def apply[A, B](intent: Cycle.Intent[A, B]) =
      Cycle.Intent[A, B] {
        case req@BasicAuth(user, pass) if (users.auth(user, pass)) =>
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
//==============================================================================
  def main(args: Array[String]) {
    println("Starting Scala Unfiltered web framework demo on Jetty http server...")
    unfiltered.jetty.Http.apply(8080).plan(handlePath).run()
  }
}