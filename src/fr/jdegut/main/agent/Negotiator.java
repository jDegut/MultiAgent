package fr.jdegut.main.agent;

import fr.jdegut.main.env.Personne;

public class Negotiator extends Agent {

    private Personne personne;

    public Negotiator(String id) {
        super(id);
    }

    public Negotiator(String id, Personne p) {
        super(id);
        this.personne = p;
        setMoney(p.getMoney());
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne p) {
        this.personne = p;
        setMoney(p.getMoney());
    }

    @Override
    public void run() {
        super.run();

        // TODO : Une fois que le négociateur a un contrat avec une personne, il peut aller se faire enculer
        // Ou bien juste récupérer les offres proposées sur le board pour en faire des néogiciations avec l'autre connard de supplier

        while(true) {
            if(personne == null) {
                System.out.println("Negotiator " + getId() + " is waiting for a person to negotiate with");
            } else if(personne != null) {

            }

            super.wait1Second();
        }
    }

}
