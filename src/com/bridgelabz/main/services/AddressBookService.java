package com.bridgelabz.main.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bridgelabz.main.FileIO;
import com.bridgelabz.main.exception.AddressBookDBException;
import com.bridgelabz.main.exception.AddressBookDBException.ExceptionType;
import com.bridgelabz.main.services.AddressBookService.IOService;

public class AddressBookService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO, CSV, JSON
	}

	private List<Contact> contactList;
	private AddressBookJDBCServices addressBookJDBCServices;

	public AddressBookService() {
		addressBookJDBCServices = AddressBookJDBCServices.getInstance();
	}

	public AddressBookService(List<Contact> employeePayrollList) {
		this();
		this.contactList = employeePayrollList;
	}

	public List<Contact> readContactData(IOService ioService) {
		if (ioService.equals(IOService.FILE_IO))
			contactList = new FileIO().readData();
		if (ioService.equals(IOService.DB_IO))
			contactList = addressBookJDBCServices.readData();
		return contactList;
	}

	public void updateCity(String firstName, String city) throws AddressBookDBException {
		int result = addressBookJDBCServices.updateContactUsingSQL(firstName, "city", city);
		Contact contact = getContactData(firstName);
		if (result != 0 && contact != null)
			contact.setCity(city);
		if (result == 0)
			throw new AddressBookDBException("Wrong name given", ExceptionType.WRONG_NAME);
		if (contact == null)
			throw new AddressBookDBException("No data found", ExceptionType.NO_DATA_FOUND);
	}

	public boolean isAddressBookSyncedWithDB(String firstName) {
		Contact contact = getContactData(firstName);
		return addressBookJDBCServices.getContacts(firstName).get(0).equals(contact);
	}

	public List<Contact> getContactsForDateRange(LocalDate startDate, LocalDate endDate) {
		return addressBookJDBCServices.getContactForDateRange(startDate, endDate);
	}

	private Contact getContactData(String name) {
		readContactData(IOService.DB_IO);
		return contactList.stream().filter(e -> e.getFirstName().equals(name)).findFirst().orElse(null);
	}

	public List<Contact> getContactsByCity(String cityName) {
		return addressBookJDBCServices.getContactsByField("city", cityName);
	}

	public List<Contact> getContactsByState(String stateName) {
		return addressBookJDBCServices.getContactsByField("state", stateName);
	}

	public Contact addNewContact(String date, String firstName, String lastName, String address, String city,
			String state, String zip, String phoneNo, String email) throws AddressBookDBException {
		return addressBookJDBCServices.insertNewContactToDB(date, firstName, lastName, address, city, state, zip,
				phoneNo, email);
	}

	public void addNewMultipleContacts(List<Contact> contacts) throws AddressBookDBException {
		Map<Integer, Boolean> status = new HashMap<>();
		contacts.forEach(contact -> {
			status.put(contact.hashCode(), false);
			Runnable task = () -> {
				try {
					addressBookJDBCServices.insertNewContactToDB("2020-10-30", contact.getFirstName(),
							contact.getLastName(), contact.getAddress(), contact.getCity(), contact.getState(),
							contact.getZip(), contact.getPhoneNo(), contact.getEmail());
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
	

	public void removeContact(String firstName) {
		Contact contact = contactList.stream().filter(e -> e.getFirstName().equals(firstName)).findFirst().orElse(null);
		contactList.remove(contact);
	}
	public Contact getContactByName(String name) {
		return contactList.stream().filter(e -> e.getFirstName().equals(name)).findFirst().orElse(null);
	}
	
}
