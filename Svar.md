# Answer File - Semester 2
# Description of each Implementation
Briefly describe your implementation of the different methods. What was your idea and how did you execute it? If there were any problems and/or failed implementations please add a description.

## Task 1 - mst
I used the Kruskals-algorithm for finding the smallest cost of the construction of the power grid. I first started with sorting all the edges in non-decreasing order, with Collections.sort. Then I looped through all the vertices in the graph, and put them in a parent map, and a rank map. Then I pick the smallest edge, and checks if it forms a cycle with the spanning tree formed so far, using the union find algorithm. If it does not form a cycle, I add it to the spanning tree.

The union-find algoritm checks if two vertices in an edge is already connected between other vertices. If they are, it will form a cycle, and the edge will not be added to the spanning tree. If they are not connected, we combine the sub-trees using the union method, add an edge between then, and the two vertices will be connected.

 I continued until I have added all the vertices to the spanning tree. As a result, this will give me the smallest cost of the construction of the power grid.

## Task 2 - lca
I first started with checking if u or v is the root, if they are, it means that the root is the lowest common ancestor, and we simply return the root. If not, we do a depth-first-search on the graph from the root. This will give ut three HashMaps, one in controll of each node parent, one for the depth of the node and one in controll of the subtree size for a node. With this information we simply triverce up the tree, if the node u is deeper down in the tree than v, we set u to be the parent of u, and vica versa. If they are on the same level, we check if they are the same node, if they are, we return the node, if not, we set u and v to be the parent of u and v. We continue this until we find the lowest common ancestor.

## Task 3 - addRedundant
I first started with doing a depth-first-search from the root. Then I looped through all the neighborus from the root, and then finding the two biggest subtrees. If the root only have one neighbor, we use the helping function getDeepestNode, which simply returns the deepest noede in the current subtree, and then add an edge between the root and the deepest node. If the root have more than one neighbor, we find the two biggest subtrees from the root, and then find the deepest node in each of those subtrees. Then we add an edge between the two deepest nodes, in the two biggest subtrees. By doing it this way, it will give us the smallest possible power outage if a power cable fails. 


# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented any helper methods you must add these as well.**

* ``mst(WeightedGraph<T, E> g)``: O(m log m)
    * We first make two arrayList and two HashMap, which has the time-complexity of O(1). Then we have the first for-loop which is looping through every edge, which has the time-complexity of O(m). Then we use a Collections.sort, which has the time-complexity of O(m log m), since we are sorting every edge in non-decreasing order. Then we have the second for-loop, which is looping through the vertices, which has the time-complexity of O(n). Then we have the last for-loop which is looping through the edges, which is using O(m) time.
    In this loop, we are also using two helping methods, which we need to dive into.

    The find method is using path compression, which ensures that future find operations on the same set are faster because the path to the root becomes shorter and more direct. The time-complexity of the find operation essentially takes O(1) time on average.

    The union method is using just .put, .get and .equals, which all are usin O(1) time. Since there is no loops, and the .equals is checking integers, the time-complexity of the union method is O(1).

    Therefore the whole method mst is using O(m) + O(m log m) + O(n) + O(m) = O(m log m) time.

* ``lca(Graph<T> g, T root, T u, T v)``: O(n)
    * This method is first checking whether u or v is the root, and then making three HashMaps, which all are using O(1) time. Then the method dfs is called. 

    dfs is using a for-loop, which is looping through all the neighbors of the root, which is O(degree(root)) time. Inside the loop we do a recursive call on the method it self on every neighbor. This means it goes through every node, which is O(n) time. Total, dfs is using O(degree(root)) + O(n) = O(n) time. 

    Then we got three while loops, which in worst case is looping through every node, which is O(n) time. 

    Therefore the whole method lca is using O(n) + O(n) + O(n) + O(n) = O(n) time.
* ``addRedundant(Graph<T> g, T root)``: O(n)
    * This method is first making three HashMaps, which all are using O(1) time. Then the method dfs is called. We know from the lca method that dfs is using O(n) time. Then we got a for-loop, which is looping through all the neighbors of the root, which is O(degree(root)) time. 

    If the root only have one neighbor, we use the helping function getDeepestNode, which simply returns the deepest node in the current subtree, and then add an edge between the root and the deepest node. This method is using a while-loop, which is looping through every node, which is O(n) time.

    Else we just add an edge between the two deepest nodes, in the two biggest subtrees. We are again using the getDeepestNode, which we know is using O(n) time.

    Therefore the whole method addRedundant is using O(n) + O(degree(root)) + O(n) + O(n) + O(n) = O(n) time.

