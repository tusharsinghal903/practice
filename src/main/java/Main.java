import org.example.multiThreading.HelloWorldPrinter;

public class Main {

    public static void main(String args[]) {
        Thread t1 = new Thread(new HelloWorldPrinter());
        t1.start();


    }
}
