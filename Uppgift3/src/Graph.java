import java.util.*;

public class Graph {
    private Map<Node, Set<Node>> adj_list;
    private static Set<Node> unsetteled = new HashSet<>();
    private static List<String> path = new ArrayList<>();

    Graph(List<Edge> edges, List<Node> nodes){
        adj_list = new HashMap<>();
        for(Node n : nodes){
            unsetteled.add(n);
            adj_list.put(n, new HashSet<>());
            for(Edge e : edges){
                if(e.Src() == n){
                    adj_list.get(n).add(e.Dest());
                }
            }
        }
    }



    public void printGraph(){
        for(Node n : adj_list.keySet()){
            System.out.print(n.getName() + " [ ");
            for(Node m : adj_list.get(n)){
                System.out.print(", " + m.getName());
            }
            System.out.println(" ]");
        }
    }

    public void dijkstras (Node src, Node dest){
        src.setKnown(true);
        unsetteled.remove(src);
        for(Node n : adj_list.get(src)){
            int newDist = src.getDist() + 1;
            if(n.getDist() > newDist){
                n.setDist(newDist);
                n.setPath(src);
            }

        }
        Node shortPath = getShortestDist();
        if(unsetteled.size() == 0){
            return;
        }
        dijkstras(shortPath, dest);
    }

    public static Node getShortestDist(){
        Node smallest = new Node();
        for(Node n : unsetteled){
            if(n.getDist() <= smallest.getDist()){
                smallest = n;
            }
        }
        return smallest;
    }

    public static void printPath(Node src, Node dest){

        if (!dest.equals(src)) {
            path.add(dest.getName());
            printPath(src, dest.getPath());
        }else {
            System.out.println(src.getName());
            for (int i = path.size()-1; i > -1; i--) {
                System.out.println(path.get(i));
            }
        }
    }
}
