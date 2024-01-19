package com.techelevator.view;

import com.techelevator.Items.Product;
import com.techelevator.Items.VendingMachine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	public Boolean feedMoneyRequest(String moneyToFeed) {
		Object choice = null;
		try {
			double amountToFeed = Double.parseDouble(moneyToFeed);
			if (amountToFeed == (int) amountToFeed && amountToFeed > 0) {
				choice = 0;
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println("We can only feed a whole number!");
			return false;

		}
		if (choice == null && Double.parseDouble(moneyToFeed) > 0) {
			System.out.println(System.lineSeparator() + "*** " + moneyToFeed + " is not a whole number ***" + System.lineSeparator());
		} else if (choice == null && Double.parseDouble(moneyToFeed) < 0 ) {
			System.out.println(System.lineSeparator() + "*** " + moneyToFeed + " is not a positive number ***" + System.lineSeparator());

		}
		return false;
	}

	public String getMoneyToFeed() {
		System.out.println("Please insert money in whole dollars: ");
		return in.nextLine();
	}

	public String inputSlotLocation() {
		System.out.println("Please give slot location");
		return in.nextLine();
	}

	public void dispenseMessage(VendingMachine vendingMachine, String slotLocation) {
		List<Product> products = vendingMachine.getProducts();
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put("Chip", "Crunch Crunch, Yum!");
		messageMap.put("Candy", "Munch Munch, Yum!");
		messageMap.put("Drink", "Glug Glug, Yum!");
		messageMap.put("Gum", "Chew Chew, Yum!");

		for (Product product : products) {
			if (product.getSlotLocation().equals(slotLocation)) {
				String messageType = product.getType();
				String message = messageMap.get(messageType);
				if (message != null) {
					System.out.println(message);
				}
			}
		}
	}
}
