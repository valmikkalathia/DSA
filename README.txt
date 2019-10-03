HITS and PageRank Algorithm implementation using Java

---------------------------------------------------------------------------------------------------------------------------------------
HITS (hits9400.java)

	This program calculates authority and hub values of edges (webpages) in the network which is implemented as a directed graph here.

PageRank (pgrk9400.java)

	This Program calculates PageRank values of edges (webpages) in the network which is implemented as a directed graph here. 

---------------------------------------------------------------------------------------------------------------------------------------
Usage 

HITS 

	Compile the hits algorithm file using this command : javac hits9400.java
	Use following command to run the program : java hits9400 iterations initialValue filename

PageRank

	Compile the hits algorithm file using this command : javac pgrk9400.java
    Use following command to run the program : java pgrk9400 iterations initialValue filename

---------------------------------------------------------------------------------------------------------------------------------------

itrations

	For 0th iteration, program will be executed only till the iteration values will converge with errorrate of 0.00001 and only values for
	the last iteration will be printed.

	For other iterations (1,2,3....) , all the iterations will be calculated and printed.

	For graphs with more than 10 edges, program will display only last iteration value with assumption iterations=0 and initialValue = -1

---------------------------------------------------------------------------------------------------------------------------------------

initialValue

	Program will accept only 0, 1, -1 or -2 as valid initialValue. If user enters any other values then program will be terminated.
	For value 0, auth/hub or pagerank values will be initialized as 0 for all nodes.
	For value 1, auth/hub or pagerank values will be initialized as 1 for all nodes.
	For value -1, auth/hub or pagerank values will be initialized as 1/e for all nodes where e is total # of edges.
	For value -1, auth/hub or pagerank values will be initialized as 1/(sqrt(e)) for all nodes where e is total # of edges.

---------------------------------------------------------------------------------------------------------------------------------------

filename

	Sample directed graph representation is provided to the program from this txt file.
	First line will have 2 integers which represents # of edges and # of vertices in the graph respectively.
	Remaining lines represents the edges from one node to other node in the graph.   