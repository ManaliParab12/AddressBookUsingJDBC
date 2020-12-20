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

}
