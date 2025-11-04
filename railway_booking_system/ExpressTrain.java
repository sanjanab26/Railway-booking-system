// Concrete Train type with overridden fare calculation (Polymorphism)
public class ExpressTrain extends Train {
    public ExpressTrain(int trainNumber, String name, String source, String destination,
                        int totalSeats, int availableSeats, double baseFare) {
        super(trainNumber, name, source, destination, totalSeats, availableSeats, baseFare);
    }

    // Express trains charge 10% extra
    @Override
    public double calculateFare(int seats) {
        if (seats <= 0) return 0.0;
        double perSeat = getBaseFare() * 1.10; // 10% extra
        return perSeat * seats;
    }
}
