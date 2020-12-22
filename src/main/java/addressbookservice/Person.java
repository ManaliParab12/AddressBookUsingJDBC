package addressbookservice;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String emailId;
    public LocalDate startDate;

    public Person(int id, String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String emailId, LocalDate startDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "ID=" + id +
                ", FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", City='" + city + '\'' +
                ", State='" + state + '\'' +
                ", Zip=" + zip +
                ", PhoneNumber=" + phoneNumber +
                ", EmailId='" + emailId + '\'' +
                ", StartDate=" + startDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id == person.id &&
                zip == person.zip &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(city, person.city) &&
                Objects.equals(state, person.state) &&
                Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(emailId, person.emailId) &&
                Objects.equals(startDate, person.startDate);
    }
}
