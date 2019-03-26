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


  def randonLengthen[T](iter : Iterator[T]) : Iterator[T] = {
    var list = List[T]()
    while (iter.hasNext) {
      val cur = iter.next()
      list = list ::: List.fill(scala.util.Random.nextInt(10))(cur)
    }
    list.toIterator
  }

  def toBeGenius(iter : Iterator[String]) : Iterator[String] = {
    var list = ListBuffer[String]()
    while (iter.hasNext) {
      val cur = iter.next()
      list += s"${cur} is a genius"
    }
    list.toIterator
  }
}
