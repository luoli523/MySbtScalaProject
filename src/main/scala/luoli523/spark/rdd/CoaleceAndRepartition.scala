package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

// coalesce和repartition都是用来对RDD数据进行重新partition分布的函数。
// coalesce对RDD数据进行制定partition num的重新分配，
// repartition(numPartitions)是对coalesce(numPartitions, shuffle=true)的包装
object CoaleceAndRepartition {

  def main(args : Array[String]) : Unit = {

    val spark = SparkSession.builder().appName("coalece and repartition").getOrCreate()

    val x = spark.sparkContext.parallelize(1 to 10, 10)

    // This results in a narrow dependency, e.g. if you go from 1000 partitions to 100 partitions,
    // there will not be a shuffle, instead each of the 100 new partitions will claim 10 of the current partitions.
    // If a larger number of partitions is requested, it will stay at the current number of partitions.
    //
    // However, if you're doing a drastic coalesce, e.g. to numPartitions = 1,
    // this may result in your computation taking place on fewer nodes than you like
    // (e.g. one node in the case of numPartitions = 1).
    // To avoid this, you can pass shuffle = true.
    // This will add a shuffle step,
    // but means the current upstream partitions will be executed in parallel (per whatever the current partitioning is).
    //
    // With shuffle = true, you can actually coalesce to a larger number of partitions.
    // This is useful if you have a small number of partitions, say 100,
    // potentially with a few partitions being abnormally large.
    // Calling coalesce(1000, shuffle = true) will result in 1000 partitions with the data distributed using a hash partitioner.
    // The optional partition coalescer passed in must be serializable.

    // 如果coalesce的调用是减少partition数，且shuffle=false，那么并不会真正发生数据shuffle
    val y = x.coalesce(2, false)

    println("parallelize(List(Range(1, 10)), 10).coalesce(2,false) partition num: " + y.getNumPartitions)

    // repartition(num)是对coalesce(num, true)的封装，会实际发生数据shuffle
    val z = x.repartition(2)
    println("parallelize(List(Range(1, 10)), 10).repartition(2) partition num: " + z.getNumPartitions)

    spark.stop()

  }

}
