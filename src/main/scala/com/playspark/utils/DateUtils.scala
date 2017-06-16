package com.playspark.utils

import java.util.Locale

import com.esotericsoftware.kryo.Kryo
import de.javakaffee.kryoserializers.jodatime.{JodaDateTimeSerializer, JodaLocalDateSerializer, JodaLocalDateTimeSerializer}
import org.apache.spark.serializer.KryoRegistrator
import org.joda.time.format.DateTimeFormat

import org.joda.time.{DateTime, LocalDate, LocalDateTime}

object DateUtils {


  class TimeSeriesKryoRegistrator extends KryoRegistrator {
    def registerClasses(kryo: Kryo): Unit = {
      //kryo.register(classOf[DateTime], new DateTimeSerializer)
      kryo.register( classOf[DateTime], new JodaDateTimeSerializer() )
      kryo.register( classOf[LocalDate], new JodaLocalDateSerializer() )
      kryo.register( classOf[LocalDateTime], new JodaLocalDateTimeSerializer() )

    }
  }

  val nullDate_fin = DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime("9999/01/01")
  val nullDate = DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime("0001/01/01")
  val mayDate =DateTimeFormat.forPattern("yyyy/MM/dd").parseDateTime("2015/05/01")
  def parseJodaDate(str: String, pattern: String): DateTime = {

    val SPANISH = new Locale("es", "ES")
    val fmt = DateTimeFormat.forPattern(pattern).withLocale(SPANISH)
    try{
      str match {
        case "" => DateUtils.nullDate
        case _ => fmt.parseDateTime(str) //DateTime.parse(a,dateFormat)
      }}
    catch{
      case _ : Throwable => DateUtils.nullDate
    }
  }

  def parseJodaDateUndefinedPat(str: String, pattern: List[String]): DateTime = {
    val a1 = pattern.map(x=> DateUtils.parseJodaDate(str,x))
    def maxDate(s1: DateTime, s2: DateTime): DateTime = if (s1.isAfter(s2)) s1 else s2
    val a2 = a1.reduceLeft(maxDate)
    return a2
  }

  def jodaDateToStr(date: DateTime): String = {
    val dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy")
    try{
      return dtfOut.print(date)
    }
    catch{
      case _ : Throwable => return ""
    }
  }

}
