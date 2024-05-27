package exercises;

import exercises.exceptions.EventException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Event event = null;

        System.out.println("*** Benvenuto/a! Qui puoi inserire un nuovo evento e gestire il numero di prenotazioni, iniziamo! ***");
        System.out.println("-------------------------------");

        while (event == null) {
            try {
                System.out.println("Inserisci il titolo dell'evento");
                String title = scanner.nextLine();

                String dateString = getDateFromUser(scanner).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                int seatingCapacity = getSeatingCapacityFromUser(scanner);

                event = new Event(title, dateString, seatingCapacity);
            } catch (EventException e) {
                System.out.println("Qualcosa è andato storto: " + e.getMessage() + " !!!");
                System.out.println("Ricomincia");
            }
        }

        System.out.println("Hai creato questo evento: " + event);
        boolean run = true;

        while(run) {
            System.out.println("Il tuo evento: " + event);
            System.out.println("--------------------------------------------");
            System.out.println("Numero di posti prenotati: " + event.getBookedSeats() + "; Numero di posti disponibili: " + event.getAvailableSeats() + ";");
            System.out.println("Cosa vuoi fare?");
            System.out.println("1 - Prenotare dei posti");
            System.out.println("2 - Disdire dei posti");
            System.out.println("3 - Uscire");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    book(scanner, event);
                    break;
                case "2":
                    cancel(scanner, event);
                    break;
                case "3":
                    run = false;
                    break;
                default:
                    System.out.println("Input non valido");
                    break;
            }
        }
        scanner.close();
    }

    private static LocalDate getDateFromUser(Scanner scanner) {
        LocalDate date = null;
        while (date == null) {
            try {
                System.out.println("Inserisci la data (dd-MM-yyyy)");
                String dateString = scanner.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido!");
            }
        }
        return date;
    }

    private static int getSeatingCapacityFromUser(Scanner scanner) {
        int seatingCapacity = 0;
        while (seatingCapacity == 0) {
            try {
                System.out.println("Inserisci la capienza massima dei posti");
                seatingCapacity = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Formato non valido!");
            }
        }
        return seatingCapacity;
    }

    private static void book(Scanner scanner, Event event) {
        int seatsToBook = 0;
        do {
            try {
                System.out.println("Quanti posti vuoi prenotare?");
                seatsToBook = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Formato numero invalido!");;
            }
        } while(seatsToBook == 0);
        try {
            event.book(seatsToBook);
            System.out.println("Prenotazione avvenuta con successo. Hai prenotato: " + seatsToBook + " posti");
        } catch (EventException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void cancel(Scanner scanner, Event event) {
        int seatsToCancel = 0;
        do {
            try {
                System.out.println("Quanti posti vuoi disdire?");
                seatsToCancel = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Errore, formato non valido!");;
            }
        } while (seatsToCancel == 0);
        try {
            event.cancel(seatsToCancel);
            System.out.println("Cancellazione avvenuta con successo. Hai disdetto: " + seatsToCancel + " posti");
        } catch (EventException e) {
            System.out.println(e.getMessage());
        }
    }
}
