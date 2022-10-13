package repositories

import model.MinifiedUrl

trait UrlRepository {
  def setURL(id: Long, data: MinifiedUrl): Boolean

  def getURL(id: Long): Option[MinifiedUrl]

  def getNextId: Option[Long]

  def incrementCounter(id: Long): Option[Long]
}
