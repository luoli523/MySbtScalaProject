package luoli523.scala

object FunctionArguments extends App {

  val sayHelloFunc = () => {
    println("Hello, Scala from funciton")
  }

  def sayHello() {
    println("Hello, Scala from method")
  }

  def oncePerSecond( callback : () => Unit, times : Int) {
    var counter = times
    while (counter > 0) {
      // Warning:(12, 7) a pure expression does nothing in statement position; you may be omitting necessary parentheses
      // 这里需要注意，对callback的调用，后面要加括号，不然就会报上述warning，因为这里callback是一个method引用
      // 实际上没有加括号的话相当于一个表达式，什么都没做，并没有方法调用
      callback()
      Thread.sleep(500)
      counter -= 1
    }
  }

  oncePerSecond(sayHello, 5)
  oncePerSecond(sayHelloFunc, 5)

  // 调用的时候直接传匿名函数
  oncePerSecond(() => {
    println("Hello, Scala")
  }, 5)

}
