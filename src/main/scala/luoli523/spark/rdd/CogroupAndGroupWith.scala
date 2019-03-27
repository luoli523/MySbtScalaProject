package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession


// cogroup和groupWith都是作用在[K,V]结构的item上的函数
// 它们都是非常有用的函数，能够将不同RDD的相同key的values group到一起
object CogroupAndGroupWith {

  def main(args : Array[String]) : Unit = {

    val spark = SparkSession.builder().appName("cogroup and groupWith").getOrCreate()

    val a = spark.sparkContext.parallelize(List(1,2,1,3), 1)
    val b = a.map((_, "b"))
    val c = a.map((_, "c"))

    // cogroup将不同rdd中相同key的value进行整合，返回后的rdd结构是 (K, (Iterable[V], Iterable[W]))
    // 既按照不同rdd将对应的value进行分别整合

    // (1,(CompactBuffer(b, b),CompactBuffer(c, c)))
    // (3,(CompactBuffer(b),CompactBuffer(c)))
    // (2,(CompactBuffer(b),CompactBuffer(c)))
    val bgroupc = b.cogroup(c)
    bgroupc.collect.foreach(println)

    // (1,(CompactBuffer(b, b),CompactBuffer(c, c),CompactBuffer(d, d)))
    // (3,(CompactBuffer(b),CompactBuffer(c),CompactBuffer(d)))
    // (2,(CompactBuffer(b),CompactBuffer(c),CompactBuffer(d)))
    val d = a.map((_, "d"))
    b.cogroup(c, d).collect.foreach(println)

    val x = spark.sparkContext.parallelize(List((1, "apple"), (2, "banana"), (3, "orange"), (4, "kiwi")), 2)
    val y = spark.sparkContext.parallelize(List((5, "computer"), (1, "laptop"), (1, "desktop"), (4, "iPad")), 2)

    // (4,(CompactBuffer(kiwi),CompactBuffer(iPad)))
    // (2,(CompactBuffer(banana),CompactBuffer()))
    // (1,(CompactBuffer(apple),CompactBuffer(laptop, desktop)))
    // (3,(CompactBuffer(orange),CompactBuffer()))
    // (5,(CompactBuffer(),CompactBuffer(computer)))
    x.cogroup(y).collect.foreach(println)

    spark.stop()
  }

}
