package org.example.multiThreading;

public class HelloWorldPrinter implements  Runnable{
    @Override
    public void run() {
        System.out.println("Hello world from thread: " + Thread.currentThread().getName());
    }
}
