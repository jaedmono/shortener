package controllers

import javax.inject.Inject
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import play.api.mvc._
import model._
import play.api.Logger
import services.UrlShortenerService


class UrlShortenerController  @Inject()(cc: ControllerComponents, service: UrlShortenerService)
  extends AbstractController(cc) {

  private val logger = Logger(getClass)

  def createShortURL: Action[AnyContent] = Action { implicit request =>

    logger.debug(s"$request ${request.body}")

    request.body.asJson match {
      case None => BadRequest("Expecting application/json request body")
      case Some(jsonBody) => decode[MinifiedUrl](jsonBody.toString()) match {
        case Left(error)=> BadRequest("Invalid application/json request body. Reason: " + error.toString)
        case Right(resource) => service.createShortURL(resource) match {
          case None => Conflict("There was a problem encoding the URL")
          case Some(shortUrl) => Created(shortUrl.asJson.noSpaces).as("application/json")
        }
      }
    }
  }

  def redirect(short_url: String) = Action { implicit request =>
    logger.debug(s"$request")

    service.lookup(short_url) match {
      case None => NotFound
      case Some(originalUrl) => MovedPermanently(originalUrl)
        .withHeaders(CACHE_CONTROL -> "no-cache, no-store, max-age=0, must-revalidate")
    }
  }

}
