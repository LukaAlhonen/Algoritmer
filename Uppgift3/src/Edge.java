public class Edge {
    private Node src;
    private Node dest;
    Edge(Node src, Node dest){
        this.src = src;
        this.dest = dest;
    }

    public Node Src(){ return src; }

    public Node Dest(){ return dest; }
}
