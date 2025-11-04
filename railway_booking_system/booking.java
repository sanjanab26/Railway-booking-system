// Placeholder file retained intentionally. The public Booking class is defined in Booking.java.
// Keeping this file prevents breaking existing IDE state while avoiding duplicate type definitions.
import java.time.LocalDateTime;

// Booking class connects a Passenger to a Train and calculates total fare
public class booking {
    private static int nextId = 1000; // Auto-increment ID start

    private final int booking;
    private final Passenger passenger;
    private final Train train;
    private final int seatsBooked;
    private final double totalFare;
    private final LocalDateTime createdAt;

    public booking(Passenger passenger, Train train, int seatsBooked) {
        this.booking = nextId++;
        this.passenger = passenger;
        this.train = train;
        this.seatsBooked = seatsBooked;
        this.totalFare = train.calculateFare(seatsBooked);
        this.createdAt = LocalDateTime.now();
    }

    public int getBookingId() { return booking; }
    public Passenger getPassenger() { return passenger; }
    public Train getTrain() { return train; }
    public int getSeatsBooked() { return seatsBooked; }
    public double getTotalFare() { return totalFare; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return String.format("#%d | %s | Train: %d - %s | Seats: %d | Total: %.2f | %s",
                booking,
                passenger.toString(),
                train.getTrainNumber(),
                train.getName(),
                seatsBooked,
                totalFare,
                createdAt.toString());
    }

    public void add(booking booking2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    public static boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }
}


