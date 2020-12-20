package addressbookservice;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AddressBookServiceTest {

    @Test
    public void givenAddressBookDBWhenRetrivedShouldMatchPersonCount() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookDataList = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(3, addressBookDataList.size());
    }

    @Test
    public void givenContactInformationWhenUpdatedShouldSyncWithDB() {
        AddressBookService addressBookService = new AddressBookService();
        List<Person> addressBookDataList = addressBookService.readAddressBookData(AddressBookService.IOService.DB_IO);
        addressBookService.updateContactNumber("Manali", "9028363759");
        boolean result = addressBookService.checkAddressBookInSyncWithDB("Manali");
        Assert.assertTrue(result);
    }
}
