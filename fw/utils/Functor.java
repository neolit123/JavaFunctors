/* Java Functor;
 * (c) 2015, Lubomir I. Ivanov
 *
 * usage:
 * Functor.setExitOnException(true);
 * Functor.setVerbose(true);
 * Functor f = new Functor(someInstance, "testMethod", String.class);
 * f.setMethod(someInstance, "someOtherMethod, String.class, String.class);
 * System.out.println(f.toString());
 * String result = (String)f.invoke(string1, string2);
 * f.dispose();
 *
 * This source code is released in the public domain without waranty of any
 * kind. Providing credit to the authors is recommended but not mandatory.
 */

package fw.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class Functor
{
	private static final String SEP = ", ";
	private static Boolean exitOnException = false;
	private static Boolean verbose = false;

	private Object target = null;
	private Method method = null;
	private Class<?>[] arguments = null;

	public static void setExitOnException(Boolean value)
	{
		exitOnException = value;
	}

	public static void setVerbose(Boolean value)
	{
		verbose = value;
	}

	public Functor(Object _target, String _methodName, Class... _args)
	{
		setMethod(_target, _methodName, _args);
	}

	public void setMethod(Object _target, String _methodName, Class... _args)
	{
		target = _target;
		arguments = _args;
		try {
			method = target.getClass().getMethod(_methodName, _args);
			if (verbose)
				System.out.println("Functor.setMethod(): " + formatVerboseString(_args));
		} catch (Exception e) {
			e.printStackTrace();
			if (exitOnException)
				System.exit(1);
		}
	}

	public Object getTarget()
	{
		return target;
	}

	public Method getMethod()
	{
		return method;
	}

	public Class<?>[] getArguments()
	{
		return arguments;
	}

	public Object invoke(Object... _args)
	{
		if (verbose)
			System.out.println("Functor.invoke(): " + formatVerboseString(_args));
		Object ret = null;
		try {
			ret = method.invoke(target, _args);
		} catch (Exception e) {
			e.printStackTrace();
			if (exitOnException)
				System.exit(1);
		}
		return ret;
	}

	private String formatVerboseString(Object[] _args)
	{
		return super.toString() + SEP + target.toString() + SEP +
			method.getName() + Arrays.toString(_args);
	}

	@Override
	public String toString()
	{
		return formatVerboseString(arguments);
	}

	public void dispose()
	{
		if (verbose)
			System.out.println("Functor.dispose(): " + super.toString());
		method = null;
		target = null;
		arguments = null;
	}
}
