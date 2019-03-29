package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

import luoli523.spark.rdd.SparkRDDFuncitons.mapWithIndexFunc

object Collect {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("collect api usage").getOrCreate()

    val rdd = spark.sparkContext.parallelize(List.range(0, 10), 2)

    // 将RDD转换成Scala的Array并返回。如果在调用时还提供了一个scala map函数（比如： f = T -> U），
    // 那么collect和toArray调用会将RDD中的数据进行map转换后的结果返回。
    rdd.mapPartitionsWithIndex(mapWithIndexFunc).collect.foreach(println)

    // 需要注意的是,collect(func)接受的函数参数实际上是一个partialFunction
    val pfunc : PartialFunction[Int, Int] = {
      // 这里相当于定义了 isDefinedAt(x) { x > 0}
      case x if (x > 0) => 42 / x
    }
    rdd.collect(pfunc).foreach(println)

    spark.stop()
  }

}
