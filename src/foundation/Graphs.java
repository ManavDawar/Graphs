package foundation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Graphs {

	static class Edge {
		int nbr;
		int wt;

		public Edge(int nbr, int wt) {
			this.nbr = nbr;
			this.wt = wt;
		}

	}

	public static ArrayList<ArrayList<Edge>> graph = new ArrayList<>();

	public static void addEdge(ArrayList<ArrayList<Edge>> graph, int v1, int v2, int wt) {
		graph.get(v1).add(new Edge(v2, wt));
		graph.get(v2).add(new Edge(v1, wt));
	}

	public static void display(ArrayList<ArrayList<Edge>> graph) {
		System.out.println("-----------------------------------------");

		for (int v = 0; v < graph.size(); v++) {

			System.out.print(v + " -> ");

			for (int n = 0; n < graph.get(v).size(); n++) {
				Edge ne = graph.get(v).get(n);
				System.out.print("[ " + ne.nbr + " @ " + ne.wt + " ]" + ", ");
			}
			System.out.println(" . ");
		}

		System.out.println("-----------------------------------------");

	}
//	for (int v = 0; v < 7; v++) {
//		graph.add(new ArrayList<>());
//	}
//	addEdge(graph, 0, 1, 10);
//	addEdge(graph, 1, 2, 10);
//	addEdge(graph, 2, 3, 10);
//	addEdge(graph, 0, 3, 40);
//	addEdge(graph, 3, 4, 2);
//	addEdge(graph, 4, 5, 3);
//	addEdge(graph, 5, 6, 3);
//	addEdge(graph, 4, 6, 8);
//	display(graph);

	public static boolean haspath(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int src, int des) {
		if (src == des) {
			return true;
		}

		visited[src] = true;

		for (int n = 0; n < graph.get(src).size(); n++) {
			Edge ne = graph.get(src).get(n);
			if (visited[ne.nbr] == false) {
				boolean path = haspath(graph, visited, ne.nbr, des);
				if (path == true) {
					return true;
				}
			}
		}

		return false;
	}
//	boolean[] visited = new boolean[graph.size()];
//	System.out.println(haspath(graph,visited, 0, 6));

	public static void printallpath(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int src, int des, String psf,
			int wsf) {
		if (src == des) {
			System.out.println(psf + des + " @ " + wsf);
		}

		visited[src] = true;
		for (int n = 0; n < graph.get(src).size(); n++) {
			Edge ne = graph.get(src).get(n);
			if (visited[ne.nbr] == false) {
				printallpath(graph, visited, ne.nbr, des, psf + src + " ", wsf + ne.wt);
			}

		}
		visited[src] = false;

	}
//	boolean[] visited = new boolean[graph.size()];
//	printallpath(graph, visited, 0, 6, "",0);

	static int spw;
	static int lpw;
	static int fpw;
	static int cpw;

	static String sp;
	static String lp;
	static String cp;
	static String fp;

	public static void multisolver(ArrayList<ArrayList<Edge>> graph, int s, int d, boolean[] visited, int cv, int fv) {
		sp = "";
		lp = "";
		cp = "";
		fp = "";

		spw = Integer.MAX_VALUE;
		lpw = Integer.MIN_VALUE;
		fpw = Integer.MIN_VALUE;
		cpw = Integer.MAX_VALUE;

		multisolver(graph, visited, s, d, cv, fv, s + "", 0);
		System.out.println("Shortest " + sp + " @ " + spw);
		System.out.println("Logest " + lp + " @ " + lpw);
		System.out.println("Ceil " + cp + " @ " + cpw);
		System.out.println("Floor " + fp + " @ " + fpw);
	}

	private static void multisolver(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int s, int d, int cv, int fv,
			String psf, int wsf) {
		if (s == d) {
			if (wsf < spw) {
				spw = wsf;
				sp = psf;
			}
			if (wsf > lpw) {
				lpw = wsf;
				lp = psf;
			}

			if (wsf > cv) {
				if (wsf < cpw) {
					cpw = wsf;
					cp = psf;
				}
			}

			if (wsf < fv) {
				if (wsf > fpw) {
					fpw = wsf;
					fp = psf;
				}
			}
			return;
		}

		visited[s] = true;
		for (int n = 0; n < graph.get(s).size(); n++) {
			Edge ne = graph.get(s).get(n);
			if (visited[ne.nbr] == false) {
				multisolver(graph, visited, ne.nbr, d, cv, fv, psf + ne.nbr, wsf + ne.wt);
			}
		}
		visited[s] = false;
	}
//	addEdge(graph, 2, 5, 5);
//
//	boolean[] visited = new boolean[graph.size()];
//	printallpath(graph, visited, 0, 6, "", 0);
//
//	boolean[] visited1 = new boolean[graph.size()];
//	multisolver(graph, 0, 6, visited1, 51, 51);

	static PriorityQueue<pair> pq;

	static class pair implements Comparable<pair> {
		int data;
		String psf;

		public pair(int data, String psf) {
			this.data = data;
			this.psf = psf;
		}

		@Override
		public int compareTo(pair o) {
			return this.data - o.data;
		}

	}

	public static void Kthlargest(ArrayList<ArrayList<Edge>> graph, int s, int d, int k) {
		boolean[] visited = new boolean[graph.size()];
		pq = new PriorityQueue<>();
		klargestHelper(graph, visited, s, d, k, s + "", 0);

		while (pq.size() > 0) {
			pair rem = pq.remove();
			System.out.println(rem.psf + "@" + rem.data);
		}

	}

	private static void klargestHelper(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int s, int d, int k,
			String psf, int wsf) {

		if (s == d) {
			if (pq.size() < k) {
				pq.add(new pair(wsf, psf));
			} else {
				if (pq.peek().data < wsf) {
					pq.remove();
					pq.add(new pair(wsf, psf));
				}
			}
		}

		visited[s] = true;

		for (int n = 0; n < graph.get(s).size(); n++) {
			Edge ne = graph.get(s).get(n);
			if (visited[ne.nbr] == false) {
				klargestHelper(graph, visited, ne.nbr, d, k, psf + ne.nbr, wsf + ne.wt);
			}
		}
		visited[s] = false;

	}

//			Kthlargest(graph, 0, 6, 2);

	public static void hamiltonianpath(ArrayList<ArrayList<Edge>> graph, int src) {

		boolean[] visited = new boolean[graph.size()];
		hamiltonianHelper(graph, visited, src, new ArrayList<Integer>());
//		psf is used as arraylist becuase we need to compare its size and know 
//		if all the vertices are covered we could have used a count so far.
	}

	private static void hamiltonianHelper(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int src,
			ArrayList<Integer> psf) {
		if (psf.size() == graph.size() - 1) {
			for (int i = 0; i < psf.size(); i++) {
				System.out.print(psf.get(i));
			}
			System.out.print(src);
//			if src and original src has a edge between them then it is a cycle 
			int first = psf.get(0);
			for (int i = 0; i < graph.get(first).size(); i++) {
				Edge ne = graph.get(first).get(i);
				if (src == ne.nbr) {
					System.out.println("*");
					return;
				}
			}
			System.out.println(".");
			return;
		}

		visited[src] = true;
		for (int i = 0; i < graph.get(src).size(); i++) {
			Edge ne = graph.get(src).get(i);
			if (visited[ne.nbr] == false) {
				psf.add(src);
				hamiltonianHelper(graph, visited, ne.nbr, psf);
				psf.remove(psf.size() - 1);
			}
		}

		visited[src] = false;
	}
//	addEdge(graph, 2, 5, 5);
//	hamiltonianpath(graph, 0);

	static int counter=0;
	public static void KnightsTour(int[][] chess, int r, int c, int move) {

		if(move==chess.length*chess[0].length-1) {
			chess[r][c]=move;
			++counter;
			System.out.println("------------"+counter+"------------");
			for(int i=0;i<chess.length;i++) {
				for(int j=0;j<chess[0].length;j++) {
					System.out.print(chess[i][j]+" ");
				}
				System.out.println();
			}
			
			chess[r][c]=-1;
			return;
		}
		chess[r][c] = move;
		if (isKnightValid(chess, r - 2, c + 1)) {
			KnightsTour(chess, r - 2, c + 1, move + 1);
		}
		if (isKnightValid(chess, r - 1, c + 2)) {
			KnightsTour(chess, r - 1, c + 2, move + 1);
		}
		if (isKnightValid(chess, r + 1, c + 2)) {
			KnightsTour(chess, r + 1, c + 2, move + 1);
		}
		if (isKnightValid(chess, r + 2, c + 1)) {
			KnightsTour(chess, r + 2, c + 1, move+1);
		}
		if (isKnightValid(chess, r + 2, c - 1)) {
			KnightsTour(chess, r + 2, c - 1, move+1);
		}
		if (isKnightValid(chess, r + 1, c - 2)) {
			KnightsTour(chess, r + 1, c - 2, move+1);
		}
		if (isKnightValid(chess, r - 1, c - 2)) {
			KnightsTour(chess, r - 1, c - 2, move+1);
		}
		if (isKnightValid(chess, r - 2, c - 1)) {
			KnightsTour(chess, r - 2, c - 1, move+1);
		}

		chess[r][c] = -1;

	}

	private static boolean isKnightValid(int[][] chess, int r, int c) {
		if (r < 0 || r >= chess.length || c < 0 || c >= chess[0].length || chess[r][c] != -1) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		for (int v = 0; v < 7; v++) {
			graph.add(new ArrayList<>());
		}

		addEdge(graph, 0, 1, 10);
		addEdge(graph, 1, 2, 10);
		addEdge(graph, 2, 3, 10);
		addEdge(graph, 0, 3, 40);
		addEdge(graph, 3, 4, 2);
		addEdge(graph, 4, 5, 3);
		addEdge(graph, 5, 6, 3);
		addEdge(graph, 4, 6, 8);
		boolean[] visited = new boolean[graph.size()];
		int[][] arr = new int[5][5];
		for (int i = 0; i < arr.length; i++) {
			Arrays.fill(arr[i], -1);
		}

		KnightsTour(arr, 1, 1, 0);
	}

}
