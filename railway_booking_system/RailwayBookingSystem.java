import java.util.*;

// Main application class providing a menu-driven console UI
public class RailwayBookingSystem {
    private final List<Train> trains = new ArrayList<>();
    private final List<booking> bookings = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new RailwayBookingSystem().run();
    }

    private void run() {
        seedData();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1:
                    viewTrains();
                    break;
                case 2:
                    bookTickets();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private void seedData() {
        trains.add(new StandardTrain(10101, "Local Express", "Chennai", "Madurai", 300, 300, 150.0));
        trains.add(new ExpressTrain(12245, "Shatabdi", "Chennai", "Bengaluru", 200, 200, 450.0));
        trains.add(new SuperFastTrain(22209, "Rajdhani", "Chennai", "Delhi", 180, 180, 1200.0));
        trains.add(new StandardTrain(13131, "Coastal Rider", "Chennai", "Puducherry", 150, 150, 120.0));
    }

    private void printMenu() {
        System.out.println("================ Railway Booking System ================");
        System.out.println("1. View available trains");
        System.out.println("2. Book tickets");
        System.out.println("3. View all bookings");
        System.out.println("4. Cancel a booking");
        System.out.println("5. Exit");
        System.out.println("========================================================");
    }

    private void viewTrains() {
        if (trains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }
        System.out.println(String.format("%-6s | %-15s | %-12s -> %-12s | %-15s | %-10s",
                "No.", "Name", "Source", "Destination", "Avail/Total", "Base Fare"));
        System.out.println("--------------------------------------------------------------------------------");
        for (Train t : trains) {
            System.out.printf("%-6d | %-15s | %-12s -> %-12s | %3d/%-3d       | %.2f%n",
                    t.getTrainNumber(), t.getName(), t.getSource(), t.getDestination(),
                    t.getAvailableSeats(), t.getTotalSeats(), t.getBaseFare());
        }
    }

    private void bookTickets() {
        int trainNo = readInt("Enter train number: ");
        Train train = findTrain(trainNo);
        if (train == null) {
            System.out.println("Train not found.");
            return;
        }
        if (train.getAvailableSeats() <= 0) {
            System.out.println("No seats available on this train.");
            return;
        }
        System.out.print("Enter passenger name: ");
        String name = scanner.nextLine().trim();
        int age = readInt("Enter age: ");
        System.out.print("Enter gender (M/F/O): ");
        String gender = scanner.nextLine().trim();
        int seats = readInt("Enter number of seats to book: ");

        if (seats <= 0) {
            System.out.println("Invalid seats count.");
            return;
        }
        if (seats > train.getAvailableSeats()) {
            System.out.printf("Only %d seats available.%n", train.getAvailableSeats());
            return;
        }

        Passenger passenger = new Passenger(name, age, gender);
        boolean ok = train.bookSeats(seats);
        if (!ok) {
            System.out.println("Booking failed due to seat availability.");
            return;
        }
        booking booking = new booking(passenger, train, seats);
        bookings.add(booking);
        System.out.println("Booking successful!");
        System.out.println(booking);
    }

    private void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
            return;
        }
        System.out.println("---------------------- All Bookings ---------------------");
        for (booking b : bookings) {
            System.out.println(b);
        }
    }

    private void cancelBooking() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings to cancel.");
            return;
        }
        int id = readInt("Enter Booking ID to cancel: ");
        booking toRemove = null;
        for (booking b : bookings) {
            if (b.getBookingId() == id) {
                toRemove = b; break;
            }
        }
        if (toRemove == null) {
            System.out.println("Booking ID not found.");
            return;
        }
        // Restore seats on the train
        toRemove.getTrain().cancelSeats(toRemove.getSeatsBooked());
        bookings.remove(toRemove);
        System.out.println("Booking cancelled successfully.");
    }

    private Train findTrain(int number) {
        for (Train t : trains) {
            if (t.getTrainNumber() == number) return t;
        }
        return null;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
