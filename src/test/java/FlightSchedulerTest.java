import org.example.FlightScheduler;
import org.example.models.QuickestConnection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class FlightSchedulerTest {
    
    @Test
    public void test() {
        FlightScheduler flightScheduler = new FlightScheduler(10);
        flightScheduler.addFlight(1,2,1L,5L);
        flightScheduler.addFlight(2,3,6L,8L);
        flightScheduler.addFlight(3,4,9L,10L);
        QuickestConnection quickestConnection = flightScheduler.getQuickestConnection(1,3);
        Assert.assertEquals(quickestConnection.getTimeRequired(), 6);
        Assert.assertEquals(quickestConnection.getFlights(), List.of(1,2,3));
    }

    @Test
    public void test2() {
        FlightScheduler scheduler = new FlightScheduler(10);
        scheduler.addFlight(0, 1, 100L, 200L);
        scheduler.addFlight(0, 2, 150L, 300L);
        scheduler.addFlight(1, 3, 250L, 350L);
        scheduler.addFlight(2, 3, 320L, 420L);
        QuickestConnection quickestConnection = scheduler.getQuickestConnection(0,3);
        Assert.assertEquals(quickestConnection.getTimeRequired(), 200);
        Assert.assertEquals(quickestConnection.getFlights(), List.of(0,1,3));
    }
    
}
