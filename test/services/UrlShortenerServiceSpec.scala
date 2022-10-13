package services

import model.MinifiedUrl
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import repositories.UrlRepository

class UrlShortenerServiceSpec extends PlaySpec with MockitoSugar {

  "UrlShortenerService#createShortURL" should {
    "return true if the data is saved" in {
      val mockRepository = mock[UrlRepository]
      val minifiedUrl = MinifiedUrl("https://www.google.com", Option("http://localhost:9000/K4"), Option("2018-07-06T16:12:14.878+0000"))
      when(mockRepository.setURL(1l,minifiedUrl)).thenReturn(true)

      val service = new UrlShortenerService(mockRepository);
      val actual = service.createShortURL(minifiedUrl)
      actual mustBe true
    }
  }
}

