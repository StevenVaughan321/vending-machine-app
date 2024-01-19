package com.techelevator;

import com.techelevator.Items.VendingMachine;
import com.techelevator.view.Menu;
import com.techelevator.view.TransactionLogger;

public class VendingMachineCLI {
	private static final String VENDING_ITEMS_PATH = "vendingmachine.csv";

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };


	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;

	}

	public void run() {
		VendingMachine vendingMachine = new VendingMachine(VENDING_ITEMS_PATH);
		boolean isOn = true;
		while (isOn) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vendingMachine.displayMenuItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				purchaseMenu(vendingMachine);

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println("Have a nice day!");
				isOn = false;

			}
		}
	}


	public void purchaseMenu(VendingMachine vendingMachine) {
		System.out.println("Current Money Provided: $" + vendingMachine.getCustomerBalance() + "\n");
		String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);



		if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
			String moneyToFeed = menu.getMoneyToFeed();

			if(menu.feedMoneyRequest(moneyToFeed)) {
				TransactionLogger.logTransaction("FEED MONEY",
						Double.parseDouble(moneyToFeed),
						Double.parseDouble(vendingMachine.getCustomerBalance()) + Double.parseDouble(moneyToFeed));

				 vendingMachine.customerFeedMoney(Double.parseDouble(moneyToFeed));
			}

			purchaseMenu(vendingMachine);

		} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
			vendingMachine.displayMenuItems();
			String slotLocation =menu.inputSlotLocation();

			if (vendingMachine.purchaseProduct(slotLocation)) {
				TransactionLogger.logTransaction(vendingMachine.getProductName(slotLocation),
						vendingMachine.getProductPrice(slotLocation),
						Double.parseDouble(vendingMachine.getCustomerBalance()));

				menu.dispenseMessage(vendingMachine, slotLocation);
			}
			purchaseMenu(vendingMachine);
		} else if (purchaseChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
			TransactionLogger.logTransaction("GIVE CHANGE", Double.parseDouble(vendingMachine.getCustomerBalance()), 0.00);
			vendingMachine.endTransaction();

		}

	}
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);

		cli.run();

	}
}
