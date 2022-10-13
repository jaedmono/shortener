package controllers


import model.MinifiedUrl
import org.mockito.Mockito.when

import scala.concurrent.Future
import org.scalatestplus.play._
import org.specs2.mock.Mockito.mockAs
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test._
import play.api.test.Helpers._
import services.UrlShortenerService



class UrlShortenerControllerSpec extends PlaySpec with Results{

  "Shortening URL #createShortURL" should {
    "should be created" in {
      val fakeRequest = FakeRequest(POST, "/api/shorten", FakeHeaders(), AnyContentAsJson(Json.parse("""{"originalUrl": "https://www.google.com"}""")))
      val mockService = mockAs[UrlShortenerService]("service")
      val minifiedUrl = MinifiedUrl("https://www.google.com", Option("http://localhost:9000/K4"), Option("2018-07-06T16:12:14.878+0000"))
      when(mockService.createShortURL(minifiedUrl)).thenReturn(Option(minifiedUrl))
      val controller             = new UrlShortenerController(Helpers.stubControllerComponents(), mockService)
      val result: Future[Result] = controller.createShortURL().apply(fakeRequest)
      val bodyText: String       = contentAsString(result)
      bodyText mustBe "{\n  \"originalUrl\": \"https://www.google.gov\",\n  \"shortUrl\": \"http://localhost:9000/K4\",\n  \"creationDate\": \"2018-07-06T16:12:14.878+0000\"\n}"
    }
  }
}

