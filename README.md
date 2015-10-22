### MOTIVATION

Functors (or function objects) are missing as a built-in feature in Java
and the suggested methods of doing callbacks is either by using Interfaces
or Lambdas.

### SOLUTIONS

Since SE 7 the Method class is available, which allows reflection of class
instance methods:

```
import java.lang.reflect.Method;

// declare some class with a method
public class SomeClass
{
	public String methodName(String str1, String str2)
	{
		return str1 + str2;
	}
}

...

// create an instance of SomeClass
SomeClass instance = new SomeClass();

// prepare the arguments for the method search
Class<?>[] args = { String.class, String.class };

// obtain a Method instance
Method method = instance.getClass().getMethod("methodName", args);

// invoke with arguments; the result has to be casted explicitly!
Sring result = (String)method.invoke("hello", "world");
```

### IMPLEMENTATION

Based on that, this Github project includes the fw.utils.Functor class which
has the following usage:

```
// global flag; call exit(1) if an exception is caught
Functor.setExitOnException(true);

// global flag; print useful information to stdout
Functor.setVerbose(true);

// creates a Functor.
// the first argument is the target instance, the second is the method name
// as String and the third is a vararg of the method argument classes.
// if the method does not accept arguments, pass nothing.
Functor f = new Functor(someInstance, "testMethod", String.class);

// set a new method; same arguments as the constructor
f.setMethod(someInstance, "someOtherMethod, String.class, String.class);

// print the Functor object to stdout
System.out.println(f.toString());

// get the target instance
Object target = f.getTarget();

// get the method
Method method = f.getMethod();

// get the argument list as a Class[]
Class<?>[] arguments = f.getArguments();

// invoke the wrapped method; argument list should be the same as the actual
// method. if the method does not accept arguments, pass nothing.
String result = (String)f.invoke(string1, string2);

// dispose of the Functor object
f.dispose();
f = null;
```

### NOTES

The class should be thread safe as long as you don't call the static methods
setExitOnException() or setVerbose() from multiple threads at the same time.

Beware that usage of Functors can confuse the readers of your source code and
make it prone to runtime errors. However, they do add great flexibility
and are pretty much re-usable via setMethod().

Instead, Interfaces are the safer and more readable way of organizing
a framework, but can add a lot of extra lines of code if you have a lot of
callbacks between different methods, as each method relation may require
a separate Interface definition.

### LICENSE

The source code in this project is released in the public domain without
waranty of any kind. Providing credit to the authors is recommended but
not mandatory.
