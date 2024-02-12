package fr.jdegut.main.agent;

import fr.jdegut.main.env.AnsiColors;
import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.strategy.Strategy;

import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
import java.util.PriorityQueue;
import java.util.Random;

public abstract class Agent implements Runnable {

    private int id;
    public String name;

    protected Environnement env;
    private Strategy strategy;
    private float money;

    protected Agent(String name, Strategy strategy, float money, Environnement env) {
        Random rand = new Random();
        this.name = name;
        this.id = rand.nextInt(1000000, 2000000);
        this.strategy = strategy;
        this.money = money;
        this.env = env;
    }

    protected Agent(String name, Environnement env) {
        this(name, null, generateRandomMoney(), env);
    }

    protected Agent(String name, Strategy strategy, Environnement env) {
        this(name, strategy, generateRandomMoney(), env);
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
        return new Random().nextInt(500, 5000);
    }

    @Override
    public void run() {

    }

    protected void deleteItself() {
        try {
            // Quand l'agent a fini sa tache, il est retiré de l'environnement
            System.out.println(AnsiColors.DELETED + " | Agent " + this.name);
            this.env.deleteAgent(this.id);
        } catch (ConcurrentModificationException e) {
            System.out.println(AnsiColors.DELETED + " | Agent " + this.name);
        }
    }
}
