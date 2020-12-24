package addressbookservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;

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

    @Test
    public void givenPersonDataInJSONServerWhenRetrivedShouldMatchTheCount() {
        Person[] arrayOfPersons = getPersonList();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfPersons));
        long entries = addressBookService.countEntries(AddressBookService.IOService.REST_IO);
        Assert.assertEquals(2, entries);
    }
}