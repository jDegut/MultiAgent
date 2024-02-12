package fr.jdegut.main.env;

public class AnsiColors {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_BLUE = "\u001B[94m";

    public static final String CREATED = BLUE + "CREATED " + RESET;
    public static final String Buyer = YELLOW + " Buyer " + RESET;

    public static final String Negotiator = WHITE + " Negotiator " + RESET;

    public static final String Supplier = MAGENTA + " Supplier " + RESET;
    public static final String OFFER_SENT = CYAN + "OFFER SENT " + RESET;;
    public static final String OFFER_ACCEPTED = GREEN + "OFFER ACCEPTED " + RESET;;
    public static final String OFFER_DECLINED = RED + "OFFER DECLINED " + RESET;;
    public static final String NEGOTIATION = BRIGHT_BLUE + "NEGOTIATION " + RESET;
    public static final String DELETED = BLACK + "DELETED " + RESET;
    public static final String COALITION = RED + "COALITION " + RESET;


}
