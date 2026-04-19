

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import booking.booking_system;
import booking.Ticket;
import booking.User;
import booking.Seat;
import booking.Show;
import booking.ConcertShow;
import booking.MovieShow;

public class Main extends Application {

    private booking_system system;
    private Show[] showsArray;
    private ObservableList<String> availableSeats;
    private ComboBox<String> seatCombo;
    private ObservableList<String> ticketsList;
    private ListView<String> ticketsListView;
    private java.util.ArrayList<Ticket> guiTickets = new java.util.ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        system = new booking_system(10, 10);

        // Sample seats for shows

        Seat[] seats1 = new Seat[20];
        Seat[] seats2 = new Seat[20];
        for (int i = 0; i < 20; i++) {
            seats1[i] = new Seat("A" + (i + 1), "Standard", true);
            seats2[i] = new Seat("B" + (i + 1), "Standard", true);
        }

        // Sample shows
        Show concertShow = new ConcertShow("Live Concert", "19:00", "2024-12-01", seats1, "BlackPink");

        Show movieShow = new MovieShow("Avengers", "21:00", "2024-12-10", seats2, "Action");


        system.addShow(concertShow);
        system.addShow(movieShow);

        showsArray = new Show[]{concertShow, movieShow};

        // ===== GUI Components =====
        Label title = new Label("🎟️ Booking System");
        title.setFont(Font.font(24));
        title.setTextFill(Color.DARKBLUE);

        // Show type selection
        Label showTypeLabel = new Label("Select Show Type:");
        ComboBox<String> showTypeCombo = new ComboBox<>();
        showTypeCombo.getItems().addAll("Concert", "Movie");
        showTypeCombo.getSelectionModel().selectFirst();

        // User info
        Label userInfoLabel = new Label("User Info:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        // Show selection
        Label showLabel = new Label("Select Show:");
        ComboBox<String> showCombo = new ComboBox<>();
        showCombo.getItems().addAll(concertShow.getShow_name(), movieShow.getShow_name());
        showCombo.getSelectionModel().selectFirst();

        // Seat selection
        Label seatLabel = new Label("Select Seat:");
        seatCombo = new ComboBox<>();
        updateSeatCombo(1); // default first show

        // Tickets display
        ticketsList = FXCollections.observableArrayList();
        ticketsListView = new ListView<>(ticketsList);
        ticketsListView.setPrefHeight(100);

        // Book Button
        Button bookBtn = new Button("Book Ticket");
        bookBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        Label confirmation = new Label();
        confirmation.setTextFill(Color.DARKGREEN);

        bookBtn.setOnAction(e -> bookTicket(usernameField, emailField, passwordField, showCombo, confirmation));

        // Cancel Button
        Button cancelBtn = new Button("Cancel Selected Ticket");
        cancelBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-font-weight: bold;");
        cancelBtn.setOnAction(e -> cancelTicket(showCombo, confirmation));

        // Filter shows based on type
        showTypeCombo.setOnAction(e -> {
            String type = showTypeCombo.getValue();
            showCombo.getItems().clear();
            for (Show s : showsArray) {
                if (type.equals("Concert") && s instanceof ConcertShow)
                    showCombo.getItems().add(s.getShow_name());
                else if (type.equals("Movie") && s instanceof MovieShow)
                    showCombo.getItems().add(s.getShow_name());
            }
            showCombo.getSelectionModel().selectFirst();
            updateSeatCombo(getShowIndex(showCombo));
        });

        showCombo.setOnAction(e -> updateSeatCombo(getShowIndex(showCombo)));

        // Layout
        VBox leftPane = new VBox(10, showTypeLabel, showTypeCombo, userInfoLabel, usernameField, emailField, passwordField, showLabel, showCombo, seatLabel, seatCombo, bookBtn, cancelBtn, confirmation);
        leftPane.setPadding(new Insets(15));
        leftPane.setAlignment(Pos.TOP_LEFT);
        leftPane.setStyle("-fx-border-color: darkblue; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: #E8F0FE;");

        VBox rightPane = new VBox(10, new Label("Booked Tickets:"), ticketsListView);
        rightPane.setPadding(new Insets(15));
        rightPane.setAlignment(Pos.TOP_LEFT);
        rightPane.setStyle("-fx-border-color: darkgreen; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: #F0FFF0;");

        HBox root = new HBox(20, leftPane, rightPane);
        root.setPadding(new Insets(15));

        VBox mainLayout = new VBox(15, title, root);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 700, 500);
        primaryStage.setTitle("Booking System GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Book ticket logic
    private void bookTicket(TextField usernameField, TextField emailField, PasswordField passwordField, ComboBox<String> showCombo, Label confirmation) {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        int showIndex = getShowIndex(showCombo);
        String seatNumber = seatCombo.getValue();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || seatNumber == null) {
            showAlert("Error", "Please fill all fields and select a seat.");
            return;
        }

        User user = new User(username, password, email);
        system.addUser(user);
        Show selectedShow = showsArray[showIndex];
        Ticket t = system.bookTicket(user, selectedShow, seatNumber);

        if (t != null) {
            guiTickets.add(t);
            ticketsList.add(username + " - " + selectedShow.getShow_name() + " - " + seatNumber);
            confirmation.setText("✅ Ticket booked successfully!");
            updateSeatCombo(showIndex);
        } else {
            showAlert("Booking Failed", "Seat is already booked.");
        }
    }

    // Cancel ticket logic
    private void cancelTicket(ComboBox<String> showCombo, Label confirmation) {
        int ticketIndex = ticketsListView.getSelectionModel().getSelectedIndex();
        int showIndex = getShowIndex(showCombo);
        if (ticketIndex >= 0 && showIndex >= 0) {
            Show selectedShow = showsArray[showIndex];
            Ticket t = guiTickets.get(ticketIndex);
            // Find seat number from tickets list
            String ticketText = ticketsList.get(ticketIndex);
            String seatNumber = ticketText.split(" - ")[2];
            system.cancelTicket(t, selectedShow, seatNumber);
            guiTickets.remove(ticketIndex);
            ticketsList.remove(ticketIndex);
            updateSeatCombo(showIndex);
            confirmation.setText("❌ Ticket canceled successfully!");
        } else {
            showAlert("Error", "Please select a ticket to cancel.");
        }
    }

    // Update available seats based on selected show
    private void updateSeatCombo(int showIndex) {
        Show show = showsArray[showIndex];
        availableSeats = FXCollections.observableArrayList();
        Seat[] seats = show.getSeats();
        for (Seat s : seats) {
            if (s.isIs_available()) availableSeats.add(s.getSeat_number());
        }
        seatCombo.setItems(availableSeats);
        if (!availableSeats.isEmpty()) seatCombo.getSelectionModel().selectFirst();
    }

    // Get index of show in showsArray based on ComboBox selection
    private int getShowIndex(ComboBox<String> showCombo) {
        String selected = showCombo.getValue();
        for (int i = 0; i < showsArray.length; i++) {
            if (showsArray[i].getShow_name().equals(selected)) return i;
        }
        return 0;
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}