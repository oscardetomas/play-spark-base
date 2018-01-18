package com.playspark

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.slf4j.LoggerFactory

import scala.math.random

/**
  * Created by oscar on 16/06/17.
  */
object PlaySparkBase {

  @transient lazy private val Log = LoggerFactory.getLogger(getClass.getName)

  def process(sc: Sparkcontext,
              hiveContext: HiveContext,
              args: Array[String]): Unit = {

    Log.info("Init process...")

    executeInSparkContext(sc){
      // Execution in Spark Context

      //testProject(sc)
      sparkPi(sc, args)

    }

    Log.info("End process...")
  }

  private def executeInSparkContext(sc: SparkContext)(function: => Unit) = {
    function
    sc.stop()
  }

  /** ----------------------------------------------
    * PROCESSING METHODS
    * ----------------------------------------------
    */

  private def testUDF(sc: SparkContext): Unit = {

    val textFile = sc.textFile("README.md")
    println("Number of items in README.md : " + textFile.count())

    val linesSparkCounter = textFile.filter(line => line.contains("Spark")).count()
    println("How many lines contain \"Spark\": " + linesSparkCounter)

  }

  private def testProject(sc: SparkContext): Unit = {

    val textFile = sc.textFile("README.md")
    println("Number of items in README.md : " + textFile.count())

    val linesSparkCounter = textFile.filter(line => line.contains("Spark")).count()
    println("How many lines contain \"Spark\": " + linesSparkCounter)

  }

  private def sparkPi(sc: SparkContext, args: Array[String]): Unit = {

    val slices = if (args.length > 0) args(0).toInt else 2
    val n = Int.MaxValue
    val count = sc.parallelize(1 until n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x*x + y*y < 1) 1 else 0
    }.reduce(_ + _)

    println("Pi is roughly " + 4.0 * count / (n - 1))

    scala.io.StdIn.readLine("Press any key to finish...")

  }

}
