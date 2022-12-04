public class Node {
    private String name;
    private boolean known;
    private int dist = Integer.MAX_VALUE;
    private Node path;
    // Constructor
    Node(String name){
        this.name = name;
    }
    Node(int dist){ this.dist = dist; }
    Node(){}
    // Getters
    public String getName(){
        return name;
    }
    public boolean isKnown() { return known; }
    public int getDist() { return dist; }
    public Node getPath() { return path; }
    //Setters
    public void setKnown(boolean barry) { known = barry; }
    public void setDist(int i) { dist = i; }
    public void setPath(Node n){ path = n; }
}
