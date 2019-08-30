import org.scalatest.{Matchers, FunSuite, BeforeAndAfter}

/** @version 1.1.0 */
class HelloWorldTest extends FunSuite with Matchers with BeforeAndAfter {

  before {
    print("test begin")
  }

  test("Say Hi!") {
    HelloWorld.hello() should be ("Hello, World!")
    val hi = "hi"
    val exc = intercept[IndexOutOfBoundsException] {
      hi.charAt(-1)
    }
  }

  after {
    print("test finished")
  }
}
