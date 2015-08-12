package example

uses org.junit.Assert
uses org.junit.Test

class NoOpGosuTest {

  @Test
  function noOp() {
    print("Running test method: ")// + this.Class.Methods.first())
    Assert.assertTrue(1 + 1 == 2)
  }

}