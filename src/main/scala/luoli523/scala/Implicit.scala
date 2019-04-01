package luoli523.scala

// implicit用来给已有的class增加方法，包括给已有的String，File class增加方便的方法
object Implicit {

  class MyString(var str : String) {
    def increment = str.map( c => (c+1).toChar )
    def backwards = str.reverse
    // def capitalize= if (str.equals("luoli")) str.capitalize // 同名函数定义会导致compile错误，有二义性定义
    def capitalizeIfLuoli = if (str.equals("luoli")) str.capitalize
  }

  implicit def morePowerString(s : String) = new MyString(s)

  def main(args: Array[String]): Unit = {
    println("HAL".increment)

    var str = "hello, scala implicit"
    println(str.increment)

    str = "suineg a si iloul"
    println(str.backwards)

    println("luoli".capitalizeIfLuoli)
  }

}
