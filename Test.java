/* Java Functor Test;
 * (c) 2015, Lubomir I. Ivanov
 *
 * This source code is released in the public domain without waranty of any
 * kind. Providing credit to the authors is recommended but not mandatory.
 */


import fw.utils.Functor;

public class Test
{
	public static void main(String[] args)
	{
		Functor.setVerbose(true);
		Functor.setExitOnException(true);

		Test t1 = new Test();
		Functor f = new Functor(t1, "someMethod", String.class, String.class);
		String result = (String)f.invoke("hello", "world");
		System.out.println(result);

		f.setMethod(t1, "someVoidMethod");
		f.invoke();
		Test t2 = new Test();
		f.setMethod(t2, "someVoidMethod");
		f.invoke();

		f.dispose();
		f = null;
	}

	public String someMethod(String s1, String s2)
	{
		return s1 + s2;
	}

	public void someVoidMethod()
	{
		System.out.println("someVoidMethod");
	}
}
