package fr.jdegut.main.agent;

import fr.jdegut.main.env.DataType;
import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Personne;
import fr.jdegut.main.env.Service;
import fr.jdegut.main.strategy.Strategy;

import java.util.List;

public class Negotiator extends Agent {

    private Personne personne;
    private Strategy strategy;
    private Negotiation bestNegotiation;
    public Float initialOfferToSupplier;

    public Negotiator(String id) {
        super(id);
    }

    public Negotiator(String id, Personne p) {
        super(id);
        this.personne = p;
        setMoney(p.getMoney());
    }

    public Negotiator(String id, Personne p, Strategy strategy) {
        super(id);
        this.personne = p;
        this.strategy = strategy;
        setMoney(p.getMoney());
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne p) {
        this.personne = p;
        setMoney(p.getMoney());
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

        while (true) {
            if (personne == null) {
                System.out.println(getId() + " is waiting for a person to negotiate with");
            } else if (personne != null) {
                // Le négociateur s'intéresse aux offres

                List<Negotiation> board = getEnv().getBoard();
                for (Negotiation negotiation : board) {
                    // On regarde si la negociation propose un service qui correspond au client (billet avec la bonne destination par exemple)
                    if(negotiation.getService().getData(DataType.ARRIVAL_LOCATION) == this.personne.getDemand().getData(DataType.ARRIVAL_LOCATION)) {
                        if(bestNegotiation == null) bestNegotiation = negotiation;
                        else {
                            // Si la négo stockée a un prix supérieur à l'actuel, on update => On cherche le moins cher
                            if((double) bestNegotiation.getService().getData(DataType.PRICE) >
                                    (double) negotiation.getService().getData(DataType.PRICE))
                                bestNegotiation = negotiation;
                        }
                    }
                }

                //TODO : SI  UN MEC EST INTERESSE PAR UNE OFFRE ON FAIT Supplier.negoOffers.add(Negotiator) et bim badaboum ca marche

                // Le négociateur initie la négociation avec le fournisseur
                if (bestNegotiation != null) {
                    System.out.println(getId() + " is initiating negotiation with the supplier.");
                    double initialPrice = (double) bestNegotiation.getService().getData(DataType.PRICE);
                    double finalPrice = strategy.updatePrice(this, initialPrice, initialPrice, initialPrice);
                    System.out.println("Negotiation result for " + getId() + ": Final Price = " + finalPrice);
                    // Réinitialisez la meilleure offre
                    bestNegotiation = null;
                }
            } else {
                System.out.println("PUTE");
                break;
            }
            super.wait1Second();
        }
    }

    public void initiateNegotiationWithSupplier(Supplier supplier, Service offer, double initialPrice) {
        // Vous pouvez mettre en œuvre votre logique pour initier la négociation ici
    }

    public void initiateNegotiation() {

    }
}