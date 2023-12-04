import org.example.DijkstraAlgoForShortestDistance;
import org.example.models.Flight;
import org.example.models.QuickestConnection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraAlgoForShortestDistanceTest {

    @Test
    public void getQuickestConnections() {
        Map<Integer, Map<Integer, Flight>> flights = new HashMap<>();
        flights.put(1, new HashMap<>());
        flights.get(1).put(2,new Flight(1,2,1L,5L));
        flights.put(2, new HashMap<>());
        flights.get(2).put(3,new Flight(2,3,6L,8L));
        flights.put(3, new HashMap<>());
        flights.get(3).put(4, new Flight(3,4,9L,10L));
        Map<Integer, Map<Integer, QuickestConnection>> quickestConnections = DijkstraAlgoForShortestDistance.getQuickestConnections(10, flights, 1);
        Assert.assertEquals(quickestConnections.get(1).get(3).getTimeRequired(), 6);
        Assert.assertEquals(quickestConnections.get(1).get(3).getFlights(), List.of(1,2,3));
    }

}
