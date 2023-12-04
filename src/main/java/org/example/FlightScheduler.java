package org.example;

import org.example.exceptions.InvalidCityException;
import org.example.exceptions.NoPathException;
import org.example.models.Flight;
import org.example.models.QuickestConnection;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlightScheduler {
    Integer numberOfCities;
    Map<Integer, Map<Integer, Flight>> flights;


    public FlightScheduler(int numberOfCities) {
        this.numberOfCities = numberOfCities;
        this.flights = new HashMap<>();


    }

    public void addFlight(int source, int destination, Long departureTime, Long arrivalTime) {
        flights.putIfAbsent(source, new HashMap<>());
        flights.get(source).putIfAbsent(destination, new Flight( source,  destination,  departureTime,  arrivalTime));
    }

    private boolean isCityValid(int city) {
            if(city < 0  || city > numberOfCities) {
                return false;
            }
            return true;
    }

    public QuickestConnection getQuickestConnection(int source, int destination) {
        if(!isCityValid(source) || !isCityValid(destination)) {
            throw new InvalidCityException();
        }
        Map<Integer, Map<Integer, QuickestConnection>> quickestConnections  = DijkstraAlgoForShortestDistance.getQuickestConnections(numberOfCities, flights, source);
        if(quickestConnections.get(source) == null || quickestConnections.get(source).get(destination) == null) {
            throw new NoPathException();
        }
        return quickestConnections.get(source).get(destination);
    }

}
