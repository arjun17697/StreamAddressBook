package com.bridgelabz;

import java.io.*;

import java.util.*;
import java.util.stream.Collectors;

import com.bridgelabz.info;

public class AddressBookMain {
	private HashMap<String, ArrayList<info>> deathNote;
	private static ArrayList<info> friends;

	public AddressBookMain() {
		friends = new ArrayList<info>();
		deathNote = new HashMap<String, ArrayList<info>>();
	}

	public void addNewContact(info entry) {
		friends.add(new info(entry.fName, entry.lName, entry.address, entry.city, entry.state, entry.zip, entry.phone,
				entry.email));
	}

	public void addAddressBook(String bookName) {
		deathNote.put(bookName, new ArrayList<info>());
	}

	public static info add() {

		// method for adding new entries.
		Scanner stdlin = new Scanner(System.in);
		// Attributes to be added

		// asking user input
		System.out.println("enter details ");
		System.out.print("First Name: ");
		String fName = stdlin.next();
		System.out.print("Last Name: ");
		String lName = stdlin.next();
		System.out.print("Address: ");
		String address = stdlin.next();
		System.out.print("City: ");
		String city = stdlin.next();
		System.out.print("State: ");
		String state = stdlin.next();
		System.out.print("ZipCode: ");
		String zip = stdlin.next();
		System.out.print("Phone No.: ");
		String phone = stdlin.next();
		System.out.print("Email: ");
		String email = stdlin.next();

		// saving as new entry
		info contact = new info(fName, lName, address, city, state, zip, phone, email);
		return contact;
	}

	public static ArrayList<info> edit(ArrayList<info> list, String name) {

		// method for edit
		Scanner stdlin = new Scanner(System.in);
		boolean flag = false;

		for (info entry : list) {

			if (entry.fName.equals(name)) {
				flag = true;
				System.out.println("Please enter new details.");
				System.out.print("Address: ");
				entry.address = stdlin.next();
				System.out.print("City: ");
				entry.city = stdlin.next();
				System.out.print("State: ");
				entry.state = stdlin.next();
				System.out.print("ZipCode: ");
				entry.zip = stdlin.next();
				System.out.print("Phone No.: ");
				entry.phone = stdlin.next();
				System.out.print("Email: ");
				entry.email = stdlin.next();

				System.out.println("Contact updated.");
				break;
			}
		}
		if (flag == false) {
			System.out.println("Contact not found");
		}
		return list;
	}

	public static ArrayList<info> delete(ArrayList<info> list, String name) {

		// method for delete
		Scanner stdlin = new Scanner(System.in);
		boolean flag = false;

		try {
			for (info entry : list) {

				if (entry.fName.equals(name)) {
					flag = true;

					System.out.println("Contact deleted for " + entry.fName);
					list.remove(entry); // delete entry from record

				}
			}
		} catch (Exception e) {
		}
		if (flag == false) {
			System.out.println("No entry found for " + name);
		}
		return list;
	}

	public static void main(String[] args) {

		Scanner stdlin = new Scanner(System.in);
		AddressBookMain makeentry = new AddressBookMain();

		makeentry.addAddressBook("AddressBook1");

		// initiating user functions of entries

		String user_input = "1";
		while ((user_input.equals("1") || user_input.equals("2") || user_input.equals("3"))) {

			// Checking in address list is present in hashmap
			System.out.print("Enter the Name of the Address Book: ");
			String bookName = stdlin.next();
			if (makeentry.deathNote.containsKey(bookName)) {
				makeentry.friends = makeentry.deathNote.get(bookName);
			}

			else {
				System.out.println("Address book with name " + bookName + " not found. Creating a new entry");
				makeentry.addAddressBook(bookName);
			}

			System.out.println("Record " + bookName + " loaded.");

			System.out.println("1. Add a new contact.");
			System.out.println("2. Edit an existing contact.");
			System.out.println("3. Delete an existing contact.");

			user_input = stdlin.next();

			switch (user_input) {

			case "1": {
				info contact = makeentry.add(); // calling function to make new entry
				makeentry.addNewContact(contact); // Adding entry to record
				System.out.println(contact);
				break;
			}
			case "2": {
				System.out.println("Please enter First name of entry to be edited.");
				String name = stdlin.next();
				ArrayList<info> list = makeentry.edit(friends, name);
				break;
			}
			case "3": {
				System.out.println("Please enter First name of entry to be deleted.");
				String name = stdlin.next();
				friends = delete(friends, name);
				break;
			}
			default:
				break;
			}
		}

		// Removing duplicates (Only first entry is preserved,rest removed)
		List<info> noDuplicates = friends.stream().distinct().collect(Collectors.toList());
		System.out.println("List with duplicates removed:" + noDuplicates);

	}
}