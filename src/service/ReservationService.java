package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservationService {
    private static final ArrayList<IRoom> rooms = new ArrayList<>();
    private static final ArrayList<Reservation> reservations = new ArrayList<>();

    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> roomWithDate = new ArrayList<>();
        try {
            for (Reservation reservation : reservations) {
                if (reservation.getCheckInDate() == checkInDate &&
                        reservation.getCheckOutDate() == checkOutDate) {
                        roomWithDate.add(reservation.getRoom());
                }
            }
        } catch (Exception e) {
            if (reservations.isEmpty()) return null;
        }
        return roomWithDate;
    }

    public List<Reservation> getCustomerReservation(Customer customer) {
        List<Reservation> getCustomer = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                getCustomer.add(reservation);
            }
        }
        return getCustomer;
    }

//    public List<Reservation> getCustomerReservation(Customer customerEmail) {
//        List<Reservation> getCustomerWithEmail = new ArrayList<>();
//
//        for (Reservation reservation : reservations) {
//            if (reservation.getCustomer().getEmail().equals(customerEmail)) {
//                getCustomerWithEmail.add(reservation);
//            }
//        }
//        return getCustomerWithEmail;
//    }

    public List<IRoom> allRooms() {
        return rooms;
    }

    public Date addDefaultPlusDays(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 7);

        return calendar.getTime();
    }

    public void printAllReservation() {
        System.out.println(reservations);
    }
}
