package org.sevenmob.geocode

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext
import dispatch._
import Formats._
import scala.concurrent.duration._


class Geocode(http: Http = Http) {

  /**
   * This call to google service is limited
   * @see https://developers.google.com/maps/documentation/geocoding/#Limits
   */
  def ?(p: Parameters)(implicit ec: ExecutionContext): Future[Either[Error, List[Result]]] = {
    import p._
    val parameters = List(
      "address" -> s"$address",
      "key" -> s"$apikey",
      "sensor" -> "false"
    )
    val req = url("https://maps.googleapis.com/maps/api/geocode/json") <<? parameters
    http(req OK as.String).map(parseResult)
  }
}

object GeocodeObj extends GeocodeCalls {

    implicit val geocode = new Geocode()

    def ?(parameters: Parameters): Point = callGeocode(parameters, Duration(3, SECONDS)) match {
         case Right(x) => x.head.geometry.location
         case Left(x) => Point(0,0)
    }
}

/** 
 * This trait is a collection methods that perform a call
 * to the Google geocode WebService.  
 * Just import an ExecutionContext and create an implicit GeocodeClient 
 * to call this functions.
 */
trait GeocodeCalls {
  
  import scala.concurrent.Await
  import scala.concurrent.duration._

  /**
   * This call to google service is limited
   * @see https://developers.google.com/maps/documentation/geocoding/#Limits
   */
  def callGeocode(p: Parameters, d: Duration)
                 (implicit ec: ExecutionContext, client: Geocode)
                 : Either[Error, List[Result]] = {
    Await.result(client ? p, d)  
  }
  
}
