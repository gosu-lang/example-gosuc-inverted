package example;

public class Foo implements IBar {

  @Override
  public String doSomething(String arg) {
    return("Gosu trumps Java!  Found argument: " + arg);
  }

}
