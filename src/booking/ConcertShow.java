
package booking;


public class ConcertShow extends Show implements Performable {
    private String singer;

    public ConcertShow(String name, String time, String date, Seat[] seats, String singer) {
        super(name, time, date, seats);
        this.singer = singer;
    }

    @Override
    public void displayShowInfo() {
        System.out.println("=== Concert Show ===");
        System.out.println("Singer: " + singer);
        super.displayShowInfo();
    }

    // Implement abstract methods
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

    // New interface method
    @Override
    public void perform() {
        System.out.println("Concert is being performed by " + singer + "!");
    }
}
