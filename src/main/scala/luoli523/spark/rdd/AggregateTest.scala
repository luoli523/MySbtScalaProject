package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

object AggregateTest {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                            .appName("RDD aggregate api usage")
                            .getOrCreate()

    val rdd = spark.sparkContext.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9),3)
  }

}
