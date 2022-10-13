import com.google.inject.AbstractModule
import repositories.{RedisUrlRepository, UrlRepository}

class Module extends AbstractModule {

  override def configure() = {
      bind(classOf[UrlRepository]).to(classOf[RedisUrlRepository]).asEagerSingleton()
  }

}
