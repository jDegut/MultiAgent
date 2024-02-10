package fr.jdegut.main.agent;

import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Ticket;
import fr.jdegut.main.strategy.Strategy;

import java.util.*;

// Le négociateur regarde les destinations de là où veulent aller les buyers et tentent de tirer le
// meilleur prix des billets auprès des suppliers
public class Negotiator extends Agent {
    private Strategy strategy;

    public Ticket offer; // Ticket dans le cas où le negotiator l'a acheté au supplier mais pas encore donné au Buyer

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

    public Negotiator(String name, Strategy s) {
        super(name);
        this.buyerAccept = 0;
        setStrategy(s);
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

            // Récupération de toutes les demandes des buyers et de toutes les offres des suppliers
            Map<Buyer, String> buyerDemands = getBuyersDemand();
            Map<Supplier, String> supplierOffers = getSuppliersOffers();
            // Récupération des destinations offertes par les suppliers et demandées par les buyers
            // + shuffle pour ne pas que tous les negotiators aillent sur la même offre en premier
            List<Object[]> goodDeals = findMatches(buyerDemands, supplierOffers);
            Collections.shuffle(goodDeals);

            while (this.supplierAccept != 1) {

                Object[] firstMatch = goodDeals.get(0); // On prend le premier de la liste
                int supplierID = (Integer) firstMatch[1];
                float budget = this.getMoney();
                // On envoie au supplier une première offre
                this.initialOfferToSupplier = Ticket.generateRandomPrice(budget/100, budget/2);
                this.env.getSuppByID(supplierID).offers.put(this, this.initialOfferToSupplier);
                while(this.supplierAccept == 0) {
                    // On utilise cette boucle while pour attendre la réponse du supplier
                    // C'est dans le code du supplier que l'on gère la négociation, ici on ne fait qu'attendre
                }
                if (this.buyerAccept == -1) { // Si le supplier a refusé, on le retire de la liste et on recommence
                    this.buyerAccept = 0;
                    goodDeals.remove(0);
                }
                if (goodDeals.isEmpty()) {
                    break;
                    // Si la liste est vide, on doit recommencer du début pour récupérer d'autres bonnes affaires
                }
            }

            Object[] firstMatch = goodDeals.get(0);
            int buyerID = (Integer) firstMatch[0];
            this.env.getBuyerByID(buyerID).negotiatorOffers.add(this.offer);
            while(this.buyerAccept == 0) {
                // On utilise cette boucle while pour attendre la réponse du buyer
            }
            if (this.buyerAccept == -1) {
                this.buyerAccept = 0;       // Réponse négative, on recommence du début
            }
        }
        this.deleteItself();        // Sinon le job est accompli et on peut se delete
    }

    public Map<Buyer, String> getBuyersDemand() {    // Récupération de toutes les demandes des buyers
        Map<Buyer, String> demand = new HashMap<>();
        for (Buyer buyer : this.getEnv().buyers) {
            demand.put(buyer, buyer.wantedDestination);
        }
        return demand;
    }

    public Map<Supplier, String> getSuppliersOffers() {  // Récupération de toutes les offres des suppliers
        Map<Supplier, String> offers = new HashMap<>();
        for (Supplier supplier : this.getEnv().suppliers) {
            offers.put(supplier, supplier.offer.arrival);
        }
        return offers;
    }


    // Récupération de tous les suppliers qui vendent une destination qui intéresse un buyer
    public List<Object[]> findMatches(Map<Buyer, String> buyerDemands, Map<Supplier, String> supplierOffers) {
        List<Object[]> matches = new ArrayList<>();

        for (Map.Entry<Buyer, String> demandEntry : buyerDemands.entrySet()) {
            for (Map.Entry<Supplier, String> offerEntry : supplierOffers.entrySet()) {
                if (demandEntry.getValue().equals(offerEntry.getValue())) {
                    int buyerID = demandEntry.getKey().getId();
                    int supplierID = offerEntry.getKey().getId();
                    String destination = demandEntry.getValue();
                    Object[] match = new Object[]{buyerID, supplierID, destination};
                    matches.add(match);
                }
            }
        }
        return matches;
    }
}