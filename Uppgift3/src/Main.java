import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Node testSrc;
        Node testDest;

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter source node: ");

        String srcNode = myObj.nextLine();  // Read user input
        System.out.println("Enter destination node: ");
        String dstNode = myObj.nextLine();



        if(args.length < 1){
            System.out.println("Pretty please, input a file");
        } else {
            try {
                File file = new File(args[0]);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    nodes.add(new Node(line));
                }
                fr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(Node n : nodes){
            for(Node m : nodes){
                if(!(n.equals(m))){
                    if(hasEdge(n.getName(), m.getName())){
                        edges.add(new Edge(n, m));
                    }
                }
            }
        }


        if(findNode(srcNode, nodes) == null && findNode(dstNode, nodes) == null) {
            System.out.println("The words you gave are not valid ");
            System.exit(1);

        }

        Graph g = new Graph(edges, nodes);
        Node dest = findNode(dstNode, nodes);
        Node src = findNode(srcNode, nodes);
        g.dijkstras(src, dest);
        g.printPath(src, dest);
    }

    public static boolean hasEdge(String one, String two){
        int mistakes = 1;
        for(int i = 0; i < 4; i++){
            if(one.charAt(i) != two.charAt(i)){
                mistakes--;
            }
            if(mistakes < 0){
                return false;
            }
        }

        return true;
    }

    public static Node findNode(String name, List<Node> nodes){
        for(Node n : nodes){
            if(n.getName().equals(name)){
                return n;
            }
        }
        return null;
    }
}
