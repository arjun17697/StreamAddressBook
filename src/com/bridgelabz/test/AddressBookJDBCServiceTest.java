package com.bridgelabz.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.main.exception.AddressBookDBException;
import com.bridgelabz.main.services.AddressBookJDBCServices;
import com.bridgelabz.main.services.AddressBookService;
import com.bridgelabz.main.services.Contact;
import com.bridgelabz.main.services.AddressBookService.IOService;

public class AddressBookJDBCServiceTest {
	AddressBookJDBCServices addressBookJDBCServices;
	AddressBookService addressBookService;

	@Before
	public void initialize() {
		addressBookJDBCServices = new AddressBookJDBCServices();
		addressBookService = new AddressBookService();
	}

	@Test
	public void givenAddressBookData_WhenRetrieved_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookJDBCServices.readData();
		assertEquals(7, contactList.size());
	}

	@Test
	public void givenName_WhenUpdatedContactInfo_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookService.updateCity("Arjun", "Pune");
		boolean isSynced = addressBookService.isAddressBookSyncedWithDB("Arjun");
		assertTrue(isSynced);
	}

	@Test
	public void givenDateRange_WhenRetrievedContactInfo_ShouldMatchCount() throws AddressBookDBException {
		LocalDate startDate = LocalDate.of(2019, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<Contact> contactList = addressBookService.getContactsForDateRange(startDate, endDate);

	}

	@Test
	public void givenAddressBookData_whenRetreivedByCity_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookService.getContactsByCity("Bhopal");
		assertEquals(1, contactList.size());
	}

	@Test
	public void givenAddressBookData_whenRetreivedByState_ShouldMatchContactCount() {
		List<Contact> contactList = addressBookService.getContactsByCity("mp");
		assertEquals(1, contactList.size());
	}

	@Test
	public void givenContactData_WhenAddedToDB_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookService.addNewContact("2018-08-08", "R1", "K1", "68 sr", "E1", "K1", "61", "8725125677",
				"t1@person.com");
		boolean isSynced = addressBookService.isAddressBookSyncedWithDB("R1");
		assertTrue(isSynced);
	}

	@Test
	public void givenMultipeContacts_WhenAddedToDBWithMultiThreads_ShouldSyncWithDB() throws AddressBookDBException {
		List<Contact> contacts = new ArrayList<>() {
			{
				add(new Contact("Trisha", "Krishnan", "68/1 Srishti Complex", "Ernakulam", "Kerala", "682011",
						"8725120000", "trisha@person.com"));
				add(new Contact("Faizal", "Ahmed", "68/1 Beauty Complex", "Aluva", "Kerala", "683022", "8725120022",
						"faizal@person.com"));
			}
		};
		addressBookService.addNewMultipleContacts(contacts);
		assertEquals(7, addressBookService.readContactData(IOService.DB_IO).size());
	}
}
