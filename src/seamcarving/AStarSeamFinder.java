package seamcarving;

import graphpathfinding.AStarGraph;
import graphpathfinding.AStarPathFinder;
import graphpathfinding.ShortestPathFinder;
import graphpathfinding.WeightedEdge;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class AStarSeamFinder extends SeamFinder {
    private List<Integer> horizontalSeam;
    private List<Integer> verticalSeam;
    private MakeNode startNode;
    private MakeNode endNode;

    /*
    Use this method to create your ShortestPathFinder.
    This will be overridden during grading to use our solution path finder, so you don't get
    penalized again for any bugs in code from previous assignments
    */
    @Override
    protected <VERTEX> ShortestPathFinder<VERTEX> createPathFinder(AStarGraph<VERTEX> graph) {
        return new AStarPathFinder<>(graph);
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        horizontalSeam = new ArrayList<>();
        MakeGraphH graph = new MakeGraphH(energies);
        ShortestPathFinder<MakeNode> path = createPathFinder(graph);
        for (MakeNode n : path.findShortestPath(startNode, endNode, Duration.ofSeconds(30)).solution()) {
            horizontalSeam.add(n.y);
        }
        if (horizontalSeam.size() > 1) {
            if (horizontalSeam.get(0) == startNode.y) {
                horizontalSeam.remove(0);
            }
            if (horizontalSeam.get(horizontalSeam.size() - 1) == endNode.y) {
                horizontalSeam.remove(horizontalSeam.size() - 1);
            }
        }
        return horizontalSeam;
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        verticalSeam = new ArrayList<>();
        MakeGraph graph = new MakeGraph(energies, true);
        ShortestPathFinder<MakeNode> path = createPathFinder(graph);
        for (MakeNode n : path.findShortestPath(startNode, endNode, Duration.ofSeconds(30)).solution()) {
            verticalSeam.add(n.x);
        }
        if (verticalSeam.size() > 1) {
            if (verticalSeam.get(0) == startNode.x) {
                verticalSeam.remove(0);
            }
            if (verticalSeam.get(verticalSeam.size() - 1) == endNode.x) {
                verticalSeam.remove(verticalSeam.size() - 1);
            }
        }
        return verticalSeam;
    }

    public class MakeGraphH implements AStarGraph<MakeNode> {
        double[][] energies;

        public MakeGraphH(double[][] energies) {
            this.energies = energies;
            startNode = new MakeNode(-1, -1);
            endNode = new MakeNode(energies.length + 1000, energies[0].length + 1000);
        }

        public Collection<WeightedEdge<MakeNode>> neighbors(MakeNode v) {
            List<WeightedEdge<MakeNode>> result = new ArrayList<>();
            if (v.x == startNode.x && v.y == startNode.y) {
                for (int i = 0; i < energies[0].length; i++) {
                    MakeNode n = new MakeNode(0, i);
                    WeightedEdge<MakeNode> e = new WeightedEdge<>(v, n, energies[0][i]);
                    result.add(e);
                }
            }
            if ((v.x >= 0 && v.x < energies.length - 1) && (v.y >= 0 && v.y < energies[0].length)) {
                if (v.y > 0) {
                    MakeNode n1 = new MakeNode(v.x + 1, v.y - 1); // top right
                    WeightedEdge<MakeNode> e1 = new WeightedEdge<>(v, n1, energies[v.x + 1][v.y - 1]);
                    result.add(e1);
                }

                MakeNode n2 = new MakeNode(v.x + 1, v.y); // right
                WeightedEdge<MakeNode> e2 = new WeightedEdge<>(v, n2, energies[v.x + 1][v.y]);
                result.add(e2);

                if (v.y < energies[0].length - 1) {
                    MakeNode n3 = new MakeNode(v.x + 1, v.y + 1); // bottom right
                    WeightedEdge<MakeNode> e3 = new WeightedEdge<>(v, n3, energies[v.x + 1][v.y + 1]);
                    result.add(e3);
                }
            }
            if (v.x + 1 == energies.length) {
                MakeNode n = endNode;
                WeightedEdge<MakeNode> e = new WeightedEdge<>(v, n, 0);
                result.add(e);
            }
            return result;
        }

        public double estimatedDistanceToGoal(MakeNode v, MakeNode goal) {
            return 0;
        }
    }

    public class MakeGraph implements AStarGraph<MakeNode> {
        double[][] energies;
        boolean vertical;

        public MakeGraph(double[][] energies, boolean vertical) {
            this.energies = energies;
            this.vertical = vertical;
            startNode = new MakeNode(-1, -1);
            endNode = new MakeNode(energies.length + 1000, energies[0].length + 1000);
        }

        public Collection<WeightedEdge<MakeNode>> neighbors(MakeNode v) {
            List<WeightedEdge<MakeNode>> result = new ArrayList<>();
            if (v.x == startNode.x && v.y == startNode.y) {
                for (int i = 0; i < energies.length; i++) {
                    MakeNode n = new MakeNode(i, 0);
                    WeightedEdge<MakeNode> e = new WeightedEdge<>(v, n, energies[i][0]);
                    result.add(e);
                }
            }
            if ((v.x >= 0 && v.x < energies.length) && (v.y >= 0 && v.y < energies[0].length - 1)) {
                if (v.x > 0) {
                    MakeNode n1 = new MakeNode(v.x - 1, v.y + 1); // bottom left
                    WeightedEdge<MakeNode> e1 = new WeightedEdge<>(v, n1, energies[v.x - 1][v.y + 1]);
                    result.add(e1);
                }

                MakeNode n2 = new MakeNode(v.x, v.y + 1); // below
                WeightedEdge<MakeNode> e2 = new WeightedEdge<>(v, n2, energies[v.x][v.y + 1]);
                result.add(e2);

                if (v.x < energies.length - 1) {
                    MakeNode n3 = new MakeNode(v.x + 1, v.y + 1); // bottom right
                    WeightedEdge<MakeNode> e3 = new WeightedEdge<>(v, n3, energies[v.x + 1][v.y + 1]);
                    result.add(e3);
                }
            }
            if (v.y + 1 == energies[0].length) {
                MakeNode n = endNode;
                WeightedEdge<MakeNode> e = new WeightedEdge<>(v, n, 0);
                result.add(e);
            }
            return result;
        }

        public double estimatedDistanceToGoal(MakeNode v, MakeNode goal) {
            return 0;
        }
    }

    private class MakeNode {
        public int x;
        public int y;

        public MakeNode(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            MakeNode makeNode = (MakeNode) o;
            return x == makeNode.x &&
                y == makeNode.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
