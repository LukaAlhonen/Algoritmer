import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        try {
            File file = new File(args[0]);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                nodes.add(new Node(line));
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
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
}
