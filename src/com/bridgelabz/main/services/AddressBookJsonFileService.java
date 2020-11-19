package com.bridgelabz.main.services;

import java.io.IOException;
import java.util.List;

public class AddressBookJsonFileService implements IOServices {

	@Override
	public void writeData(List<Contact> addressBook) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Contact> readData() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateContact(String firstName, String column, String columnValue) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Contact> getContactsByCity(String cityName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Contact> getContactsByState(String stateName) {
		// TODO Auto-generated method stub
		return null;
	}

}
