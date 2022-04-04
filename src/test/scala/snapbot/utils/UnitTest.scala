package snapbot.utils

import cats.effect.unsafe.IORuntime
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.mockito.scalatest.IdiomaticMockito
import org.scalatest.BeforeAndAfterEach

trait UnitTest
  extends AnyWordSpec
    with Matchers
    with IdiomaticMockito
    with BeforeAndAfterEach {

  implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global
}
