package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

// 笛卡尔积函数，计算两个RDD中数据的笛卡尔积（第一个RDD中的每一个item join 第二个RDD中的每一个item），
// 并返回包含笛卡尔积数据结果的新RDD
//（Warning: 这个函数使用的时候需要慎重，因为会产生大量的中间结果数据，RDD消耗的内存会瞬间暴涨）

object Cartesian {

  def main(args : Array[String]) : Unit = {
    val spark = SparkSession.builder().appName("cartesian api").getOrCreate()

    val x = spark.sparkContext.parallelize(List(1,2,3,4,5))
    val y = spark.sparkContext.parallelize(List(6,7,8,9,10))

    val cartesian = x.cartesian(y)
    println("List(1,2,3,4,5).cartesian(List(6,7,8,9,10)):")
    cartesian.collect.foreach(println)

    spark.stop()
  }

}
