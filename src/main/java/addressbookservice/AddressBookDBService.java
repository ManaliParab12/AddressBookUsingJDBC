package addressbookservice;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {
    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    private AddressBookDBService() {
    }

    public static AddressBookDBService getInstance() {
        if (addressBookDBService == null)
            addressBookDBService = new AddressBookDBService();
        return addressBookDBService;
    }

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?useSSL=false";
        String userName = "root";
        String password = "Manali@123";
        Connection connection;
        System.out.println("Connectinng to database:" + jdbcURL);
        connection = DriverManager.getConnection(jdbcURL, userName, password);
        System.out.println("Connection is successful!!!" + connection);
        return connection;
    }

    public List<Person> readData() {
        String sql = "SELECT * FROM Person;";
        return this.getPersonDataUsingDB(sql);
    }

    private List<Person> getPersonDataUsingDB(String sql) {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            personList = this.getPersonData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public List<Person> getPersonData(String firstName) {
        List<Person> personList = null;
        if (this.addressBookDataStatement == null)
            this.prepareStatementForAddressBookData();
        try {
            addressBookDataStatement.setString(1, firstName);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            personList = this.getPersonData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }


    private List<Person> getPersonData(ResultSet resultSet) {
        List<Person> personList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String address = resultSet.getString("address");
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                int zip = resultSet.getInt("zip");
                String phoneNumber = resultSet.getString("phoneNumber");
                String emailId = resultSet.getString("emailId");
                LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
                personList.add(new Person(id, firstName, lastName, address, city, state, zip, phoneNumber, emailId, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    public int updateContactNumber(String firstName, String phoneNumber) {
        return this.updateAddressBookDataUsingStatement(firstName, phoneNumber);

    }

    private int updateAddressBookDataUsingStatement(String firstName, String phoneNumber) {
        String sql = String.format("UPDATE Person set phoneNumber = '%s' where firstName = '%s';", phoneNumber, firstName);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private void prepareStatementForAddressBookData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM Person WHERE firstName = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
