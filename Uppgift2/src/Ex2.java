/*
*
* Luka Alhonen, Amos Weckström
*
 */
import java.util.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Ex2 {
    
    public static void main(String[] args) throws CycleFoundException{
	
	LinkedList<Vertex> sortedOrder;
	Graf G=new Graf();
	
	Collection<Vertex> myGraph;

	// Read the graph 
	try {
	    myGraph = G.readGraph(args[0]);
	} catch (IOException | FileFormatException e) { // No file 
	    System.out.println("Error:" + e.getMessage());
	    return;
	} catch (ArrayIndexOutOfBoundsException e) { //No filename was given, print help message
	    System.out.println("This program prints the topological order of a given graph");
	    System.out.println("");
	    System.out.println("Usage: Ex2 filename");
	    return;
	}

	// Make an array of the nodes in the graph
	Vertex[] myGraphArray = myGraph.toArray(new Vertex[0]);

	// Do the topological sort on the array
	try {
	    sortedOrder=G.topSort(myGraphArray);
	} catch (CycleFoundException e) {         // Terminate if we find a cycle
	    System.out.println("The input graph in " + args[0] + " contains a cycle");
	    return;
	}
	G.printGraph(sortedOrder);
	G.printOrder(sortedOrder);	// Write the result of topological sort
	
    }
}


// Class for representing nodes in a graph
class Vertex{
	
    protected String name;                 // Name of the node
    protected int indegree;                // Indegree
    protected LinkedList<Vertex> Adj;      // Linked list of adjacent nodes
    protected int topNum;                  // Order in topological sort

    public Vertex(String name){
	this.name=name;
	this.indegree=0;
	this.Adj=new LinkedList<Vertex>();
	this.topNum=-1;
    }
    
    // Add an adjacent node
    public void connectTo(Vertex a){
	a.indegree++;        // Increment the indegree of node a
	Adj.add(a);          // Add it to the list of adjacent nodes
    }

}

class Graf {

	/* Read in a graph from a file.
	 */
	public static Collection<Vertex> readGraph(String fileName) throws IOException, FileFormatException {

		// Hash map for the nodes in the input graph
		HashMap<String, Vertex> input = new HashMap<String, Vertex>();
		String data = null;
		BufferedReader r = new BufferedReader(new FileReader(fileName));

		try {
			// Allow comments in the beginning of the file befor the [Vertex] tag
			while (!(data = r.readLine()).equalsIgnoreCase("[Vertex]")) ;

			// Read all vertex definitions
			while (!(data = r.readLine()).equalsIgnoreCase("[Edges]")) {
				if (data.trim().length() > 0) {  // Skip empty lines
					try {
						// Split the line into a comma separated list V1,V2, V3, etc
						String[] nodeNames = data.split(",");
						for (int i = 0; i < nodeNames.length; i++) {
							// Trim off whitepsace and add the node to the hash map
							input.put(nodeNames[i].trim(), new Vertex(nodeNames[i].trim()));
						}
					} catch (Exception e) {   // Something wrong in the graph file
						r.close();
						throw new FileFormatException("Error in vertex definitions");
					}
				}
			}

		} catch (NullPointerException e1) {  // The input file has wrong format
			throw new FileFormatException(" No [Vertex] or [Edges] section found in the file " + fileName);
		}

		// Read all edge definitions
		while ((data = r.readLine()) != null) {
			if (data.trim().length() > 0) {  // Skip empty lines
				try {
					String[] edges = data.split(",");            // Edges are comma separated pairs e1:e2
					for (int i = 0; i < edges.length; i++) {        // For all edges
						String trimmedEdge = edges[i].trim();  // Trim off whitespace
						String[] edgePair = trimmedEdge.split(":"); //Split edge components v1:v2
						// Make v2 adjacent to v1 (and trim them too)
						input.get(edgePair[0].trim()).connectTo(input.get(edgePair[1].trim()));
					}

				} catch (Exception e) { //Something is wrong, Edges should be in format v1:v2
					r.close();
					throw new FileFormatException("Error in edge definition");
				}
			}
		}
		r.close();  // Close the reader

		return input.values(); //Return the vertices as a Collection
	}


	// Topological sort algorithm
	public LinkedList<Vertex> topSort(Vertex[] allNodes) throws CycleFoundException {
		LinkedList<Vertex> result = new LinkedList<Vertex>();
		Queue<Vertex> q = new LinkedList<>();
		int counter = 0;
		for (Vertex a : allNodes) {
			if (a.indegree == 0) {
				q.add(a);
				result.add(a);
			}
		}
		while (!q.isEmpty()) {
			Vertex b = q.remove();
			b.topNum = ++counter;

			for (Vertex vert : b.Adj) {
				if (--vert.indegree == 0) {
					q.add(vert);
					result.add(vert);
				}
			}
		}
		if (counter != allNodes.length) {
			throw new CycleFoundException("You done messed up boy");
		}


		return result;
}

    // Print the graph as an incident list
    public void printGraph (Collection<Vertex> someGraph) {
	/* You implement this */
		for(Vertex v : someGraph){
			String denssi = "";
			Iterator it = v.Adj.iterator();
			while(it.hasNext()){
				Vertex b = (Vertex) it.next();
				denssi += b.name;
				if(it.hasNext()){
					denssi += " --> ";
				}
			}
			System.out.println(v.name + ": [" + denssi + "]");
		}
    }
    
    // Print out the topoligical order
    public void printOrder(LinkedList<Vertex> result) {
	/* You implement this */
		Iterator it = result.iterator();
		String denssi = "";
		while(it.hasNext()){
			Vertex v = (Vertex) it.next();
			denssi += v.name + "[" + v.topNum + "]";
			if(it.hasNext()){
				denssi += ", ";
			}
		}
		System.out.println(denssi);

    }
    
}

@SuppressWarnings("serial")
class FileFormatException extends Exception { //Input file has the wrong format
    public FileFormatException(String message) {
	super(message);
    }
    
}

@SuppressWarnings("serial")
class CycleFoundException extends Exception {
    public CycleFoundException(String message) {
    }
}
