import java.util.Objects;

// Abstract base class representing a Train (Abstraction)
public abstract class Train {
    // Encapsulated fields (private with getters/setters)
    private final int trainNumber;
    private String name;
    private String source;
    private String destination;
    private int totalSeats;
    private int availableSeats;
    private double baseFare;

    public Train(int trainNumber, String name, String source, String destination,
                 int totalSeats, int availableSeats, double baseFare) {
        this.trainNumber = trainNumber;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.baseFare = baseFare;
    }

    // Polymorphic fare calculation to be overridden by subclasses
    public abstract double calculateFare(int seats);

    // Booking logic with validation
    public boolean bookSeats(int seats) {
        if (seats <= 0) return false;
        if (seats > availableSeats) return false;
        availableSeats -= seats;
        return true;
    }

    // Cancellation restores seat availability
    public void cancelSeats(int seats) {
        if (seats <= 0) return;
        availableSeats = Math.min(totalSeats, availableSeats + seats);
    }

    public int getTrainNumber() { return trainNumber; }
    public String getName() { return name; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public int getTotalSeats() { return totalSeats; }
    public int getAvailableSeats() { return availableSeats; }
    public double getBaseFare() { return baseFare; }

    public void setName(String name) { this.name = name; }
    public void setSource(String source) { this.source = source; }
    public void setDestination(String destination) { this.destination = destination; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    public void setBaseFare(double baseFare) { this.baseFare = baseFare; }

    @Override
    public String toString() {
        return String.format("%-6d | %-15s | %-12s -> %-12s | Seats: %3d/%-3d | Base Fare: %.2f",
                trainNumber, name, source, destination, availableSeats, totalSeats, baseFare);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return trainNumber == train.trainNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainNumber);
    }
}
