package luoli523.spark.rdd

import scala.collection.mutable.ListBuffer

object SparkRDDFuncitons {

  /**
    * (1,2,3,4) -> ((1,2), (2,3), (3,4))
    * @param iterator
    * @tparam T
    * @return
    */
  def oneToTwo[T](iterator: Iterator[T]) : Iterator[(T, T)] = {
    var list = List[(T, T)]()
    var pre = iterator.next()
    while (iterator.hasNext) {
      val cur = iterator.next()
      list .::= (pre, cur)
      pre = cur
    }
    list.toIterator
  }

}
