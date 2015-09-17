package example;

public class Foo implements IBar {

  @Override
  public String doSomething(String arg) {
    System.out.println(IBarEnhancement.getMeaningOfLife(this)); //note, this will show as a potential compiler error in the IDE, but the code will execute properly in Maven.
    return("Gosu trumps Java!  Found argument: " + arg);
  }

}
