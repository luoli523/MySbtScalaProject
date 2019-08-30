import org.scalatest.{Matchers, FunSuite, BeforeAndAfter}

class TwoferTest extends FunSuite with Matchers {

  test("no name given") {
    Twofer.twofer() should be ("One for you, one for me")
  }

  test("Alice") {
    Twofer.twofer("Alice") should be ("One for Alice, one for me")
  }

  test("Bob") {
    Twofer.twofer("Bob") should be ("One for Bob, one for me")
  }

  /*
  test("null") {
    Twofer.twofer(null) should be ("One for Bob, one for me")
  }
  */
}