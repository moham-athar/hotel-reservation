package service;

import model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static final ArrayList<Customer> customers = new ArrayList<Customer>();

    public List<Customer> getCustomerObject(String customerEmail) {

        List<Customer> customerList = new ArrayList<>();
        for (Customer customer :  customers) {
            if (customer.email().equals(customerEmail)) {
                customerList.add(customer);
            }
        }
        return customerList;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.add(customer);

        //placeholder for testing
        Customer customer1 = new Customer("jdhcdbchkdb", "ncducnucn", "hbhbfhh@gmail.com");
        customers.add(customer1);
    }

    public Customer getCustomer (String customerEmail) {
        for (Customer customer : customers) {
            if (customer.email().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public List<Customer> getAllCustomer() {
        return customers;
    }

}
