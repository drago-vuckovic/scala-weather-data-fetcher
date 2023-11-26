import io.circe.parser
import io.circe.Decoder
import io.circe.generic.auto._
import com.opencsv.CSVWriter
import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneOffset}
import requests._

case class Coordinates(lon: Double, lat: Double)
case class MainData(temp: Double, feels_like: Double, temp_min: Double, temp_max: Double, pressure: Int, humidity: Int, sea_level: Double)
case class SysData(sunrise: Long, sunset: Long)
case class WeatherData(name: String, coord: Coordinates, dt: Long, main: MainData, sys: SysData)

object Week2 {

  private val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

  def main(args: Array[String]): Unit = {

    val apiKey: Option[String] = sys.env.get("apiKey")

    val latitude = "45.23"
    val longitude = "19.82"

    val apiUrl = s"https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&appid=$apiKey"

    val response = requests.get(apiUrl)

    val json = parser.parse(response.text).getOrElse(io.circe.Json.Null)

    val coordinatesResult: Decoder.Result[Coordinates] = Decoder[Coordinates].decodeJson(json.hcursor.downField("coord").focus.getOrElse(io.circe.Json.Null))
    val weatherDataResult: Decoder.Result[WeatherData] = Decoder[WeatherData].decodeJson(json)

    val result: Decoder.Result[(Coordinates, WeatherData)] = for {
      coordinates <- coordinatesResult
      weatherData <- weatherDataResult
    } yield (coordinates, weatherData)

    result match {
      case Right((coordinates, weatherData)) =>
        val outputPath = "output.csv"
        val writer = new CSVWriter(new java.io.FileWriter(outputPath))

        writer.writeNext(Array("City name", "Lat. Long", "date", "temperature", "feels_like", "temp_min", "temp_max", "pressure", "humidity", "sea level", "sunrise time", "sunset time"))

        val formattedDate = Instant.ofEpochSecond(weatherData.dt).atOffset(ZoneOffset.UTC).format(dateFormat)
        val formattedSunrise = Instant.ofEpochSecond(weatherData.sys.sunrise).atOffset(ZoneOffset.UTC).format(dateFormat)
        val formattedSunset = Instant.ofEpochSecond(weatherData.sys.sunset).atOffset(ZoneOffset.UTC).format(dateFormat)

        val csvData = Array(
          weatherData.name,
          s"${coordinates.lat}, ${coordinates.lon}",
          formattedDate,
          weatherData.main.temp.toString,
          weatherData.main.feels_like.toString,
          weatherData.main.temp_min.toString,
          weatherData.main.temp_max.toString,
          weatherData.main.pressure.toString,
          weatherData.main.humidity.toString,
          weatherData.main.sea_level.toString,
          formattedSunrise,
          formattedSunset
        )

        writer.writeNext(csvData)
        writer.close()
      case Left(error) => println(s"Error decoding JSON: $error")
    }
  }
}
