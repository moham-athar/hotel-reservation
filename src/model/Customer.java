package model;

import java.util.regex.Pattern;


public record Customer(String firstName, String lastName, String email) {
    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    private boolean isValidEmail(String email) {
        final Pattern EMAIL_PATTERN =
                Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String toString() {
        return "Customer " + firstName + " " + lastName + ", " + email + ".";
    }
}
