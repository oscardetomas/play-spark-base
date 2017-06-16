package com.playspark

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveContext
import org.slf4j.LoggerFactory

/**
  * Created by oscar on 16/06/17.
  */
object PlaySparkBase {

  @transient lazy private val Log = LoggerFactory.getLogger(getClass.getName)

  def process(sc: SparkContext, hiveContext: HiveContext): Unit = {
    Log.info("Init process...")

    executeInSparkContext(sc){
      // Execution in Spark Context

      testProject(sc)

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

  private def testProject(sc: SparkContext): Unit = {

    val textFile = sc.textFile("README.md")
    println("Number of items in README.md : " + textFile.count())

    val linesSparkCounter = textFile.filter(line => line.contains("Spark")).count()
    println("How many lines contain \"Spark\": " + linesSparkCounter)

  }

}
