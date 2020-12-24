package addressbookservice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressBookService {

    public enum IOService {FILE_IO, DB_IO, REST_IO}

    private List<Person> personList;

    private AddressBookDBService addressBookDBService;

    public AddressBookService () {
        addressBookDBService = AddressBookDBService.getInstance();
    }

    public AddressBookService(List<Person> personList) {
        this();
        this.personList = new ArrayList<>(personList);
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

    Person getPersonData(String firstName) {
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
// UC 23
    public void addPersonToAddressBook(Person person, IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            this.addPersonToAddressBook(person.id, person.firstName, person.lastName, person.address, person.city, person.state, person.zip, person.phoneNumber, person.emailId, person.startDate);
        else personList.add(person);
    }

    public void addPersonToAddressBook(int id, String firstName, String lastName, String address, String city, String state, int zip, String phoneNumber, String emailId, LocalDate startDate) {
        personList.add(addressBookDBService.addPersonToAddressBook(id, firstName, lastName, address, city, state, zip, phoneNumber, emailId, startDate));
    }

    public void addPersonsToAddressBook(List<Person> personDataList) {
        personDataList.forEach(person -> {
            System.out.println("Person Being Added:" +person.firstName);
            this.addPersonToAddressBook(person.id, person.firstName, person.lastName, person.address, person.city, person.state, person.zip, person.phoneNumber, person.emailId, person.startDate);
            System.out.println("Person Added:" +person.firstName);
        });
        System.out.println(this.personList);
    }

    public void addPersonsTOAddressBookWithThreads(List<Person> personDataList) {
        Map<Integer, Boolean> employeeAdditionStatus = new HashMap<Integer, Boolean>();
        personDataList.forEach(person -> {
            Runnable task = () -> {
                employeeAdditionStatus.put(person.hashCode(), false);
                System.out.println("Employee Being Added:" + Thread.currentThread().getName());
                this.addPersonToAddressBook(person.id, person.firstName, person.lastName, person.address, person.city, person.state, person.zip, person.phoneNumber, person.emailId, person.startDate);
                employeeAdditionStatus.put(person.hashCode(), true);
                System.out.println("Employee Added:" + Thread.currentThread().getName());
            };
            Thread thread = new Thread(task, person.firstName);
            thread.start();
        });
        while (employeeAdditionStatus.containsValue(false)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) { }
        }
        System.out.println(this.personList);
    }

    public boolean checkNameInDatabase(String name) {
        boolean status = false;
        for (Person person : personList) {
            if (person.firstName.equalsIgnoreCase(name)) {
                status = true;
            }
        }
        return status;
    }


    public void deletePersonFromAddressBook(Person person, IOService ioService) {


    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            return new AddressBookFileIOService().countEntries();
        return personList.size();
    }



    public static void main(String[] args){
        System.out.println("Welcome to Address Book Service Database");
    }
}
