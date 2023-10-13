package student;

import java.util.ArrayList;

import graph.*;

public class ProblemSolver implements IProblem {

	@Override
	public <V, E extends Comparable<E>> ArrayList<Edge<V>> mst(WeightedGraph<V, E> g) {
		throw new UnsupportedOperationException("Implement me :)");
	}

	@Override
	public <V> V lca(Graph<V> g, V root, V u, V v) {
		throw new UnsupportedOperationException("Implement me :)");
	}

	@Override
	public <V> Edge<V> addRedundant(Graph<V> g, V root) {
		throw new UnsupportedOperationException("Implement me :)");
	}

}
