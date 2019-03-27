package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

import luoli523.spark.rdd.SparkRDDFuncitons.oneToTwo
import luoli523.spark.rdd.SparkRDDFuncitons.randonLengthen
import luoli523.spark.rdd.SparkRDDFuncitons.toBeGenius
import luoli523.spark.rdd.SparkRDDFuncitons.mapWithIndexFunc

object MapPartitionTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                            .appName("RDD mapPartition & mapPartitionWithIndex api usage")
                            .getOrCreate()

    val rdd = spark.sparkContext.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9),3)

    /*
     mapPartition接受一个用户函数参数
     该用户函数必须返回一个Iterator类型的集合
     该函数仅仅在RDD的所有partition上执行一次。
     每个partition中的items是以iterator的形式进行逐个处理
     所有partition上返回的Iterator会被搜集到driver并组合成一个新的RDD
     */
    rdd.mapPartitions(oneToTwo).collect().foreach(println)
    rdd.mapPartitions(randonLengthen).collect().foreach(println)

    val names = spark.sparkContext.parallelize(List("luoli", "guili", "guige"), 3)
    names.mapPartitions(toBeGenius).collect.foreach(println)

    /*
    mapPartitionWithIndex和mapPartitions类似，但参数函数接受两个参数
    第一个参数为该partition在所有partitions中的index
    第二个参数为item的iterator。
    参数函数的返回值必须也要是一个iterator
     */
    rdd.mapPartitionsWithIndex(mapWithIndexFunc).collect.foreach(println)
    names.mapPartitionsWithIndex(mapWithIndexFunc).collect.foreach(println)

    spark.stop()
  }

}
