package snapbot.utils

import cats.effect.IO

object IOUtils {
  def raceMany[T](ios: List[IO[T]]): IO[T] = ios.reduce{ _.race(_).map(_.merge) }
}
