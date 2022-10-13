package services


import model.MinifiedUrl
import repositories.UrlRepository

import javax.inject.{Inject, Singleton}
import utils._

@Singleton
class UrlShortenerService @Inject()(repository: UrlRepository) {

  def createShortURL(resource: MinifiedUrl): Option[MinifiedUrl] = {
    repository.getNextId match {
      case None => None
      case Some(id) =>
        val encodedId = Utils.idEnconder.encode(id)
        val minifiedUrl = MinifiedUrl(resource.originalUrl,
              Option(Config.SHORT_URL_DOMAIN + encodedId),
              Option(Utils.getCurrentUTCTimeString))

        if (repository.setURL(id, minifiedUrl))
          Option(minifiedUrl)
        else
          None

    }
  }

  def lookup(shortURL: String): Option[String] = {
    Utils.idEnconder.decode(shortURL) match {
      case Nil => None
      case id :: _ => repository.getURL(id) match {
        case None => None
        case Some(data) => {
          repository.incrementCounter(id)
          Option(data.originalUrl)
        }
      }
    }
  }
}
