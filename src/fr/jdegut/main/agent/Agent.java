package fr.jdegut.main.agent;

import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent implements Runnable {

    private int id;
    public String name;

    protected Environnement env;
    private Strategy strategy;
    private float money;

    protected Agent(String name, Strategy strategy, float money) {
        Random rand = new Random();
        this.name = name;
        this.id = rand.nextInt(1000000, 2000000);
        this.strategy = strategy;
        this.money = money;
    }

    protected Agent(String name) {
        this(name, null, generateRandomMoney());
    }

    protected Agent(String name, Strategy strategy) {
        this(name, strategy, generateRandomMoney());
    }

    public int getId() {
        return this.id;
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

    public float getMoney() {
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

    private static float generateRandomMoney() {
        return new Random().nextInt(500, 20000);
    }

    @Override
    public void run() {
        System.out.println("Agent " + id + " is running");
    }

    protected void deleteItself() {
        // Quand l'agent a fini sa tache, il est retiré de l'environnement
        System.out.println("Agent " + this.name + " supprimé");
        this.env.deleteAgent(this.id);
    }
}
