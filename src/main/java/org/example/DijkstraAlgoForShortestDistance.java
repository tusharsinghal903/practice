package org.example;

import org.example.models.Flight;
import org.example.models.QuickestConnection;

import java.util.*;

public class DijkstraAlgoForShortestDistance {

    static class Node implements Comparable<Node> {
        int v;
        int distance;
        List<Integer> path;

        public Node(int v, int distance, List<Integer> path) {
            this.v = v;
            this.distance = distance;
            this.path = path;
        }

        public Node(int v, int distance) {
            this.v = v;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node n) {
            if (this.distance <= n.distance) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static Map<Integer, Map<Integer, QuickestConnection>> getQuickestConnections(
            int numberOfCities,
            Map<Integer, Map<Integer, Flight>> flights,
            int source) {
        boolean[] visited = new boolean[numberOfCities];
        HashMap<Integer, Node> map = new HashMap<>();
        PriorityQueue<Node> q = new PriorityQueue<>();

        map.put(source, new Node(source, 0, List.of(source, source)));
        q.add(new Node(source, 0, List.of(source)));

        while (!q.isEmpty()) {
            Node n = q.poll();
            int v = n.v;
            int distance = n.distance;
            visited[v] = true;

            Map<Integer, Flight> adjList
                    = flights.get(v);
            if(adjList == null) continue;
            adjList.keySet().forEach(key -> {
                Flight flight = adjList.get(key);
                if(flight == null) return;
                if (!visited[flight.getDestination()]) {
                    List<Integer> path = new ArrayList<>(n.path);
                    path.add(flight.getDestination());
                    if (!map.containsKey(flight.getDestination())) {
                        map.put(
                                flight.getDestination(),
                                new Node(v,
                                        distance
                                                + flight.getDistance(), path));
                    } else {
                        Node sn = map.get(flight.getDestination());
                        if (distance + flight.getDistance()
                                < sn.distance) {
                            sn.v = v;
                            sn.distance
                                    = distance + flight.getDistance();
                            sn.path = path;
                        }
                    }
                    q.add(new Node(flight.getDestination(),
                            distance
                                    + flight.getDistance(), path));
                }
            });
        }

        Map<Integer, Map<Integer, QuickestConnection>> quickestConnections = new HashMap<>();


        for (int i = 0; i < numberOfCities; i++) {
            if (map.get(i) == null) continue;
            quickestConnections.putIfAbsent(source, new HashMap<>());
            quickestConnections.get(source).put(i, new QuickestConnection(
                    (long) map.get(i).distance,
                    map.get(i).path

            ));
        }

        return quickestConnections;
    }
}

