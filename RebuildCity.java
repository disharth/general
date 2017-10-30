package pramod.shukla.graph;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * https://www.hackerrank.com/challenges/torque-and-development/problem
 * Rebuilding the city with minimum Cost adn ensure everyone has access to Library.
 */
public class RebuildCity {

    public static void main(String[] args) throws FileNotFoundException {

        try(BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            int querySize = Integer.valueOf(in.readLine());
            while(querySize >0){
                String metaLine = in.readLine();
                String[] meta =  metaLine.split(" ");
                int noOfCities = Integer.valueOf(meta[0]);
                int noOfRoads = Integer.valueOf(meta[1]);
                long costOfLib = Long.valueOf(meta[2]);
                long costOfRoad = Long.valueOf(meta[3]);
                Query query = new Query(noOfCities,noOfCities,costOfLib,costOfRoad);
                for (int j = 0; j <noOfRoads ; j++) {
                    String edgeLine = in.readLine();
                    String[] edges =  edgeLine.split(" ");
                    query.addEdgeToGraph(Integer.valueOf(edges[0]) -1,Integer.valueOf(edges[1]) -1);
                }
                query.minimizeRebuildCost();
                System.out.println(query.getFinalCost());
                querySize--;
            }
        }catch (IOException e){
            System.out.println("Exception ...");
        }
    }


}

/**
 * This class Represents the Query details.
 */
class Query {

    int noOfCities , noOfroads ;
    long costOfLib , costOfRoad;
    Graph graph;
    private boolean[] marked;
    long cost;

    public Query(int noOfCities , int noOfroads , long costOFLib , long costOfRoad ) {
        this.noOfCities = noOfCities;
        this.noOfroads = noOfroads;
        this.costOfRoad = costOfRoad;
        this.costOfLib = costOFLib;
        graph = new Graph(noOfCities);
        marked = new boolean[graph.V()];
    }

    public void addEdgeToGraph(int s , int t){
        graph.addEdge(s,t);
    }


    public void minimizeRebuildCost(){

        for (int v = 0; v < graph.V(); v++) {
            if (!marked[v]) {
                // initial Cost to build a Library in each connected component.
                cost = cost +costOfLib;
                dfs(v);
            }
        }

    }

    public long getFinalCost(){
        return cost;
    }

    private void dfs(int v) {
        marked[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                // Make sure the cost is minimum after every iteration.
                cost = Math.min((cost + costOfLib) , (cost + costOfRoad) );
                dfs( w);
            }
        }
    }


}

/**
 * This Represents the Graph.
 *
 */
class Graph {

    private final int V;
    private List<Integer>[] adj;

    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        adj = (List<Integer>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }


    public int V() {
        return V;
    }


    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }


    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

}


