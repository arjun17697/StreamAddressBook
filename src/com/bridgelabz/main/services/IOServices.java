package com.bridgelabz.main.services;

import java.io.IOException;
import java.util.List;

public interface IOServices {

		public void writeData(List<Contact> addressBook) throws IOException;

		public List<Contact> readData() throws IOException;

		public int updateContact(String firstName, String column, String columnValue);

		public List<Contact> getContactsByCity(String cityName);

		public List<Contact> getContactsByState(String stateName);
	}


