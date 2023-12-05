public class Main {
    public static void main(String[] args) {

        Service s = new Service("plane_ticket");

        s.setData("departure_date", "05-12-2023");
        s.setData("departure_time", "10:00");
        s.setData("departure_airport", "Paris CDG");
        s.setData("arrival_date", "06-12-2023");
        s.setData("arrival_time", "08:00");
        s.setData("arrival_airport", "Tokyo Haneda");

        System.out.println(s);
    }
}