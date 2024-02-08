package fr.jdegut.main.agent;

import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Ticket;
import fr.jdegut.main.strategy.Strategy;

import java.util.List;

// Le négociateur regarde les destinations de là où veulent aller les buyers et tentent de tirer le
// meilleur prix des billets auprès des suppliers
public class Negotiator extends Agent {
    private Strategy strategy;

    public int supplierAccept;  // 0 à l'initialisation, -1 si le supplier a refusé (on repasse alors à 0)
    // 1 si le supplier a accepté
    public int buyerAccept;     // 0 à l'initialisation, -1 si le buyer a refusé (on repasse alors à 0)
                                // 1 si le buyer a accepté
    private Negotiation bestNegotiation;
    public Float initialOfferToSupplier;

    public Negotiator(String name) {
        super(name);
        this.buyerAccept = 0;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void run() {
        super.run();
        while (this.buyerAccept != 1) {

            while (this.supplierAccept != 1) {
                // regarder les buyers
                // regarder les suppliers
                // negotier avec un supplier
                // jusqua ce quon ait un ticket
                // aller revoir le buyer et hope il accepte
            }

        }
        this.deleteItself();
    }

    public void initiateNegotiation() {

    }
}