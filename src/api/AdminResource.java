package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.List;

public class AdminResource {
    private static final CustomerService customerService = new CustomerService();
    private static final ReservationService reservationService = new ReservationService();

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public void addRoom(IRoom rooms) {
        reservationService.addRoom(rooms);
    }

    public List<IRoom> getAllRoom() {
        return reservationService.allRooms();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomer();
    }

    public List<IRoom> getAllRooms() {
        return reservationService.allRooms();
    }
}
