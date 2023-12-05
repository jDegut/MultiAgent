package fr.jdegut.main.agent;

import fr.jdegut.main.env.Personne;

public class Negotiator extends Agent {

    private Personne p;

    public Negotiator(String id) {
        super(id);
    }

    public Negotiator(String id, Personne p) {
        super(id);
        this.p = p;
    }

    public Personne getPersonne() {
        return p;
    }

    public void setPersonne(Personne p) {
        this.p = p;
    }

}
