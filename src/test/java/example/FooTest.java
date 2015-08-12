package example;

import org.junit.Test;

import static org.junit.Assert.*;

public class FooTest {

  @Test
  public void makeAFoo() {
    System.out.println("newing Foo");
    Foo foo = new Foo();
    assertNotNull(foo);

    assertTrue(foo instanceof IBar);

    System.out.println(foo.doSomething("oh, inverted world"));

    assertEquals(42, IBarEnhancement.getMeaningOfLife(foo));
  }

}
