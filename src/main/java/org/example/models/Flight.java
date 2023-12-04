package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.DijkstraAlgoForShortestDistance;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
public class Flight implements Comparable<Flight> {
    int source;
    int destination;
    Long departure_time;
    Long arrival_time;

    int distance;

    public Flight(int source, int destination, Long departureTime, Long arrivalTime) {
        this.source = source;
        this.destination = destination;
        this.departure_time = departureTime;
        this.arrival_time = arrivalTime;
        this.distance = Math.toIntExact(arrivalTime - departureTime);
    }

    @Override
    public int compareTo(Flight o) {
        if (this.distance <= o.distance) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
