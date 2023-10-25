package student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import graph.*;

public class ProblemSolver implements IProblem {

	// O(m log m)
	@Override
	public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) {
		ArrayList<Edge<V>> mst = new ArrayList<>();
		ArrayList<Edge<V>> edges = new ArrayList<>();
		HashMap<V, V> parent = new HashMap<>();
		HashMap<V, Integer> rank = new HashMap<>();

		// O(m)
		for (Edge<V> e : g.edges()) {
			edges.add(e);
		}

		// O(m log m)
		Collections.sort(edges, g);

		// O(n)
		for (V v : g.vertices()) {
			parent.put(v, v);
			rank.put(v, 0); 
		}

		// O(m)
		for(Edge<V> e : edges){
			V x = find(parent, e.a); //O(1)
			V y = find(parent, e.b); //O(1)

			//if x and y are not in the same set, add the edge to the mst and union the sets.
			if(!x.equals(y)){
				mst.add(e);
				union(parent, rank, x, y); //O(1)
			}
		}
		return mst;
	}


	/////////////////////////////////
	////HELPING METHODS FOR MST//////

	// O(1)
	private <V> V find(HashMap<V, V> parent, V vertex){
		if(!parent.get(vertex).equals(vertex)){
			parent.put(vertex, find(parent, parent.get(vertex)));
		}
		return parent.get(vertex);
	}

	// O(1) 
	private <V> void union(HashMap<V, V> parent, HashMap<V, Integer> rank, V x, V y){
		if(rank.get(x) > rank.get(y)){
			parent.put(y, x);
		} else{
			parent.put(x, y);
			if (rank.get(x).equals(rank.get(y))) {
				rank.put(y, rank.get(y) + 1);
			}
		}
	}
	/////////////////////////////////



	// O(n) + O(n) + O(n) + O(n) = O(n)
	@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) { 
		if (u == root || v == root) {
			return root;
		}

		HashMap<V, V> parent = new HashMap<>();
		HashMap<V, Integer> depth = new HashMap<>();
		HashMap<V, Integer> subTreeSize = new HashMap<>();
		
		dfs(g, parent, depth, subTreeSize, root, null, 0); //O(n)
		
		while (depth.get(u)>depth.get(v)) { //O(n)
			u = parent.get(u);
		}

		while (depth.get(v)>depth.get(u)) { //O(n)
			v = parent.get(v);
		}

		while (!u.equals(v)) { //O(n)
			u = parent.get(u);
			v = parent.get(v);
		}

		return u;
	}


	//O(n) + O(degree(root)) + O(n) + O(n) + O(n) = O(n)
	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		HashMap<V, V> parent = new HashMap<>();
		HashMap<V, Integer> depth = new HashMap<>();
		HashMap<V, Integer> subTreeSize = new HashMap<>();

		dfs(g, parent, depth, subTreeSize, root, null, 0); //O(n)

		//Finner de to barna til roten med størst subtreesize
		V maxChild1 = null;
		V maxChild2 = null;

		for(V child : g.neighbours(root)) { //O(degree(root))
			if (maxChild1 == null || subTreeSize.get(child) > subTreeSize.get(maxChild1)) {
				maxChild2 = maxChild1;
				maxChild1 = child;
			} else if (maxChild2 == null || subTreeSize.get(child) > subTreeSize.get(maxChild2)) {
				maxChild2 = child;
			}
		}

		if(maxChild2 == null){
			V deepest = getDeepestNode(g, depth, parent, maxChild1, subTreeSize); //O(n)
			if(deepest.equals(maxChild1)){
				if (parent.get(deepest) != null) {
					deepest = parent.get(deepest);
				} else {
					return null;
				}
			}
			g.addEdge(root, deepest);
			return new Edge<V>(root, deepest);
		} else{
			V deepest1 = getDeepestNode(g, depth, parent, maxChild1, subTreeSize); //O(n)
			V deepest2 = getDeepestNode(g, depth, parent, maxChild2, subTreeSize); //O(n)
			g.addEdge(deepest1, deepest2);
			return new Edge<V>(deepest1, deepest2);
		}
	}

	

	// O(n)
	private <V> void dfs(Graph<V> g, HashMap<V, V> parent, HashMap<V, Integer> depth, HashMap<V, Integer> subTreeSize, V root, V parentNode, int d){
		if (root == null) return;
		//O(1)
		parent.put(root, parentNode);
		depth.put(root, d);

		int size = 1;

		// O(n)
		for(V child : g.neighbours(root)){ //O(degree(root))
			if (child.equals(parentNode)) continue;
			dfs(g, parent, depth, subTreeSize, child, root, d + 1); //O(n)
			size += subTreeSize.get(child);
		}
		subTreeSize.put(root, size);
	}

	// O(n)
	private <V> V getDeepestNode(Graph<V> g, HashMap<V, Integer> depth, HashMap<V, V> parent, V start, HashMap<V, Integer> subTreeSize){
		V currentNode = start;
		V deepestNode = start;
		int maxDepth = depth.get(start);

		Set<V> visited = new HashSet<>();
		Queue<V> queue = new LinkedList<>();
		queue.offer(currentNode);

		while(!queue.isEmpty()){ //O(n)
			currentNode = queue.poll();
			visited.add(currentNode);

			for(V neighbor : g.neighbours(currentNode)){ //O(degree(currentNode))
				if(visited.contains(neighbor)) continue; //Skip if already visited
				if(depth.get(neighbor) <= depth.get(currentNode)) continue; //Skip if neighbor is not deeper than currentNode

				//Check if neighbor is in the subtree of currentNode
				if(parent.get(neighbor).equals(currentNode) || parent.get(neighbor).equals(start)){

					//Update deepestNode if neighbor is deeper than current deepestNode
					if(depth.get(neighbor) > maxDepth || (depth.get(neighbor) == maxDepth && subTreeSize.get(neighbor) > subTreeSize.get(deepestNode))){
						maxDepth = depth.get(neighbor);
						deepestNode = neighbor;
					}
					queue.offer(neighbor);
				}	
			}
		}
		return deepestNode;
	}
}
