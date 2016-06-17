package org.sevenmob.geocode

import com.maxmind.geoip2.DatabaseReader
import java.io.File
import java.net.InetAddress
import scala.util.{Try, Success, Failure}

object DbReader {
    @transient lazy val reader = {
        val db = new File("/spark-job/geocode_db/GeoLite2-City.mmdb")
        val reader = new DatabaseReader.Builder(db).build()

        reader
    }
}

case class GeoIp(
  lng: Double,
  lat: Double,
  city: Option[String] = None,
  region: Option[String] = None,
  countryCode: Option[String] = None)

case class GeoIPLookup() {

  def toDouble(num: Any): Double =
    Try(num match { case i: Int => i case f: Float => f case d: Double => d }) match {
        case Success(r) => r
        case Failure(e) => 0.0
  }

  def fromIP(ip: String): Option[GeoIp] =
    Try(DbReader.reader.city(InetAddress getByName ip)) match {
      case Success(r) => Some(GeoIp(
        lng = toDouble(r.getLocation.getLongitude),
        lat = toDouble(r.getLocation.getLatitude),
        city = Option(r.getCity.getName),
        region = Option(r.getMostSpecificSubdivision.getName),
        countryCode = Option(r.getCountry.getIsoCode)))
      case Failure(e) => None
  }
}
