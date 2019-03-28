package luoli523.scala.list

import java.util

import scala.collection.mutable

object ListEx extends App {

  // ++ 用来合并两个Collection的操作
  var x = List(1)
  var y = List(2)
  x = x ++ y
  assert(x.length == 2)
  println(x) // List(1, 2)
  x = x ++ Array(3, 4)
  assert(x.isInstanceOf[List[Any]]) // List(1, 2, 3, 4),注意这里的返回值类型是List，而不是Array
  println(x)

  // ++: 同样是用来合并两个Collection的操作,跟++的区别在于，++: 操作的返回值类型取决于右操作数的类型
  var z = x ++: mutable.LinkedList(5)
  println(z) // LinkedList(1, 2, 3, 4, 5)

  // +: 和 :+
  x = List(1)
  // +: 是 A copy of the list with an element prepended.
  x = 2 +: x
  println(x) // List(2, 1)
  // :+ 是 A copy of this list with an element appended
  x = x :+ 3
  println(x) // List(2, 1, 3)


  // :: Adds an element at the beginning of this list.
  x = 0 :: x
  println(x) // List(0, 2, 1, 3)
  x = x.::(4)
  println(x) // List(4, 0, 2, 1, 3)


  // /: 从左向右fold操作
  x = List(1,2,3,4)
  val sumStartWithFive = (5 /: x)(_ + _)
  println(s"x = List(1,2,3,4)\n(5 /: x)(_ + _) = ${sumStartWithFive}")

  val abcd = List("a", "b", "c", "d")
  var abcdFold = ("x" /: abcd)(_ + _)
  println(s"abcd = List(a, b, c, d)\n('x' /: abcd)(_ + _) = ${abcdFold}")

  // :\ 从右向左fold操作
  abcdFold = (abcd :\ "x")(_ + _)
  println(s"abcd = List(a, b, c, d)\n(abcd :\\ 'x')(_ + _) = ${abcdFold}")
}
