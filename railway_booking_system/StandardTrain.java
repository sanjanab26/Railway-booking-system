// Standard train that uses base fare without surcharge
public class StandardTrain extends Train {
    public StandardTrain(int trainNumber, String name, String source, String destination,
                         int totalSeats, int availableSeats, double baseFare) {
        super(trainNumber, name, source, destination, totalSeats, availableSeats, baseFare);
    }

    @Override
    public double calculateFare(int seats) {
        if (seats <= 0) return 0.0;
        return getBaseFare() * seats; // No surcharge
    }
}