
package booking;


public class MovieShow extends Show {

    private String genre;

    public MovieShow(String name, String time, String date, Seat[] seats, String genre) {
        super(name, time, date, seats);
        this.genre = genre;
    }

    @Override
    public void displayShowInfo() {
        System.out.println("=== Movie Show ===");
        System.out.println("Genre: " + genre);
        super.displayShowInfo();
    }

    @Override
    public boolean bookSeat(String seatNumber) {
        for (Seat s : getSeats()) {
            if (s.getSeat_number().equals(seatNumber) && s.isIs_available()) {
                s.setIs_available(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean cancelSeat(String seatNumber) {
        for (Seat s : getSeats()) {
            if (s.getSeat_number().equals(seatNumber) && !s.isIs_available()) {
                s.setIs_available(true);
                return true;
            }
        }
        return false;
    }
}
