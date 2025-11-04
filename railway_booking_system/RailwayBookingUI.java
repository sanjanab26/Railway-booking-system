import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class RailwayBookingUI extends JFrame {
    private final List<Train> trains = new ArrayList<>();
    private final List<booking> bookings = new ArrayList<>();

    private JTable trainsTable;
    private DefaultTableModel trainsModel;

    private JTable bookingsTable;
    private DefaultTableModel bookingsModel;

    private JTextField nameField;
    private JSpinner ageSpinner;
    private JComboBox<String> genderBox;
    private JSpinner seatsSpinner;

    public RailwayBookingUI() {
        setTitle("Railway Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        seedData();
        initComponents();
        refreshTrains();
        refreshBookings();
    }

    private void seedData() {
        trains.add(new StandardTrain(10101, "Local Express", "Chennai", "Madurai", 300, 300, 150.0));
        trains.add(new ExpressTrain(12245, "Shatabdi", "Chennai", "Bengaluru", 200, 200, 450.0));
        trains.add(new SuperFastTrain(22209, "Rajdhani", "Chennai", "Delhi", 180, 180, 1200.0));
        trains.add(new StandardTrain(13131, "Coastal Rider", "Chennai", "Puducherry", 150, 150, 120.0));
    }

    private void initComponents() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Trains", buildTrainsPanel());
        tabs.add("Book", buildBookPanel());
        tabs.add("Bookings", buildBookingsPanel());
        setContentPane(tabs);
    }

    private JPanel buildTrainsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"No.", "Name", "Source", "Destination", "Available", "Total", "Base Fare"};
        trainsModel = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; }};
        trainsTable = new JTable(trainsModel);
        trainsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(trainsTable), BorderLayout.CENTER);

        JButton refreshBtn = new JButton(new AbstractAction("Refresh") {
            @Override public void actionPerformed(ActionEvent e) { refreshTrains(); }
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(refreshBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.LINE_END;
        form.add(new JLabel("Passenger Name:"), gc);
        gc.gridy++; form.add(new JLabel("Age:"), gc);
        gc.gridy++; form.add(new JLabel("Gender:"), gc);
        gc.gridy++; form.add(new JLabel("Seats:"), gc);

        gc.gridx = 1; gc.gridy = 0; gc.anchor = GridBagConstraints.LINE_START;
        nameField = new JTextField(18);
        form.add(nameField, gc);

        gc.gridy++;
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        ((JSpinner.DefaultEditor) ageSpinner.getEditor()).getTextField().setColumns(5);
        form.add(ageSpinner, gc);

        gc.gridy++;
        genderBox = new JComboBox<>(new String[]{"M","F","O"});
        form.add(genderBox, gc);

        gc.gridy++;
        seatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        ((JSpinner.DefaultEditor) seatsSpinner.getEditor()).getTextField().setColumns(5);
        form.add(seatsSpinner, gc);

        panel.add(form, BorderLayout.WEST);

        JButton bookBtn = new JButton(new AbstractAction("Book Selected Train") {
            @Override public void actionPerformed(ActionEvent e) { handleBook(); }
        });
        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("Select a train in the Trains tab and fill the details."), BorderLayout.NORTH);
        right.add(bookBtn, BorderLayout.SOUTH);
        panel.add(right, BorderLayout.CENTER);

        return panel;
    }

    private JPanel buildBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Booking ID", "Passenger", "Train No.", "Train Name", "Seats", "Total Fare", "Created"};
        bookingsModel = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c) { return false; }};
        bookingsTable = new JTable(bookingsModel);
        bookingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(bookingsTable), BorderLayout.CENTER);

        JButton cancelBtn = new JButton(new AbstractAction("Cancel Selected Booking") {
            @Override public void actionPerformed(ActionEvent e) { handleCancel(); }
        });
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(cancelBtn);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshTrains() {
        trainsModel.setRowCount(0);
        for (Train t : trains) {
            trainsModel.addRow(new Object[]{
                    t.getTrainNumber(),
                    t.getName(),
                    t.getSource(),
                    t.getDestination(),
                    t.getAvailableSeats(),
                    t.getTotalSeats(),
                    String.format("%.2f", t.getBaseFare())
            });
        }
    }

    private void refreshBookings() {
        bookingsModel.setRowCount(0);
        for (booking b : bookings) {
            bookingsModel.addRow(new Object[]{
                    b.getBookingId(),
                    b.getPassenger().toString(),
                    b.getTrain().getTrainNumber(),
                    b.getTrain().getName(),
                    b.getSeatsBooked(),
                    String.format("%.2f", b.getTotalFare()),
                    b.getCreatedAt().toString()
            });
        }
    }

    private void handleBook() {
        int selectedRow = trainsTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a train in the Trains tab.", "No Train Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int trainNo = (int) trainsModel.getValueAt(selectedRow, 0);
        Train train = findTrain(trainNo);
        if (train == null) {
            JOptionPane.showMessageDialog(this, "Selected train not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String name = nameField.getText().trim();
        int age = (Integer) ageSpinner.getValue();
        String gender = (String) genderBox.getSelectedItem();
        int seats = (Integer) seatsSpinner.getValue();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter passenger name.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (seats <= 0) {
            JOptionPane.showMessageDialog(this, "Seats must be positive.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (seats > train.getAvailableSeats()) {
            JOptionPane.showMessageDialog(this, "Only " + train.getAvailableSeats() + " seats available.", "Not Enough Seats", JOptionPane.WARNING_MESSAGE);
            return;
        }
        boolean ok = train.bookSeats(seats);
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Booking failed due to seat availability.", "Booking Failed", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Passenger passenger = new Passenger(name, age, gender);
        booking b = new booking(passenger, train, seats);
        bookings.add(b);
        refreshTrains();
        refreshBookings();
        nameField.setText("");
        ageSpinner.setValue(18);
        genderBox.setSelectedIndex(0);
        seatsSpinner.setValue(1);
        JOptionPane.showMessageDialog(this, "Booking successful!\n" + b.toString(), "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleCancel() {
        int row = bookingsTable.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a booking to cancel.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int bookingId = (int) bookingsModel.getValueAt(row, 0);
        booking toRemove = findBooking(bookingId);
        if (toRemove == null) {
            JOptionPane.showMessageDialog(this, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Cancel booking #" + bookingId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        toRemove.getTrain().cancelSeats(toRemove.getSeatsBooked());
        bookings.remove(toRemove);
        refreshTrains();
        refreshBookings();
        JOptionPane.showMessageDialog(this, "Booking cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
    }

    private Train findTrain(int number) {
        for (Train t : trains) {
            if (t.getTrainNumber() == number) return t;
        }
        return null;
    }

    private booking findBooking(int id) {
        for (booking b : bookings) {
            if (b.getBookingId() == id) return b;
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new RailwayBookingUI().setVisible(true);
        });
    }
}
