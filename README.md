# example-gosuc-inverted
Example usage of the Gosu compiler component for Maven's compiler plugin.

This example **inverts** typical Java-Gosu interplay.  In this repo, a Java class will instantiate Gosu classes in the project directly from bytecode (i.e. not via reflection).

## Assumptions
This project assumes a hybrid Gosu project with a mixture of Gosu and Java classes and tests.

With regards to ordering:
* Gosu classes will be compiled prior to Java classes.
* Gosu tests will execute prior to Java tests.

## Caveats

1. No IDE support - any Java IDE parser will report errors in FooTest, saying that Gosu classes IBar and IBarEnhancement do not exist.
2. Avoid parameterized types - calls to generic Gosu methods are ugly.

Also, note the static invocation of the property on IBarEnhancement at [FooTest](src/test/java/example/FooText.java):31

## POM configuration
This is more or less identical to the [example-gosuc-hybrid](https://github.com/gosu-lang/example-gosuc-hybrid) repository, which compiles Java-then-Gosu in a single module.

The major difference here is that the Gosu compilation and test compilation executions are attached to the `process-resources` and `process-test-resources` phases, respectively.
These two phases are immediate predecessors to `compile` and `test-compile` in the default maven lifecycle.

```
<execution>
  <id>Compile Gosu sources</id>
  <phase>process-resources</phase>
  <goals>
    <goal>compile</goal>
  </goals>
  <configuration>
    <compilerId>gosuc</compilerId>
    <excludes>
      <exclude>**/*.java</exclude>
    </excludes>
  </configuration>
</execution>
```

Further, while the Java compiler will only process \*.java classes, while the Gosu compiler will include all files in all source roots\*.  It is necesasry to explicitly exclude `**/*.java` from the Gosu compiler.

\* This is not intentional or desirable behavior, but rather a shortcoming of the maven compiler plugin and plexus compiler interface.

## Usage/Outcome
Executing `$ mvn compile` should produce the following output:
```
[INFO] --- build-helper-maven-plugin:1.9.1:add-source (add-source) @ example-gosuc-inverted ---
[INFO] Source directory: /home/kmoore/dev/gosu-lang/example-gosuc-inverted/src/main/gosu added.
...
[INFO] --- maven-compiler-plugin:3.3:compile (Compile Gosu sources) @ example-gosuc-inverted ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to /home/kmoore/dev/gosu-lang/example-gosuc-inverted/target/classes
[INFO] Adding Gosu JARs to compiler classpath
...
[INFO] --- maven-compiler-plugin:3.3:compile (default-compile) @ example-gosuc-inverted ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/kmoore/dev/gosu-lang/example-gosuc-inverted/target/classes

```

Executing `$ mvn test` should produce the following additional output:
```
[INFO] --- build-helper-maven-plugin:1.9.1:add-test-source (add-test-source) @ example-gosuc-inverted ---
[INFO] Test Source directory: /home/kmoore/dev/gosu-lang/example-gosuc-inverted/src/test/gosu added.
...
[INFO] --- maven-compiler-plugin:3.3:testCompile (Compile Gosu test sources) @ example-gosuc-inverted ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/kmoore/dev/gosu-lang/example-gosuc-inverted/target/test-classes
[INFO] Adding Gosu JARs to compiler classpath
...
[INFO] --- maven-compiler-plugin:3.3:testCompile (default-testCompile) @ example-gosuc-inverted ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/kmoore/dev/gosu-lang/example-gosuc-inverted/target/test-classes
...
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running example.FooTest
newing Foo
Gosu trumps Java!  Found argument: oh, inverted world
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.393 sec
Running example.NoOpGosuTest
Running test method:
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.279 sec

Results :

Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
```