
package booking;

import java.util.ArrayList;

public class booking_system {
    private User[] users;
    private Show[] shows;
    ArrayList<Ticket> tickets;

    public booking_system(int maxUsers, int maxShows) {
        users = new User[maxUsers];
        shows = new Show[maxShows];
        tickets = new ArrayList<>();
    }

    public void addUser(User u) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = u;
                break;
            }
        }
    }

    public void addShow(Show s) {
        for (int i = 0; i < shows.length; i++) {
            if (shows[i] == null) {
                shows[i] = s;
                break;
            }
        }
    }
    public Ticket bookTicket(User user, Show show, String seatNumber) {

        // Find the real seat
        for (Seat s : show.getSeats()) {
            if (s.getSeat_number().equals(seatNumber) && s.isIs_available()) {

                s.setIs_available(false); // book seat
                Ticket ticket = new Ticket(user, show, s);
                tickets.add(ticket);
                return ticket;
            }
        }

        return null; // already booked
    }


    public void cancelTicket(Ticket ticket, Show show, String seatNumber) {
        if (ticket == null) return;
        show.cancelSeat(seatNumber);
        tickets.remove(ticket);
    }
}
