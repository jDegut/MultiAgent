package fr.jdegut.main.agent;

public class Supplier extends Agent {

    private static int id = 0;

    public Supplier() {
        super("Supplier-"+id++);
    }
}
