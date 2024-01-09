package fr.jdegut.main.agent;

import fr.jdegut.main.RandomSingle;
import fr.jdegut.main.env.Service;
import fr.jdegut.main.env.ServiceAccess;

public class Supplier extends Agent {

    private static int id = 0;
    private final String name;

    private Service offer;

    public Supplier(String name) {
        super("Supplier-"+id++);
        this.name = name;
        setMoney(9_000 + RandomSingle.getRandom().nextInt(1_000));
    }

    @Override
    public void run() {
        super.run();
        while(true) {
            if (this.offer == null) {
                this.offer = ServiceAccess.getRandomService();
                super.env.addOfferToBoard(this.offer);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // TODO : Attendre un peu et regarder les réponses à l'offre proposée faites par les négociants
        }
    }
}
