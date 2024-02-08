package fr.jdegut.main.agent;

import fr.jdegut.main.env.CitiesCompanies;
import fr.jdegut.main.env.Ticket;
import fr.jdegut.main.strategy.Strategy;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// Buyer = celui qui veut aller quelque part (avec un budget max) et qui attend des propositions des
// négociateurs jusqu'à en trouver une qui lui convient
public class Buyer extends Agent {
    public String wantedDestination; // Destination où l'agent veut se rendre
    public boolean requestFulfilled; // False jusqu'à ce qu'il trouve son billet
    private float maxPayPrice; // Prix max qu'il compte payer parcequ'il n'a tout de même pas envie de se faire arnaquer
    public Queue<Ticket> negotiatorOffers = new LinkedList<>();

    public Buyer(String name) {
        super(name);
        this.wantedDestination = CitiesCompanies.getRandomDestination();
        this.maxPayPrice = generateRandomPrice();
        this.requestFulfilled = false;
    }

    public Buyer (String name, Strategy s) {
        super(name, s);
        this.wantedDestination = CitiesCompanies.getRandomDestination();
    }

    public Buyer (String name, Strategy s, float money) {
        super(name, s, money);
        this.wantedDestination = CitiesCompanies.getRandomDestination();
    }

    public Buyer (String name, Strategy s, float money, String destination) {
        super(name, s, money);
        this.wantedDestination = destination;
    }
    @Override
    public void run() {
        super.run();
        while (!this.requestFulfilled) {        // Tant qu'il n'a pas trouvé son billet
            if (!negotiatorOffers.isEmpty()) {      // Regarde sa pile FIFO de propositions
                Ticket t = negotiatorOffers.poll();
                int negotiatorID = t.agentID;

                if (t.price <= this.maxPayPrice) {      // Si celle-ci est à un prix convenable
                    t.attributeTo(this.getId());
                    this.requestFulfilled = true;       // On est d'accord
                    this.env.getNegoByID(negotiatorID).buyerAccept = 1; // Dire au négo qu'on a accepté son offre
                } else {
                    this.maxPayPrice *= 1.05;           // Sinon on augmente de 5% notre prix max
                    this.env.getNegoByID(negotiatorID).buyerAccept = -1;    // Dire au négo qu'on a refusé son offre
                }
            }
        }
        this.deleteItself();
    }


    private static float generateRandomPrice() {
        return new Random().nextInt(500, 5000);
    }
}
