package fr.jdegut.main.agent;

import fr.jdegut.main.env.Message;
import fr.jdegut.main.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public abstract class Agent implements Runnable {

    private final String id;
    private final PriorityQueue<Message> messages;

    private Strategy strategy;

    protected Agent(String id) {
        this.id = id;
        this.messages = new PriorityQueue<>();
    }

    protected Agent(String id, Strategy strategy) {
        this.id = id;
        this.messages = new PriorityQueue<>();
        this.strategy = strategy;
    }

    public String getId() {
        return this.id;
    }

    public void sendMessage(Agent dest, Object key, Object value) {
        dest.messages.add(new Message(key, LocalDateTime.now(), value));
    }

    public Message readMessage() {
        return this.messages.poll();
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
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
