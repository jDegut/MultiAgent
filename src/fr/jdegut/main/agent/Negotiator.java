package fr.jdegut.main.agent;

import fr.jdegut.main.env.AnsiColors;
import fr.jdegut.main.env.Environnement;
import fr.jdegut.main.env.Negotiation;
import fr.jdegut.main.env.Ticket;
import fr.jdegut.main.strategy.Strategy;

import java.util.*;

import static fr.jdegut.main.RandomSingle.random;

// Le négociateur regarde les destinations de là où veulent aller les buyers et tentent de tirer le
// meilleur prix des billets auprès des suppliers
public class Negotiator extends Agent {
    private Strategy strategy;

    public boolean coalition; // Le Negotiator trouve-t-il cela intéressant de faire une coalition.

    public Ticket offer; // Ticket dans le cas où le negotiator l'a acheté au supplier mais pas encore donné au Buyer

    public int supplierAccept;  // 0 à l'initialisation, -1 si le supplier a refusé (on repasse alors à 0)
    // 1 si le supplier a accepté
    public int buyerAccept;     // 0 à l'initialisation, -1 si le buyer a refusé (on repasse alors à 0)
                                // 1 si le buyer a accepté
    public Float initialOfferToSupplier;

    public Negotiator(String name, Environnement env) {
        super(name, env);
        this.buyerAccept = 0;
        this.coalition = false;
    }

    public Negotiator(String name, Strategy s, Environnement env) {
        super(name, env);
        this.buyerAccept = 0;
        this.coalition = false;
        setStrategy(s);
        System.out.println(AnsiColors.CREATED + "|" + AnsiColors.Negotiator + name +  " with budget " + this.getMoney());
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

            int buyerID = 0;

            while (this.supplierAccept != 1) {

                // Récupération de toutes les demandes des buyers et de toutes les offres des suppliers
                Map<Buyer, String> buyerDemands = getBuyersDemand();
                Map<Supplier, String> supplierOffers = getSuppliersOffers();
                // Récupération des destinations offertes par les suppliers et demandées par les buyers
                // + shuffle pour ne pas que tous les negotiators aillent sur la même offre en premier
                List<Object[]> goodDeals = findMatches(buyerDemands, supplierOffers);

                if (goodDeals == null || goodDeals.isEmpty()) {
                    System.out.println(AnsiColors.OFFER_DECLINED + "|" + AnsiColors.Negotiator + this.name + " found no match, deleting..");
                    break;
                }

                Collections.shuffle(goodDeals);

                Object[] firstMatch = goodDeals.get(0); // On prend le premier de la liste
                int supplierID = (Integer) firstMatch[1];
                float budget = this.getMoney();
                // On envoie au supplier une première offre
                this.initialOfferToSupplier = Ticket.generateRandomPrice(budget/100, budget/3);
                this.env.getSuppByID(supplierID).offers.put(this, this.initialOfferToSupplier);
                System.out.println(AnsiColors.OFFER_SENT + "|" + AnsiColors.Negotiator + this.name + " to" + AnsiColors.Supplier + this.env.getSuppByID(supplierID).name + " with destination " +  this.env.getSuppByID(supplierID).offer.arrival + " for " + this.initialOfferToSupplier);

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // On utilise cette boucle while pour attendre la réponse du supplier
                // C'est dans le code du supplier que l'on gère la négociation, ici on ne fait qu'attendre


                if (this.supplierAccept == -1) { // Si le supplier a refusé, on le retire de la liste et on recommence
                    this.supplierAccept = 0;
                    goodDeals.remove(0);
                }
                if (this.supplierAccept == 1 || goodDeals == null || goodDeals.isEmpty()) {
                    firstMatch = goodDeals.get(0);
                    buyerID = (Integer) firstMatch[0];
                    break;
                    // Si la liste est vide, on doit recommencer du début pour récupérer d'autres bonnes affaires
                }
            }

            if (this.supplierAccept != 1) {
                break;
            }

            try {       // Essayer de faire une offre au Buyer
                if (makeOfferToBuyer(buyerID)) {
                    break;
                }
                } catch (ConcurrentModificationException e) {   // Si celui-ci n'existe plus, recommencer du début
            }
        }
        this.deleteItself();        // Sinon le job est accompli et on peut se delete
    }

    public void getIntoCoalition() {
        Environnement env = this.getEnv();

        // Calcul de la probabilité de succès d'un Negotiator en tenant compte de la compétitivité entre les Negotiator
        float buyers = (float) env.buyers.size();
        float supp = (float) env.suppliers.size();
        float minBuySup = Math.min(buyers, supp);
        float negos = (float) env.negotiators.size();
        float successProbability1 = negos * negos * 2;
        float successProbability2 =  minBuySup * minBuySup; // Nombre d'offres possibles / nombre de Negotiator
        float successProbability = successProbability2 / successProbability1;

        if (successProbability >= 1) { // Si il y a plus d'offres que d'agents, la coalition ne sert à rien et ne nous intéresse pas.
            this.coalition = false;
        } else {        // Sinon, la probabilité d'être intéressé par une coalition est inversement proportionnelle à la probabilité de succès.
            float proba = random.nextFloat();
            this.coalition = (proba <= 1 - successProbability);
        }
    }

    public boolean makeOfferToBuyer(int buyerID) {
        Buyer b = this.env.getBuyerByID(buyerID);
        if (b == null) {
            return false;
        }
        b.negotiatorOffers.add(this.offer);
        System.out.println(AnsiColors.OFFER_SENT + "|" + AnsiColors.Negotiator + this.name + " to" + AnsiColors.Buyer + b.name + " with destination " + this.offer.arrival + " for " + this.offer.price);
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (this.buyerAccept == 1) {
            return true;
        } else { // (this.buyerAccept == -1)
            this.buyerAccept = 0;       // Réponse négative, on recommence avec un meilleur prix
            this.offer.price *= 0.93;
            return makeOfferToBuyer(buyerID);
        }
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