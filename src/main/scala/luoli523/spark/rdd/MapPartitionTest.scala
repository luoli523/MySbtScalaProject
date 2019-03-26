package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

import luoli523.spark.rdd.SparkRDDFuncitons.oneToTwo
import luoli523.spark.rdd.SparkRDDFuncitons.randonLengthen

/*
 mapPartition接受一个用户函数参数
 该用户函数必须返回一个Iterator类型的集合
 该函数仅仅在RDD的所有partition上执行一次。
 每个partition中的items是以iterator的形式进行逐个处理
 所有partition上返回的Iterator会被搜集到driver并组合成一个新的RDD
 */
object MapPartitionTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                            .appName("RDD aggregate api usage")
                            .getOrCreate()

    val rdd = spark.sparkContext.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9),3)

    rdd.mapPartitions(oneToTwo).collect().foreach(println)

    rdd.mapPartitions(randonLengthen).collect().foreach(println)

    spark.stop()
  }

}
