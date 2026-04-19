
package booking;

public abstract class Show {
    private String show_name;
    private String show_time;
    private String show_date;
    private Seat[] seats;

    // Getters and setters (same as before)
    public String getShow_name() { return show_name; }
    public void setShow_name(String show_name) { this.show_name = show_name; }
    public String getShow_time() { return show_time; }
    public void setShow_time(String show_time) { this.show_time = show_time; }
    public String getShow_date() { return show_date; }
    public void setShow_date(String show_date) { this.show_date = show_date; }



    public Seat[] getSeats() {
        return seats; // NO COPY
    }

    public void setSeats(Seat[] seats) {
        if (seats == null) {
            this.seats = null;
            return;
        }
        this.seats = new Seat[seats.length];
        for (int i = 0; i < seats.length; i++) {
            this.seats[i] = new Seat(seats[i]);
        }
    }

    // Constructors (default, param, copy) same as before
    public Show() {
        this.show_name = "Default Show";
        this.show_time = "18:00";
        this.show_date = "2024-01-01";
        this.seats = new Seat[0];
    }

    public Show(String show_name, String show_time, String show_date, Seat[] seats) {
        this.show_name = show_name;
        this.show_time = show_time;
        this.show_date = show_date;
        setSeats(seats);
    }

    public Show(Show other) {
        this.show_name = other.show_name;
        this.show_time = other.show_time;
        this.show_date = other.show_date;
        setSeats(other.seats);
    }

    public void displayShowInfo() {
        System.out.println("Show Name: " + show_name);
        System.out.println("Show Time: " + show_time);
        System.out.println("Show Date: " + show_date);
        System.out.println("Seats Information:");
        if (seats != null) {
            for (Seat seat : seats) {
                seat.displaySeatInfo();
                System.out.println("-----");
            }
        } else {
            System.out.println("No seats available.");
        }
    }

    // Abstract methods
    public abstract boolean bookSeat(String seatNumber);
    public abstract boolean cancelSeat(String seatNumber);
}
