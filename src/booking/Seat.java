

package booking;

public class Seat {
  private  String  seat_number;
   private  String seat_type;
   private  boolean is_available;

   public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
   public  Seat(String seat_number, String seat_type, boolean is_available) {
        this.seat_number = seat_number;
        this.seat_type = seat_type;
        this.is_available = is_available;
    }
public Seat() {
    this.seat_number = "A1";
    this.seat_type = "Standard";
    this.is_available = true;   
    }
    public Seat(Seat S) {
        this.seat_number = S.seat_number;
        this.seat_type =S.seat_type;
        this.is_available = S.is_available;   
    }
   public  void displaySeatInfo() {
        System.out.println("Seat Number: " + seat_number);
        System.out.println("Seat Type: " + seat_type);
        System.out.println("Is Available: " + is_available);
    }

}



