package ui;

import api.AdminResource;
import api.HotelResource;
import model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = new HotelResource();;
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    public static void mainMenu() {
        String selectedMenu = "";
        Scanner scanner = new Scanner(System.in);

        displayMainMenu();

        try {
            do {
                selectedMenu = scanner.nextLine();

                if (selectedMenu.length() == 1) {
                    switch (selectedMenu.charAt(0)) {
                        case '1':
                            findAndReserveRoom();
                            break;
                        case '2':
                            seeMyReservations();
                            break;
                        case '3':
                            createAccount();
                            break;
                        case '4':
                            AdminMenu.adminMenu();
                            break;
                        case '5':
                            System.out.println("Exit");
                            break;
                        default:
                            System.out.println("Please enter a valid option number (1-5)\n");
                            break;
                    }
                } else {
                    System.out.println("Please enter a single-digit option number (1-5)\n");
                }
            } while (selectedMenu.charAt(0) != '5' || selectedMenu.length() != 1);
        } catch (Exception ex) {
            System.err.println("An error occurred while choosing main menu options: " + ex.getMessage());
        }
    }

    private static void displayMainMenu() {
        System.out.println("\nWelcome to the Hotel Reservation Application\n\n" +
                "-------------------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an Account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "-------------------------------------------------------\n" +
                "Please select a number for the menu option");
    }

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter CheckIn Date mm/dd/yyyy example 02/01/2020");
        Date checkIn = getDateFormat(scanner);

        System.out.println("Enter Check-Out Date mm/dd/yyyy example 02/21/2020");
        Date checkOut = getDateFormat(scanner);

        if (checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                System.out.println("No rooms available");
            } else {
                final Date alternativeCheckIn = hotelResource.addDefaultPlusDays(checkIn);
                final Date alternativeCheckOut = hotelResource.addDefaultPlusDays(checkOut);
                System.out.println("We've only found rooms on alternative dates:" +
                        "\nCheck-In Date:" + alternativeCheckIn +
                        "\nCheck-Out Date:" + alternativeCheckOut);

                printRooms(availableRooms);
            }
        }
    }

    private static void reserveRoom(final Scanner scanner, final Date checkInDate,
                                    final Date checkOutDate, final Collection<IRoom> rooms) {
        System.out.println("Would you like to book? y/n");
        final String bookRoom = scanner.nextLine();

        if ("y".equals(bookRoom)) {
            System.out.println("Do you have an account with us? y/n");
            final String haveAccount = scanner.nextLine();

            if ("y".equals(haveAccount)) {
                System.out.println("Enter Email format: name@domain.com");
                final String customerEmail = scanner.nextLine();

                if (hotelResource.getCustomer(customerEmail) == null) {
                    System.out.println("Customer not found.\nYou may need to create a new account.");
                } else {
                    System.out.println("What room number would you like to reserve?");
                    final String roomNumber = scanner.nextLine();

                    if (rooms.stream().anyMatch(room -> room.getRoomNumber().equals(roomNumber))) {
                        final IRoom room = hotelResource.getRoom(roomNumber);

                        final Reservation reservation = hotelResource
                                .bookARoom(customerEmail, room, checkInDate, checkOutDate);
                        System.out.println("Reservation created successfully!");
                        System.out.println(reservation);
                    } else {
                        System.out.println("Error: room number not available.\nStart reservation again.");
                    }
                }

                displayMainMenu();
            } else {
                System.out.println("Please, create an account.");
                displayMainMenu();
            }
        } else if ("n".equals(bookRoom)){
            displayMainMenu();
        } else {
            reserveRoom(scanner, checkInDate, checkOutDate, rooms);
        }
    }

    private static void printRooms(final Collection<IRoom> rooms) {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Email Address: name@domain.com");
        String customerEmail = scanner.nextLine();

        hotelResource.getCustomerReservation(customerEmail);
    }

    private static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter Email format: name@domain.com");
        String userEmail = scanner.nextLine();

        System.out.println("First Name");
        String firstName = scanner.nextLine();

        System.out.println("Last Name");
        String lastName = scanner.nextLine();

        try {
            hotelResource.createACustomer(userEmail, firstName, lastName);
            displayMainMenu();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
            createAccount();
        }
    }

    private static Date getDateFormat(Scanner scanner) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Invalid date format!");
            findAndReserveRoom();
        }

        return null;
    }
}