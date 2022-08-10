 public void visit(DirectedGraph dg) {
 CompoundDirectedGraph graph = (CompoundDirectedGraph) dg;


 NodeList roots = new NodeList();
 // Find all subgraphs and root subgraphs
 for (int i = 0; i < graph.nodes.size(); i++) {
 Object node = graph.nodes.get(i);
 if (node instanceof Subgraph) {
 Subgraph s = (Subgraph) node;
 Insets padding = dg.getPadding(s);
 s.head = new SubgraphBoundary(s, padding, 0);
 s.tail = new SubgraphBoundary(s, padding, 2);
 Edge headToTail = new Edge(s.head, s.tail);
 headToTail.weight = 10;
 graph.edges.add(headToTail);
 graph.containment.add(headToTail);


 graph.subgraphs.add(s);
 if (s.getParent() == null)
 roots.add(s);
 if (s.members.size() == 2) // The 2 being the head and tail only
 graph.edges.add(new Edge(s.head, s.tail));
			}
		}


 buildNestingTreeIndices(roots, 0);
 convertSubgraphEndpoints(graph);
 addContainmentEdges(graph);
 replaceSubgraphsWithBoundaries(graph);
	}