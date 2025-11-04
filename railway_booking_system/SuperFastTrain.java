// Another concrete Train type demonstrating method overriding
public class SuperFastTrain extends Train {
    public SuperFastTrain(int trainNumber, String name, String source, String destination,
                          int totalSeats, int availableSeats, double baseFare) {
        super(trainNumber, name, source, destination, totalSeats, availableSeats, baseFare);
    }

    // SuperFast trains charge 25% extra
    @Override
    public double calculateFare(int seats) {
        if (seats <= 0) return 0.0;
        double perSeat = getBaseFare() * 1.25; // 25% extra
        return perSeat * seats;
    }
}
