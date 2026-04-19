
package booking;


public class Ticket {

    private static int idCounter = 1;   // shared by all tickets
    private int ticketID;               // unique per ticket

    private String passenger_name;
    private double ticket_price;

    // ----------- Constructors -----------

    public Ticket() {
        this.ticketID = idCounter++;  // store unique ID
        this.passenger_name = "Unknown";
        this.ticket_price = 250;
    }

    public Ticket(Ticket t) {
        this.ticketID = idCounter++;  // new unique ID
        this.passenger_name = t.passenger_name;
        this.ticket_price = t.ticket_price;
    }

    public Ticket(String passenger_name, double ticket_price) {
        this.ticketID = idCounter++;  // new unique ID
        this.passenger_name = passenger_name;
        this.ticket_price = ticket_price;
    }


    public Ticket(User user, Show show, Seat s) {
    }

    // ----------- Getters / Setters -----------

    public int getTicketID() {
        return ticketID;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public double getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(double ticket_price) {
        this.ticket_price = ticket_price;
    }

    // ----------- Methods -----------

    public void displayticketinfo() {
        System.out.println("Ticket ID: " + ticketID);
        System.out.println("Passenger Name: " + passenger_name);
        System.out.println("Ticket Price: " + ticket_price);
    }
}

