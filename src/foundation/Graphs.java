package foundation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;




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

	static void addEdge(ArrayList<ArrayList<Edge>> graph, int v1, int v2, int w) {

		graph.get(v1).add(new Edge(v2, w));
		graph.get(v2).add(new Edge(v1, w));

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

	static int counter = 0;

	public static void KnightsTour(int[][] chess, int r, int c, int move) {

		if (move == chess.length * chess[0].length - 1) {
			chess[r][c] = move;
			++counter;
			System.out.println("------------" + counter + "------------");
			for (int i = 0; i < chess.length; i++) {
				for (int j = 0; j < chess[0].length; j++) {
					System.out.print(chess[i][j] + " ");
				}
				System.out.println();
			}

			chess[r][c] = -1;
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
			KnightsTour(chess, r + 2, c + 1, move + 1);
		}
		if (isKnightValid(chess, r + 2, c - 1)) {
			KnightsTour(chess, r + 2, c - 1, move + 1);
		}
		if (isKnightValid(chess, r + 1, c - 2)) {
			KnightsTour(chess, r + 1, c - 2, move + 1);
		}
		if (isKnightValid(chess, r - 1, c - 2)) {
			KnightsTour(chess, r - 1, c - 2, move + 1);
		}
		if (isKnightValid(chess, r - 2, c - 1)) {
			KnightsTour(chess, r - 2, c - 1, move + 1);
		}

		chess[r][c] = -1;

	}

	private static boolean isKnightValid(int[][] chess, int r, int c) {
		if (r < 0 || r >= chess.length || c < 0 || c >= chess[0].length || chess[r][c] != -1) {
			return false;
		}
		return true;
	}

//	int[][] arr = new int[5][5];
//	for (int i = 0; i < arr.length; i++) {
//		Arrays.fill(arr[i], -1);
//	}
//
//	KnightsTour(arr, 1, 1, 0);
	public static void astro(int[] arr1, int[] arr2, int n) {

		ArrayList<ArrayList<Edge>> graph1 = new ArrayList<>();
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			graph1.add(new ArrayList<>());
		}

		for (int i = 0; i < arr1.length; i++) {
			addEdge(graph1, arr1[i], arr2[i], 1);
		}
		list = gccint(graph1);
		int ans = 0;
		System.out.println(list);

		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				ans += list.get(i) * list.get(j);
			}
		}
		System.out.println(ans);

	}
//	int[] arr1 = { 2, 3, 8, 1, 6, 4 };
//	int[] arr2 = { 9, 8, 7, 7, 5, 2 };
//
//	astro(arr1, arr2, 10);

	static ArrayList<Integer> gccint(ArrayList<ArrayList<Edge>> graph) {

		ArrayList<Integer> list = new ArrayList<>();

		boolean[] visited = new boolean[graph.size()];

		for (int v = 0; v < graph.size(); v++) {
			if (visited[v] == false) {
				Integer comp = getconnectedcomponentint(graph, visited, v);
				list.add(comp);
			}
		}
		return list;
	}

	private static Integer getconnectedcomponentint(ArrayList<ArrayList<Edge>> graph, boolean[] visited, int v) {
		Integer comp = 0;
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(v);
		while (queue.size() > 0) {
			int rem = queue.removeFirst();
			if (visited[rem] == true) {
				continue;
			} else {
				visited[rem] = true;
			}
			comp++;
			for (int n = 0; n < graph.get(rem).size(); n++) {
				Edge ne = graph.get(rem).get(n);
				if (visited[ne.nbr] == false) {
					queue.add(ne.nbr);
				}
			}
		}
		return comp;
	}

	static ArrayList<String> gcc() {
		ArrayList<String> comps = new ArrayList<>();
		boolean[] visited = new boolean[graph.size()];

		for (int i = 0; i < graph.size(); i++) {
			if (visited[i] == false) {
				String comp = gccomponents(visited, i);
				comps.add(comp);
			}
		}
		return comps;
	}

	private static String gccomponents(boolean[] visited, int i) {
		String comp = "";
		Queue<Integer> q = new LinkedList<>();
		q.add(i);
		while (q.size() > 0) {
			Integer rem = q.remove();
			if (visited[rem] == true) {
				continue;
			}
			visited[rem] = true;
			comp += rem;

			for (int n = 0; n < graph.get(rem).size(); n++) {
				Integer ne = graph.get(rem).get(n).nbr;
				if (visited[ne] == false) {
					q.add(ne);
				}
			}
		}
		return comp;
	}

	static boolean isConnected() {
		int counter = 0;
		boolean[] visited = new boolean[graph.size()];
		for (int v = 0; v < graph.size(); v++) {
			if (visited[v] == false) {
				getconnectedcomponentint(graph, visited, v);
				counter++;

				if (counter == 2) {
					return false;
				}
			}
		}

		return true;
	}

	static boolean isCyclic() {
		boolean iscycle = false;
		boolean[] visited = new boolean[graph.size()];
		for (int v = 0; v < graph.size(); v++) {
			if (visited[v] == false) {
				iscycle = isCompCyclic(v, visited);
				if (iscycle) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isCompCyclic(int v, boolean[] visited) {
		Queue<Integer> q = new LinkedList<>();
		q.add(v);
		while (q.size() > 0) {
			Integer rem = q.remove();

			if (visited[rem] == true) {
				return true;
			}
			visited[rem] = true;

			for (int n = 0; n < graph.get(rem).size(); n++) {
				Integer ne = graph.get(rem).get(n).nbr;
				if (visited[ne] == false) {
					q.add(ne);
				}
			}

		}
		return false;
	}

	static class Bipair {
		int v;
		int l;

		public Bipair(int l, int v) {
			this.l = l;
			this.v = v;
		}
	}

	static boolean isBipartite(ArrayList<ArrayList<Edge>> graph) {
		int[] visited = new int[graph.size()];
		for (int v = 0; v < graph.size(); v++) {
			if (visited[v] == 0) {
				boolean isBipar = isBiHelper(graph, v, visited);
				if (!isBipar) {
					return false;
				}
			}
		}

		return true;
	}

	private static boolean isBiHelper(ArrayList<ArrayList<Edge>> graph2, int v, int[] visited) {
		Queue<Bipair> q = new LinkedList<>();
		q.add(new Bipair(0, v));

		while (q.size() > 0) {
			Bipair rem = q.remove();

			if (visited[rem.v] != 0) {
				if (visited[rem.v] % 2 != rem.l % 2) {
					return false;
				}
			}
			visited[rem.v] = rem.l;
			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);
				if (visited[ne.nbr] == 0) {
					q.add(new Bipair(rem.l + 1, ne.nbr));
				}
			}
		}
		return true;
	}
//	addEdge(graph, 0, 1, 10);
//	addEdge(graph, 1, 2, 10);
//	addEdge(graph, 2, 0, 10);
//	System.out.println(isBipartite(graph));

	static class Dipair implements Comparable<Dipair> {
		String psf;
		int v;
		int wsf;

		public Dipair(int v, String psf, int wsf) {
			this.v = v;
			this.psf = psf;
			this.wsf = wsf;
		}

		public int compareTo(Dipair o) {
			return this.wsf - o.wsf;
		}

		public String toString() {
			return v + " via " + psf + " @ " + wsf;
		}
	}

	static void Dijkstra(ArrayList<ArrayList<Edge>> graph, int src) {
		PriorityQueue<Dipair> pq = new PriorityQueue<>();
		pq.add(new Dipair(src, src + "", 0));
		boolean[] visited = new boolean[graph.size()];
		while (pq.size() > 0) {
			Dipair rem = pq.remove();
			if (visited[rem.v]) {
				continue;
			}
			visited[rem.v] = true;
			System.out.println(rem);
			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);
				if (visited[ne.nbr] == false) {
					pq.add(new Dipair(ne.nbr, rem.psf + " " + ne.nbr, rem.wsf + ne.wt));
				}
			}
		}
	}

	static class Ppair implements Comparable<Ppair> {
		int v;
		int av;
		int cost;

		public Ppair(int v, int av, int cost) {
			this.v = v;
			this.av = av;
			this.cost = cost;
		}

		@Override
		public int compareTo(Ppair o) {
			return this.cost - o.cost;
		}

		public String toString() {
			return v + " via " + av + " @ " + cost;
		}
	}

	public static void Prims(ArrayList<ArrayList<Edge>> graph, int src) {
		boolean[] visited = new boolean[graph.size()];
		PriorityQueue<Ppair> pq = new PriorityQueue<>();
		pq.add(new Ppair(src, src, 0));

		while (pq.size() > 0) {
			Ppair rem = pq.remove();

			if (visited[rem.v]) {
				continue;
			}
			visited[rem.v] = true;
			System.out.println(rem);
			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);
				if (!visited[ne.nbr]) {
					pq.add(new Ppair(ne.nbr, rem.v, ne.wt));
				}
			}
		}
	}

// this is also prims if you are asked to construct the min spanning tree 
	static class MstHelper implements Comparable<MstHelper> {
		int v;
		int parent;
		int cost;

		public MstHelper(int v, int parent, int cost) {
			this.v = v;
			this.parent = parent;
			this.cost = cost;
		}

		@Override
		public int compareTo(MstHelper arg0) {
			return this.cost - arg0.cost;
		}

		public String toString() {
			return v + " via " + parent + " @" + cost;
		}
	}

	public static ArrayList<ArrayList<Edge>> Mst(ArrayList<ArrayList<Edge>> graph, int src, boolean[] visited) {

		ArrayList<ArrayList<Edge>> mygraph = new ArrayList<>();

		for (int i = 0; i < graph.size(); i++) {
			mygraph.add(new ArrayList<>());
		}

		PriorityQueue<MstHelper> queue = new PriorityQueue<>();

		queue.add(new MstHelper(src, -1, 0));
		while (queue.size() > 0) {
			MstHelper rem = queue.peek();
			queue.remove();

			if (visited[rem.v] == true) {
				continue;
			} else {
				visited[rem.v] = true;
			}

			if (rem.parent != -1) {
				addEdge(mygraph, rem.v, rem.parent, rem.cost);
			}

			for (int n = 0; n < graph.get(rem.v).size(); n++) {
				Edge ne = graph.get(rem.v).get(n);

				if (visited[ne.nbr] == false) {
					queue.add(new MstHelper(ne.nbr, rem.v, ne.wt));
				}
			}
		}

		return mygraph;
	}

	static void TopologicalSorMatser(ArrayList<ArrayList<Edge>> graph) {

		boolean[] visited = new boolean[graph.size()];
		LinkedList<Integer> stack = new LinkedList<>();
		for (int i = 0; i < graph.size(); i++) {
			if (visited[i] == false) {
				TopologicalSorHelper(graph, visited, stack, i);

			}
		}

		while (stack.size() > 0) {
			System.out.print(stack.pop() + " ");
		}

	}

	
	private static void TopologicalSorHelper(ArrayList<ArrayList<Edge>> graph, boolean[] visited,
			LinkedList<Integer> stack, int src) {

		visited[src] = true;

		for (int i = 0; i < graph.get(src).size(); i++) {
			Edge ne = graph.get(src).get(i);

			if (visited[ne.nbr] == false) {
				TopologicalSorHelper(graph, visited, stack, ne.nbr);
			}
		}
		stack.push(src);
	}


	static void addEdgeSingle(ArrayList<ArrayList<Edge>> graph, int v1, int v2, int w) {

		graph.get(v1).add(new Edge(v2, w));

	}
	

	static class Kedge implements Comparable<Kedge> {
		int v1;
		int v2;
		int w;

		public Kedge(int v1, int v2, int w) {
			this.v1 = v1;
			this.v2 = v2;
			this.w = w;

		}

		@Override
		public int compareTo(Kedge arg0) {
			return this.w - arg0.w;
		}

	}

	public static ArrayList<ArrayList<Edge>> krushkals(ArrayList<ArrayList<Edge>> graph) {

		ArrayList<ArrayList<Edge>> mst = new ArrayList<>();
		int[] pa = new int[graph.size()];
		int[] ra = new int[graph.size()];

		for (int i = 0; i < graph.size(); i++) {
			mst.add(new ArrayList<>());
			ra[i] = 1;
			pa[i] = i;
		}

		PriorityQueue<Kedge> queue = new PriorityQueue<>();

		for (int v = 0; v < graph.size(); v++) {
			for (int n = 0; n < graph.get(v).size(); n++) {

				Edge ne = graph.get(v).get(n);
				if (v < ne.nbr) {
					queue.add(new Kedge(v, ne.nbr, ne.wt));
				}

			}
		}
		int counter = 0;
		while (queue.size() > 0 && counter < graph.size() - 1) {

			Kedge rem = queue.remove();

			int v1sl = find(pa, rem.v1);// v1 set leader
			int v2sl = find(pa, rem.v2);

			if (v1sl != v2sl) {
				addEdge(mst, rem.v1, rem.v2, rem.w);
				counter++;
				merge(pa, ra, v1sl, v2sl);
			}

		}

		return mst;
	}

	private static void merge(int[] pa, int[] ra, int v1sl, int v2sl) {

		if (ra[v1sl] < ra[v2sl]) {
			pa[v1sl] = v2sl;
		} else if (ra[v2sl] < ra[v1sl]) {
			pa[v2sl] = v1sl;
		} else {
			pa[v1sl] = v2sl;
			ra[v2sl]++;
		}
	}

	private static Integer find(int[] pa, int v1) {
		if (pa[v1] == v1) {
			return v1;
		} else {
			int sl = find(pa, pa[v1]);
			pa[v1]=sl;
			return sl;
		}
	}
//	for (int v = 0; v < 7; v++) {
//		topo.add(new ArrayList<>());
//	}
//	addEdgeSingle(topo, 0, 1, 1);
//	addEdgeSingle(topo, 1, 2, 1);
//	addEdgeSingle(topo, 2, 3, 1);
//	addEdgeSingle(topo, 0, 4, 1);
//	addEdgeSingle(topo, 5, 4, 1);
//	addEdgeSingle(topo, 5, 6, 1);
//	addEdgeSingle(topo, 6, 3, 1);
//	TopologicalSorMatser(topo);


	public static ArrayList<ArrayList<Edge>> floyd = new ArrayList<>();

	public static ArrayList<ArrayList<Edge>> topo = new ArrayList<>();

	public static ArrayList<ArrayList<Edge>> bell = new ArrayList<>();

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
		display(krushkals(graph));

//		boolean[] visited = new boolean[graph.size()];

		for (int v = 0; v < 4; v++) {
			floyd.add(new ArrayList<>());
		}

		
		for (int v = 0; v < 4; v++) {
			bell.add(new ArrayList<>());
		}

//		addEdgeSingle(bell, 0, 1, 2);
//		addEdgeSingle(bell, 1, 2, 4);
//		addEdgeSingle(bell, 2, 3, 8);
//		addEdgeSingle(bell, 3, 0, 9);
//		addEdgeSingle(bell, 2, 0, -5);
//
//		bellmanFord(bell, 0);

		
//		floyd.get(0).add(new Edge(1, 2));
//		floyd.get(0).add(new Edge(2, 4));
//		floyd.get(0).add(new Edge(3, 8));
//		floyd.get(1).add(new Edge(2, 1));
//		floyd.get(1).add(new Edge(3, 5));
//		floyd.get(2).add(new Edge(3, 1));

	}

}
