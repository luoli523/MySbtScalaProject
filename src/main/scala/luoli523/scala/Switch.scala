package luoli523.scala

import scala.annotation.switch

object Switch extends App {

  // 对于以下这种闭环switch,用match来实现的场景，最好加上@switch annotation
  // 这样scala编译器会将其优化为一个tableswitch或者lookupswitch，并且为逻辑错误隐患提供编译警告
  val month = (x : Int) => (x : @switch) match {
    case 1 => "January"
    case 2 => "February"
    case 3 => "March"
    case 4 => "April"
    case 5 => "May"
    case 6 => "June"
    case 7 => "July"
    case 8 => "August"
    case 9 => "September"
    case 10 => "October"
    case 11 => "November"
    case 12 => "December"
    case _ => "Invalid month" // the default, catch-all
  }

  println(month(2))
  println(month(12))
  println(month(13))

}
