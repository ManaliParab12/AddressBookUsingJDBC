package addressbookservice;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddressBookServiceTest {

    @Test
    public void givenAddressBookDBWhenRetrivedShouldMatchPersonCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookDataList = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(4, addressBookDataList.size());
    }

    @Test
    public void givenContactInformationWhenUpdatedShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookDataList = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.updateContactNumber("Manali", "9045363759");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Manali");
        Assert.assertTrue(result);
    }

    @Test
    public void givenDateRangeWhenRetrievedShouldMatchEntryCount() {
        AddressBookService addressBookService = new AddressBookService();
        LocalDate startDate = LocalDate.of(2016, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<Person> addressBookDataList =
                addressBookService.readAddressBookForDateRange(AddressBookService
                        .IOService.DB_IO, startDate, endDate);
        Assert.assertEquals(4, addressBookDataList.size());
    }

    @Test
    public void givenCityWhenRetrievedShouldMatchEntryCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookDataList =
                addressBookService.countPeopleFromGivenCity(AddressBookService
                        .IOService.DB_IO, "Raigad");
        Assert.assertEquals(1, addressBookDataList.size());
    }

    @Test
    public void givenNewEntryWhenAddedShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.addPersonToAddressBook(7, "Priya", "Thakur",
                "MahadevNagar", "Dhule", "Maharashtra",
                432415, "9822625786", "thakurneha@gmail.com", LocalDate.now());
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Neha");
        Assert.assertTrue(result);
    }

    @Test
    public void given3PersonsWhenAddedTODBShouldMatchPersonEntries() {
        Person[] arrayOfPerson = {
                new Person(12,"Prajkta", "Kharat",  "mahadevPrth", "Goregaon", "Maharashtra", 879231, "8932456718", "prajktakharat01@gmail.com", LocalDate.now()),
                new Person(13,"Jignesh", "Tambade", "SantNagar", "Virar", "Mharashtra", 435276, "7276327108", "jignesht555@gmail.com", LocalDate.now()),
                new Person(14,"Pratibha", "Thakur", "MahadevNagar","Dhule", "Maharashtra", 432415, "9822625786", "thakurneha@gmail.com", LocalDate.now()),
        };
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        Instant start = Instant.now();
        addressBookService.addPersonsToAddressBook(Arrays.asList(arrayOfPerson));
        Instant end = Instant.now();
        System.out.println("Duration without Thread :" + Duration.between(start, end));
        Instant threadStart = Instant.now();
        addressBookService.addPersonsTOAddressBookWithThreads(Arrays.asList(arrayOfPerson));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with Thread :" + Duration.between(threadStart, threadEnd));
        boolean result = addressBookService.checkNameInDatabase("Pratibha");
        Assert.assertTrue(result);
    }
}