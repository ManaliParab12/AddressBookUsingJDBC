package addressbookservice;

import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.util.Arrays;

public class AddressBookServiceRestTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Person[] getPersonList() {
        setup();
        Response response = RestAssured.get("/Persons");
        System.out.println("PERSON DATA IN JSON Server :\n" + response.asString());
        Person[] arrayOfPersons = new Gson().fromJson(response.asString(), Person[].class);
        return arrayOfPersons;
    }

    public Response addPersonToJsonServer(Person person) {
        String personJson = new Gson().toJson(person);
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.header("Content-Type", "application/json");
        requestSpecification.body(personJson);
        return requestSpecification.post("/Persons");
    }

    @Test
    public void givenPersonDataInJSONServerWhenRetrivedShouldMatchTheCount() {
        Person[] arrayOfPersons = getPersonList();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfPersons));
        long entries = addressBookService.countEntries(AddressBookService.IOService.REST_IO);
        Assert.assertEquals(2, entries);
    }


    @Test
    public void givenListOfNewPersonsWhenAddedShouldMatchResponse201AndCount(){
        Person[] arrayOfPersons = getPersonList();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfPersons));
        Person[] arrayOfPersonData = {
                new Person(3, "Priya", "Labde", "MahadevNagar", "Dhule", "Maharashtra", 432415, "9822625786", "thakurneha@gmail.com", LocalDate.now()),
                new Person(4, "Pratibha", "Thakur", "MahadevNagar", "Dhule", "Maharashtra", 432415, "9822625786", "thakurneha@gmail.com", LocalDate.now())
        };
        for(Person person : arrayOfPersonData) {
            Response response = addPersonToJsonServer(person);
            int statusCode = response.getStatusCode();
            Assert.assertEquals(201, statusCode);
            person = new Gson().fromJson(response.asString(), Person.class);
            addressBookService.addPersonToAddressBook(person, AddressBookService.IOService.REST_IO);
        }
        long entries = addressBookService.countEntries(AddressBookService.IOService.REST_IO);
        Assert.assertEquals(4, entries);
    }

        @Test
        public void givenNewContactNumberForPersonWhenUpdatedShouldMatch200Response(){
            Person[] arrayOfPersons = getPersonList();
            AddressBookService addressBookService;
            addressBookService = new AddressBookService(Arrays.asList(arrayOfPersons));

            addressBookService.updateContactNumber("Jignesh", "9045363759", AddressBookService.IOService.REST_IO);
            Person person = addressBookService.getPersonData("Jignesh");

            String personJson = new Gson().toJson(person);
            RequestSpecification requestSpecification = RestAssured.given();
            requestSpecification.header("Content-Type", "application/json");
            requestSpecification.body(personJson);
            Response response = requestSpecification.put("/Persons/" +person.id);
            int statusCode = response.getStatusCode();
            Assert.assertEquals(200, statusCode);
        }
}