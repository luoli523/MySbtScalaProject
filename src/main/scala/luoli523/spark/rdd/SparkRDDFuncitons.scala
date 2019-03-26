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

  /**
    * (1,2,3) -> (1,1,.., 2,.., 3,3,..)
    * @param iter
    * @tparam T
    * @return
    */
  def randonLengthen[T](iter : Iterator[T]) : Iterator[T] = {
    var list = List[T]()
    while (iter.hasNext) {
      val cur = iter.next()
      list = list ::: List.fill(scala.util.Random.nextInt(10))(cur)
    }
    list.toIterator
  }

  /**
    * (a, b, c) -> (a is a genius, b is a genius, c is a genius)
    * @param iter
    * @return
    */
  def toBeGenius(iter : Iterator[String]) : Iterator[String] = {
    var list = ListBuffer[String]()
    while (iter.hasNext) {
      val cur = iter.next()
      list += s"${cur} is a genius"
    }
    list.toIterator
  }

  /**
    * 1, (a, b, c) -> ((1,a), (1,b), (1,c))
    * 2, (d, e, f) -> ((2,d), (2,e), (2,f))
    * @param index
    * @param iter
    * @tparam T
    * @return
    */
  def mapWithIndexFunc[T](index : Int, iter : Iterator[T]) : Iterator[(Int, T)] = {
    iter.toList.map( i => (index, i)).toIterator
  }
}
