package luoli523.scala.list.recursive

import scala.annotation.tailrec

/**
  * Different ways to calculate the sum of a list using
  * recursive Scala methods.
  */
object ListRecursive {

  def main(args: Array[String]): Unit = {
    val list = List.range(1, 100)
    println(sum1(list))
    println(sumWithTailRecursive(list))
    println(sum3(list))
    println(list.reduceLeft(_ + _))
    println((0 /: list)(_ + _))

    val plist = scala.util.Random.shuffle(List.range(1, 10))
    println(produce1(plist))
    println(produceWithTailRecursive(plist))

    val mlist = scala.util.Random.shuffle(List.range(1, 100))
    println(max1(mlist))
    println(max2(mlist))
  }

  // 因为没有使用尾递归，当list元素很多的时候这里有可能会引起StackOverflowError
  def sum1(list : List[Int]) : Int = {
    list match {
      case head :: tail => head + sum1(tail)
      case Nil => 0
    }
  }

  // 使用尾递归
  def sumWithTailRecursive(list: List[Int]) : Int = {

    @tailrec
    def sumAccumulator(list : List[Int], accu : Int) : Int = {
      list match {
        case Nil => accu
        case head :: tail => sumAccumulator(tail, accu + head)
      }
    }
    sumAccumulator(list, 0)
  }

  def sum3(list : List[Int]) : Int = {
    if (list.isEmpty) 0
    else list.head + sum3(list.tail)
  }

  def produce1(list : List[Int]) : Int = {
    list match {
      case head :: tail => head * produce1(tail)
      case Nil => 1
    }
  }

  def produceWithTailRecursive(list: List[Int]) : Int = {
    @tailrec
    def produceAccumulator(list: List[Int], accu : Int) : Int = {
      list match {
        case head :: tail => produceAccumulator(tail, head * accu)
        case Nil => accu
      }
    }
    produceAccumulator(list, 1)
  }

  def max1(list : List[Int]) : Int = {

    @tailrec
    def maxWithTailRecursive(list: List[Int], accu : Int) : Int = {
      list match {
        case head :: tail => if(head > accu) maxWithTailRecursive(tail, head) else maxWithTailRecursive(tail, accu)
        case Nil => accu
      }
    }
    maxWithTailRecursive(list.tail, list.head)
  }

  def max2(list : List[Int]) : Int = {

    @tailrec
    def maxWithTailRecursiveIfElse(list: List[Int], accu : Int) : Int = {
      if (list.isEmpty)
        accu
      else {
        if (list.head > accu)
          maxWithTailRecursiveIfElse(list.tail, list.head)
        else
          maxWithTailRecursiveIfElse(list.tail, accu)
      }
    }
    maxWithTailRecursiveIfElse(list.tail, list.head)
  }

}
