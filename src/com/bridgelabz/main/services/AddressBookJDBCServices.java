package com.bridgelabz.main.services;



import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bridgelabz.main.exception.AddressBookDBException;
import com.bridgelabz.main.services.AddressBookService.IOService;

public class AddressBookJDBCServices implements IOServices{
	private PreparedStatement contactPreparedStatement;
	private static AddressBookJDBCServices addressBookJDBCServices;

	public AddressBookJDBCServices() {
	}

	public static AddressBookJDBCServices getInstance() {
		if (addressBookJDBCServices == null)
			addressBookJDBCServices = new AddressBookJDBCServices();
		return addressBookJDBCServices;
	}

	private synchronized Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/address_book_service?useSSL=false";
		String userName = "root";
		String password = "arjun321";
		return DriverManager.getConnection(jdbcURL, userName, password);
	}


	public List<Contact> readData() {
		String sql = String.format("select * from address_book");
		return getContactList(sql);
	}

	private List<Contact> getContactList(String sql) {
		List<Contact> contactList = new ArrayList<>();
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				contactList.add(new Contact(resultSet.getString("first_name"), resultSet.getString("last_name"),
						resultSet.getString("address"), resultSet.getString("city"), resultSet.getString("state"),
						resultSet.getString("zip"), resultSet.getString("phone_number"), resultSet.getString("email")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contactList;
	}
	
	public List<Contact> getContacts(String firstName) {
		String sql = String.format("select * from address_book where first_name = '%s'", firstName);
		return getContactList(sql);
	}
	
	public List<Contact>getContactForDateRange(LocalDate startDate, LocalDate endDate){
		String sql=String.format("SELECT * FROM address_book Where date_added between '%s' and '%s';",
				Date.valueOf(startDate),Date.valueOf(endDate));
		return getContactList(sql);
	}

	public int updateContactUsingSQL(String firstName, String column, String columnValue) {
		String sql = String.format("UPDATE address_book SET %s = '%s'  WHERE first_name = '%s';", column, columnValue,
				firstName);
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Contact> getContactsByField(String columnName, String columnValue) {
		String sql = String.format("select * from address_book where %s = '%s'", columnName,columnValue);
		return getContactList(sql);
	}
	
	public Contact insertNewContactToDB(String date, String firstName, String lastName, String address, String city,
			String state, String zip, String phoneNo, String email) throws AddressBookDBException {
		String sql = String.format(
				"INSERT INTO address_book (date_added,first_name,last_name,address,city,state,zip,phone_number,email) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s');",
				date, firstName, lastName, address, city, state, zip, phoneNo, email);
		Contact contact = null;
		try (Connection connection = getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			int result = preparedStatement.executeUpdate();
			if (result == 1)
				contact = new Contact(firstName, lastName, address, city, state, zip, phoneNo, email);
		} catch (SQLException e) {
			throw new AddressBookDBException("Wrong SQL or field given",
					AddressBookDBException.ExceptionType.WRONG_SQL);
		}
		return contact;
	}
	
	@Override
	public void writeData(List<Contact> contacts) throws IOException {
		Map<Integer, Boolean> status = new HashMap<>();
		contacts.forEach(contact -> {
			status.put(contact.hashCode(), false);
			Runnable task = () -> {
				try {
					this.insertNewContactToDB(LocalDate.now().toString(), contact.getFirstName(), contact.getLastName(),
							contact.getAddress(), contact.getCity(), contact.getState(), contact.getZip(),
							contact.getPhoneNo(), contact.getEmail());
					status.put(contact.hashCode(), true);
				} catch (AddressBookDBException e) {
				}
			};
			Thread thread = new Thread(task, contact.getFirstName());
			thread.start();
		});
		while (status.containsValue(false))
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
	}

	@Override
	public int updateContact(String firstName, String column, String columnValue) {
		return updateContactUsingSQL(firstName, column, columnValue);
	}

	@Override
	public List<Contact> getContactsByCity(String cityName) {
		return getContactsByField("city", cityName);
	}

	@Override
	public List<Contact> getContactsByState(String stateName) {
		return getContactsByField("state", stateName);
	}
}