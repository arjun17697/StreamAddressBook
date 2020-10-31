package com.bridgelabz.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
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
	
	@Test
	public void givenDateRange_WhenRetrievedContactInfo_ShouldMatchCount() throws AddressBookDBException{
		LocalDate startDate = LocalDate.of(2019, 01, 01);
		LocalDate endDate= LocalDate.now();
		List<Contact> contactList=addressBookService.getContactsForDateRange(startDate,endDate);
		
	}
	
	@Test
	public void givenAddressBookData_whenRetreivedByCity_ShouldMatchContactCount() {
		List<Contact>contactList=addressBookService.getContactsByCity("Bhopal");
		assertEquals(1,contactList.size());
	}
	
	@Test
	public void givenAddressBookData_whenRetreivedByState_ShouldMatchContactCount() {
		List<Contact>contactList=addressBookService.getContactsByCity("mp");
		assertEquals(1,contactList.size());
	}
	
	@Test
	public void givenContactData_WhenAddedToDB_ShouldSyncWithDB() throws AddressBookDBException {
		addressBookService.addNewContact("2018-08-08", "Trisha", "Krishnan", "68/1 Srishti Complex", "Ernakulam",
				"Kerala", "682011", "8725120000", "trisha@person.com");
		boolean isSynced = addressBookService.isAddressBookSyncedWithDB("Trisha");
		assertTrue(isSynced);
	}
}
