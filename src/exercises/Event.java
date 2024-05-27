package exercises;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event {

    // Attributi
    private String title;
    private LocalDate date;
    private final int totalSeats;
    private int bookedSeats;

    // Costruttore
    public Event(String title, String date, int totalSeats) throws IllegalArgumentException {
        this.title = validateTitle(title);
        this.date = validateDate(date);
        this.totalSeats = validateTotalSeats(totalSeats);
        this.bookedSeats = 0;
    }

    // Metodi
    @Override
    public String toString() {
        return getFormattedDate() + " - " + title;
    }

    // Metodi di validazione
    private LocalDate validateDate(String date) throws IllegalArgumentException {
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato data non valido. Il formato deve essere: 'dd-MM-yyyy'");
        }

        if (parsedDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Impossibile scegliere date già passate");
        }
        return parsedDate;
    }

    private String validateTitle(String title) throws IllegalArgumentException {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titolo non valido");
        }
        return title;
    }

    private int validateTotalSeats(int totalSeats) throws IllegalArgumentException {
        if (totalSeats <= 0) {
            throw new IllegalArgumentException("Il numero totale di posti deve essere positivo");
        }
        return totalSeats;
    }

    private int validateSeatsToBookOrCancel(int seats) throws IllegalArgumentException {
        if (seats <= 0) {
            throw new IllegalArgumentException("Il numero di posti da prenotare o disdire deve essere positivo");
        }
        return seats;
    }

    // Metodi per gestire le prenotazioni
    public void book(int seatsToBook) throws IllegalArgumentException {
        validateSeatsToBookOrCancel(seatsToBook);
        if (this.date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'evento è già passato");
        }
        if (bookedSeats + seatsToBook > totalSeats) {
            throw new IllegalArgumentException("Non ci sono abbastanza posti disponibili");
        }
        this.bookedSeats += seatsToBook;
    }

    public void cancel(int seatsToCancel) throws IllegalArgumentException {
        validateSeatsToBookOrCancel(seatsToCancel);
        if (this.date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("L'evento è già passato");
        }
        if (seatsToCancel > this.bookedSeats) {
            throw new IllegalArgumentException("Il numero di posti da disdire è maggiore del numero di posti prenotati");
        }
        this.bookedSeats -= seatsToCancel;
    }

    public int getAvailableSeats() {
        return this.totalSeats - this.bookedSeats;
    }


    // Getter e Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException {
        this.title = validateTitle(title);
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public void setDate(String date) throws IllegalArgumentException {
        this.date = validateDate(date);
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }
}
