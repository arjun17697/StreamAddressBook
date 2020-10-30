package com.bridgelabz.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.main.AddressBookDBException;
import com.bridgelabz.main.AddressBookJDBCServices;
import com.bridgelabz.main.AddressBookService;
import com.bridgelabz.main.Contact;

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
}
