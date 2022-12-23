///////////////////////////
Running Program
///////////////////////////

Compile using javac:
    javac Main.java

Run:
    java Main (see more below)

Main class can either accept args from cmd for example:
    "java Main routeFile requestFile"

or it can be run without args, default file name is route.txt and request.txt:
    "java Main"

the two input files is provided
main outputs to output.txt

Make sure the route.txt and request.txt are in the SAME DIRECTORY as the executable, else it cannot be found, (user error)
Program has been compiled and run successfully locally and online IDE. If running into error, please check your compiler.
Make sure terminal/cmd can find JVM in environment variable to compile and run project

///////////////////////////
Source Code
///////////////////////////

LinkedList.java:
	Source code for any linked list - based object for the program, has CRUD methods
	and any method overloads for different uses

NodeCityList.java:
	Extends linked list, used as the source to other adjacent cities
	CRUD implemented and overloads, super()

Graph.java:
	Graph class, containing a linked list of NodeCityList and implements DFS using stack

StackG.java:
	Named StackG to avoid using java.util.stack
	Implemented using ArrayList
	Implemented CRUD methods

Main.java:
	Main running file

route.txt: input file containing connection between cities
request.txt: each line request a flight from origin to destination and sort by type

output.txt is the output of the program
