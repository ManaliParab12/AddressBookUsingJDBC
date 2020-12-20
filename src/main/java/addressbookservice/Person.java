package addressbookservice;

import java.time.LocalDate;

public class Person {
    public int id;
    public String firstName;
    public String lastName;
    public String city;
    public String state;
    public int zip;
    public String phoneNumber;
    public String emailId;
    public LocalDate startDate;

    public Person(int id, String firstName, String lastName, String city, String state, int zip, String phoneNumber, String emailId, LocalDate startDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
