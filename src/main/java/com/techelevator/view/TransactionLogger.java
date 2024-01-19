package com.techelevator.view;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

public class TransactionLogger {
    private static final String LOG_FILE_NAME = "Log.txt";
    public static void logTransaction(String transactionType, double amount, double newBalance) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_NAME, true))) {
            String timeStamp = getCurrentTimestamp();
            String logEntry = String.format("%s %s: $%.2f $%.2f", timeStamp, transactionType, amount, newBalance);
            writer.println(logEntry);



        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private static String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        return dateFormat.format(new Date());
    }


}
