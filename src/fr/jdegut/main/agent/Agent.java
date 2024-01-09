package fr.jdegut.main.agent;

import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.env.Message;
import fr.jdegut.main.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public abstract class Agent implements Runnable {

    private final String id;
    private final PriorityQueue<Message> messages;

    protected Environnement env;
    private Strategy strategy;
    private int money;

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

    public Environnement getEnv() {
        return this.env;
    }

    public void setEnv(Environnement env) {
        this.env = env;
    }

    public Strategy getStrategy() {
        return this.strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    protected void wait1Second() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
