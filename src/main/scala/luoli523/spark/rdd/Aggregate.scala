package luoli523.spark.rdd

import org.apache.spark.sql.SparkSession

import luoli523.spark.rdd.SparkRDDFuncitons.mapWithIndexFunc

/*
aggregate函数可以让用户对RDD进行两轮reduce函数操作。
第一轮reduce函数作用在每个RDD partition内部，对每个RDD内的数据进行一轮reduce计算，产生一个结果。
第二轮reduce函数作用在第一轮中每个partition上reduce函数的结果上，同样产生一个最终结果。
这种用户自己提供两轮reduce计算逻辑的方式给用户提供了非常大的灵活性。

例如第一轮reduce可以计算出各个partition中最大的值，而第二轮reduce函数可以计算这些最大值的和。
不仅如此，用户还可以提供一个初始值，用来参与到两轮的reduce计算中。

aggregate函数需要注意以下几点：

 - 初始值不仅仅会参与每个partition上的第一轮计算，同时也会参与第二轮的reduce计算。
 - 两轮reduce的函数都要对各个item进行交替的组合计算
 - 每一轮的reduce计算的顺序都是不一定的。
 */
object Aggregate {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
                            .appName("rdd aggregate api usage")
                            .getOrCreate()

    //val rdd = spark.sparkContext.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9), 3)
    val rdd = spark.sparkContext.parallelize(List.range(1, 10), 3)

    // 打印list中各个partition中的元素
    rdd.mapPartitionsWithIndex(mapWithIndexFunc).collect.foreach(println)

    // RDD的aggregate函数需要提供三个参数
    // 1, 一个初始值
    // 2, 在每个partition中对各个item进行reduce的函数
    // 3, 最后对所有partition上reduce后的结果进行再次reduce的函数
    val maxSumOfEveryPartition = rdd.aggregate(0)(math.max(_ , _), _+_)
    println("sc.parallelize(Range(1, 10).toList, 3).aggregate(0)(math.max(_ , _), _+_) is: " + maxSumOfEveryPartition)

    val maxSumOfEveryPartitionWithInitTwo = rdd.aggregate(2)(math.max(_ , _), _+_)
    println("sc.parallelize(Range(1, 10).toList, 3).aggregate(2)(math.max(_ , _), _+_) is: " + maxSumOfEveryPartitionWithInitTwo)

    // 这个例子的最终值是25的原因是：初始值是5
    // 在partition 0上的reduce函数的结果是max(5, 1, 2, 3) = 5
    // 在partition 1上的reduce函数的结果是max(5, 4, 5, 6) = 6
    // 在partition 2上的reduce函数的结果是max(5, 7, 8, 9) = 9
    // 最后第二个reduce函数将初始值，以及各个partition上第一轮reduce的结果进行二次reduce的结果：5 + 5 + 6 + 9 = 25
    // 需要注意，最终结果中需要再次包含初始值进行计算
    val maxSumOfEveryPartitionWithInitFive = rdd.aggregate(5)(math.max(_, _), _ + _)
    println("sc.parallelize(Range(1, 10).toList, 3).aggregate(5)(math.max(_ , _), _+_) is: " + maxSumOfEveryPartitionWithInitFive)

    var z = spark.sparkContext.parallelize(List("a","b","c","d","e","f"),2)
    z.mapPartitionsWithIndex(mapWithIndexFunc).collect.foreach(println)

    // 注意，这里结果也有可能是defabc或者abcdef,因为各个partition的结果在第二轮reduce的时候的顺序是不一定的
    var res = z.aggregate("")(_ + _, _+_)
    println("sc.parallelize(List(a,b,c,d,e,f), 2).aggregate(\"\")(_ + _, _+_) is: " + res)

    // 看这里的初始值“x”被使用了三次
    //  - 在每个partition上进行第一轮reduce的时候一次
    //  - 最后对各个partition上的值进行第二轮reduce的时候第二次
    // 注意这里的结果可能是"xxdefxabc",也有可能是“xxabcxdef”,原因同上
    res = z.aggregate("x")(_ + _, _+_)
    println("sc.parallelize(List(a,b,c,d,e,f), 2).aggregate(\"x\")(_ + _, _+_) is: " + res)

    // 下面是一些其他的用法, 有一些比较tricky.
    z = spark.sparkContext.parallelize(List("12","23","345","4567"),2)
    res = z.aggregate("")((x,y) => math.max(x.length, y.length).toString, (x,y) => x + y)
    println("sc.parallelize(List(\"12\",\"23\",\"345\",\"4567\"), 2).aggregate(\"\")((x,y) => math.max(x.length, y.length).toString, (x,y) => x + y) is: " + res)

    res = z.aggregate("")((x,y) => math.min(x.length, y.length).toString, (x,y) => x + y)
    println("sc.parallelize(List(\"12\",\"23\",\"345\",\"4567\"), 2).aggregate(\"\")((x,y) => math.min(x.length, y.length).toString, (x,y) => x + y) is: " + res)

    z = spark.sparkContext.parallelize(List("12","23","345",""),2)
    res = z.aggregate("")((x,y) => math.min(x.length, y.length).toString, (x,y) => x + y)
    println("sc.parallelize(List(\"12\",\"23\",\"345\",\"\"), 2).aggregate(\"\")((x,y) => math.min(x.length, y.length).toString, (x,y) => x + y) is: " + res)

    // aggregateByKey是作用在[K,V]对的数据上的。它跟aggregate函数比较像，
    // 只不过reduce function并不是作用在所有的item上，而是作用在同一个key的所有item上。
    // 同时区别于aggregate，aggregateByKey初始值只作用于第一轮reduce，不参与第二轮reduce的计算。
    val pairRDD = spark.sparkContext.parallelize(List( ("cat",2),
                                                       ("cat", 5),
                                                       ("mouse", 4),
                                                       ("cat", 12),
                                                       ("dog", 12),
                                                       ("mouse", 2)), 2)
    pairRDD.mapPartitionsWithIndex(mapWithIndexFunc).collect.foreach(println)

    // Array[(String, Int)] = Array((dog,12), (cat,17), (mouse,6))
    pairRDD.aggregateByKey(0)(math.max(_, _), _ + _).collect.foreach(println)

    // Array[(String, Int)] = Array((dog,100), (cat,200), (mouse,200))
    pairRDD.aggregateByKey(100)(math.max(_, _), _ + _).collect.foreach(println)

    spark.stop()
  }

}
