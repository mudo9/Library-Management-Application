package uk.ac.sheffield.com1003.library;

import java.text.ParseException;
import java.util.Objects;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String fullName) throws ParseException {
        String[] names = fullName.split(" ");
        if (names.length < 2) {
            throw new ParseException("Invalid full name format.",0);
        }
        this.firstName = names[0];
        this.lastName = names[1];
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns a string representation of a person
     *
     * @return A string representation of a person in
     * the format "<last name>, <first name>"
     */
    @Override
    public String toString() {
        return lastName + ", " + firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
