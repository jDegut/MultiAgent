package fr.jdegut.main.agent;

import java.util.PriorityQueue;

public abstract class Agent implements Runnable {

    private final String id;
    private final PriorityQueue<String> messages;

    protected Agent(String id) {
        this.id = id;
        this.messages = new PriorityQueue<>();
    }

    public String getId() {
        return this.id;
    }

    public void sendMessage(Agent dest, String message) {
        dest.messages.add(message);
    }

    public String readMessage() {
        return this.messages.poll();
    }

    @Override
    public void run() {
        System.out.println("Agent " + id + " is running");
    }

    @Override
    public String toString() {
        return this.id;
    }

}
