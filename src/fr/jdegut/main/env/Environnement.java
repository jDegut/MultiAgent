package fr.jdegut.main.env;

import fr.jdegut.main.agent.Agent;
import fr.jdegut.main.agent.Buyer;
import fr.jdegut.main.agent.Negotiator;
import fr.jdegut.main.agent.Supplier;

import java.time.LocalDateTime;
import java.util.*;

public class Environnement {

	public static LocalDateTime startDate;
	private final List<Agent> agents;
	private final List<Supplier> suppliers;
	private final List<Negotiator> negotiators;
	private final List<Buyer> buyers;
	private final List<Negotiation> board;

	public Environnement() {
		this.agents = new ArrayList<>();
		this.suppliers = new ArrayList<>();
		this.negotiators = new ArrayList<>();
		this.buyers = new ArrayList<>();
		this.board = new ArrayList<>();
	}


	// Crée X buyers aléatoires
	public void generateRandomBuyers(int X) {
		for (int i = 0; i < X; i++) {
			Buyer b = new Buyer(generateRandomName());
			buyers.add(b);
			agents.add(b);
		}
	}

	// Crée X suppliers aléatoires
	public void generateRandomSuppliers(int X) {
		for (int i = 0; i < X; i++) {
			Supplier s = new Supplier(generateRandomName());
			suppliers.add(s);
			agents.add(s);
		}
	}

	// Crée X négociateurs aléatoires
	public void generateRandomNegotiator(int X) {
		for (int i = 0; i < X; i++) {
			Negotiator n = new Negotiator(generateRandomName());
			negotiators.add(n);
			agents.add(n);
		}
	}

	// Crée des buyers, suppliers et négociateurs aléatoires
	public void generateRandomAgents(int buyers, int suppliers, int negotiators) {
		generateRandomBuyers(buyers);
		generateRandomSuppliers(suppliers);
		generateRandomNegotiator(negotiators);
	}

	// Crée un board à partir des tickets de tous les suppliers
	public void generateRandomBoard() {
		for (Supplier s : this.suppliers) {
			this.board.add(new Negotiation(s, s.offer));
		}
	}

	// Crée un environnement à partir des agents et du board
	public void generateRandomEnv(int buyers, int suppliers, int negotiators) {
		generateRandomAgents(buyers, suppliers, negotiators);
		generateRandomBoard();
	}

	public void run() {

		startDate = LocalDateTime.now();

		for(Agent agent : this.agents) {
			new Thread(agent).start();
		}
	}

	public List<Agent> getAgents() {
		return this.agents;
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
		agent.setEnv(this);
		if(agent instanceof Supplier supplier) {
			this.suppliers.add(supplier);
		} else if(agent instanceof Negotiator negotiator) {
			this.negotiators.add(negotiator);
		}
	}

	public void addOfferToBoard(Supplier supplier, Ticket offer) {
		Negotiation negotiation = new Negotiation(supplier, offer, LocalDateTime.now());
		this.board.add(negotiation);
	}

	public List<Negotiation> getBoard() {
		return this.board;
	}

	public Negotiator getNegoByID(int id) {
		for (Negotiator n : this.negotiators) {
			if (n.getId() == id) {
				return n;
			}
		}
		throw new NoSuchElementException("No Negotiator found with ID: " + id);
	}

	public static String generateRandomName() {
		Random rand = new Random();
		int length = rand.nextInt(7) + 3; // (0 to 6) + 3 = 3 to 9
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = rand.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}

	public void deleteAgent(int id) {
		deleteFromIterator(id, this.agents.iterator());
		deleteFromIterator(id, this.suppliers.iterator());
		deleteFromIterator(id, this.negotiators.iterator());
		deleteFromIterator(id, this.buyers.iterator());
	}

	public <T extends Agent> void deleteFromIterator(int id, Iterator<T> iter) {
		while (iter.hasNext()) {
			T agent = iter.next();
			if (agent.getId() == id) {
				iter.remove();
				break;
			}
		}
	}
}
