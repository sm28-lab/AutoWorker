package dev.sorn.autoworker;

import static dev.sorn.autoworker.Graph.Builder.graph;

public interface GraphTestData extends NodeTestData {

    default Graph.Builder aGraph() {
        var nodeA = aNode().build();
        var nodeB = aNode().build();
        var nodeC = aNode().build();
        var nodeD = aNode().build();
        return graph()
            .edge(nodeA, nodeB)
            .edge(nodeA, nodeD)
            .edge(nodeB, nodeC);
    }

}
