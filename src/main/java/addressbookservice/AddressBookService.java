package addressbookservice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

    public enum IOService {DB_IO, REST_IO}

    private List<Person> personList;

    private AddressBookDBService addressBookDBService;

    public AddressBookService () {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public AddressBookService(List<Person> personList) {
        this();
        this.personList = personList;
    }

    public List<Person> readAddressBookData(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            this.personList = addressBookDBService.readData();
        return personList;
    }

    public void updateContactNumber(String firstName, String phoneNumber) {
        int result = addressBookDBService.updateContactNumber(firstName, phoneNumber);
        if (result == 0) return;
        Person person = this.getPersonData(firstName);
        if (person != null) person.phoneNumber = phoneNumber;
    }

    public boolean checkAddressBookInSyncWithDB(String firstName) {
        List<Person> personList = addressBookDBService.getPersonData(firstName);
        return personList.get(0).equals(getPersonData(firstName));
    }

    private Person getPersonData(String firstName) {
        return this.personList.stream()
                .filter(personDataItem -> personDataItem.firstName.equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public List<Person> readAddressBookForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if(ioService.equals(IOService.DB_IO))
            return addressBookDBService.getAddressBookForDateRange(startDate, endDate);
        return null;
    }

    public List<Person> countPeopleFromGivenCity(IOService ioService, String city) {
        return addressBookDBService.countPeopleFromGivenCity(city);
    }

    public void addPersonToAddressBook(int id, String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String emailId, LocalDate startDate) {
        personList.add(addressBookDBService.addPersonToAddressBook(id, firstName, lastName, address, city, state, zip, phoneNumber, emailId, startDate));
    }


    public static void main(String[] args){
        System.out.println("Welcome to Address Book Service Database");
    }
}
