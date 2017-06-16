package com.playspark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory
import com.playspark.utils.DateUtils

import scala.concurrent.duration._

/**
  * Created by oscar on 16/06/17.
  */
object PlaySparkBaseApp {

  @transient lazy private val Log = LoggerFactory.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("PlaySparkBaseApp")

    // FOR LOCAL EXECUTION UNCOMMENT
    .setMaster("local[*]").setAppName("PlaySparkBaseApp")

    // FOR CLUSTER EXECUTION UNCOMMENT
    //Set Cluster Yarn parameters
//    sparkConf.set("spark.yarn.executor.memoryOverhead", "1024")
//      .set("spark.yarn.driver.memoryOverhead", "1024")
//      .set("spark.yarn.queue", "root.DyA.Apps")
//
//      .set("spark.rdd.compress", "true")
//      .set("spark.broadcast.compress", "true")
//      .set("spark.io.compression.codec", "snappy")
//      .set("spark.shuffle.compress", "true")
//      .set("spark.shuffle.spill.compress", "true")
//
//      //Set kryo serializer options
//      .set("spark.kryoserializer.buffer", "128k")
//      .set("spark.kryoserializer.buffer.max", "128m")
//      .set("spark.kryo.registrator", classOf[DateUtils.TimeSeriesKryoRegistrator].getName)

    val sparkContext = new SparkContext(sparkConf)
    val hiveContext = new HiveContext(sparkContext)

    val iniTime = System.currentTimeMillis()

    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    PlaySparkBase.process(sparkContext, hiveContext, args)

    Log.info("Total time: " + (System.currentTimeMillis() - iniTime).millis.toMinutes + " minutes ")
    Log.info("End of execution")
  }
}
