package org.sevenmob.geocode

import org.specs2.mutable.Spec
import scala.concurrent._, duration._, ExecutionContext.Implicits.global


class GeocodeSpec extends Spec {
  "Geocode" should {
    "find data by address" in {
      val geocode = new Geocode()

      def ?(x: Parameters) = Await.result(geocode ? x, 3.seconds)

      ?(Parameters("London", "")) must beRight
    }
  }
}
