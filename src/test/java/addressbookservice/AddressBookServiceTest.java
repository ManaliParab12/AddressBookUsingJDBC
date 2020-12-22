package addressbookservice;

import org.junit.Assert;
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
}