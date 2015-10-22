all: Test.class

Test.class:
	javac Test.java

run: Test.class
	java Test

clean:
	rm Test.class && rm ./fw/utils/Functor.class
