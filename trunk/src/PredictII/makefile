all:
	@echo "nothin to do"
java:
	find . -name "*.java"|xargs javac

clean:
	find . -name "*.class"|xargs rm
cleanI:
	rm *java~
cleanA:
	rm *~
indent:
	dos2unix *.java
	myindent *.java
	rm *.class
