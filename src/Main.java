package com.andustoica;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    public static WaterIndexData listIndexes = new WaterIndexData();

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        // Loading the text file where all the water meter records are stored, if it exists.
        File file = new File("DataBase.txt");
        if ((file.exists()) && file.length() != 1) {
            listIndexes.readData();
        }

        // Loading the value of deletionChecker from the text file, if it exists.
        File deletionChecker = new File("DeletionChecker.txt");
        if (!deletionChecker.exists()) {
            listIndexes.writeChecker();
        } else {
            listIndexes.readChecker();
        }


        Scanner scan = new Scanner(System.in);
        printOptions();
        boolean menu = false;

        while (!menu) {
            String input = scan.next();
            int option = isInt(input);

            switch (option) {
                case 0:
                    //Printing the main menu:
                    printOptions();
                    break;
                case 1:
                    //Adding and saving a new water meter reading:
                    addNewReading();
                    listIndexes.writeDataBase();
                    printOptions();
                    break;

                case 2:
                    //Printing the list of water meter readings:
                    listIndexes.printList();
                    break;

                case 3:
                    //Displaying the current water consumption:
                    System.out.println(listIndexes.waterConsumption());
                    break;

                case 4:
                    //Generating a flyer with the current water consumption:
                    System.out.println("Please enter the apartment number!");
                    Scanner apNumber = new Scanner(System.in);
                    while(!apNumber.hasNextInt()){
                        System.out.println("Please enter a valid integer number!");
                        apNumber.next();
                    }
                    int apartmentNumber = apNumber.nextInt();
                    listIndexes.writeFlyer(apartmentNumber);
                    printOptions();
                    break;

                case 5:
                    //Deleting the last element of the list of water meter readings:
                    // + saving the list and the checker.
                    listIndexes.deleteLastElement();
                    listIndexes.writeChecker();
                    listIndexes.writeDataBase();
                    break;

                case 6:
                    //Deleting all the records in the program and in the text file.
                    listIndexes.clearList();
                    listIndexes.writeDataBase();
                    break;

                case 7:
                    //Closing the app, after saving the list of water meter readings.
                    listIndexes.writeDataBase();
                    menu = true;
                    break;
                default:
                    //Printing the main menu:
                    System.out.println("Please select an available option!");
                    printOptions();
                    break;
            }
        }
    }

    // Method for printing the main menu:
    public static void printOptions() {
        System.out.println("Press:" +
                "\n\t 0 to show this list of options" +
                "\n\t 1 to enter a new water index reading" +
                "\n\t 2 to print the history of water index readings" +
                "\n\t 3 to see the current water consumption" +
                "\n\t 4 to export a ready to print flyer with the current consumption" +
                "\n\t 5 to delete the last entry in the history of water index readings" +
                "\n\t 6 to reset the list of water meter readings" +
                "\n\t 7 to quit the program");
    }

    //Testing the input in the main menu. Invalid input displays the main menu.
    public static int isInt(String input) {
        boolean isValid = true;
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                isValid = false;
                break;
            }
        }
        if (isValid) {
            return Integer.parseInt(input);
        }
        return 0;
    }

    //Method for adding a new water meter reading after it verifies the input from the user:
    public static void addNewReading() {
        String date;
        try {
            //Testing the date:
            System.out.println("Please enter the date of the reading in the format 'dd mm yyyy'");
            date = scanner.nextLine();

            if (!isIntOrSpace(date)) {
                System.out.println("Your input is incorrect!");
                addNewReading();
            } else {

                String[] dateArray = date.split(" ");
                String dayReading = dateArray[0];
                int day = Integer.parseInt(dayReading);
                String monthReading = dateArray[1];
                int month = Integer.parseInt(monthReading);
                String yearReading = dateArray[2];
                int year = Integer.parseInt(yearReading);

                LocalDate dateNow = LocalDate.of(year, month, day);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate dateOld = existingDate();
                if ((dateOld != null) && (dateNow.equals(dateOld) || dateNow.isBefore(dateOld))) {
                    System.out.println("Invalid date entered. Please enter a date which is after the last date saved in the water meter readings list - " + formatter.format(dateOld));
                    addNewReading();
                } else {

                    //Testing the input for cold water index:
                    System.out.println("Please enter the Cold water index: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Please enter a valid number for the cold water reading:");
                        scanner.next();
                    }
                    double cold = scanner.nextDouble();
                    if ((!listIndexes.getList().isEmpty()) && (cold <= listIndexes.getList().get(listIndexes.getList().size() - 1).getCold())) {
                        System.out.println("You cannot enter a lower cold water index than the last one recorded in the application." +
                                "\nEither check the accuracy of your cold water reading, or delete the last water index recorded.");
                        return;
                    }
                    scanner.nextLine();

                    //Testing the input for hot water index:
                    System.out.println("Please enter the Hot water index: ");
                    while (!scanner.hasNextDouble()) {
                        System.out.println("Please enter a valid number for the hot water reading:");
                        scanner.next();
                    }
                    double hot = scanner.nextDouble();
                    if ((!listIndexes.getList().isEmpty()) && (hot <= listIndexes.getList().get(listIndexes.getList().size() - 1).getHot())) {
                        System.out.println("You cannot enter a lower hot water index than the last one recorded in the application." +
                                "\nEither check the accuracy of your hot water reading, or delete the last water index recorded.");
                        return;
                    }
                    scanner.nextLine();

                    //Creating the water meter reading and adding to the list:
                    WaterIndex waterIndex = new WaterIndex(dateNow, cold, hot);
                    listIndexes.getList().add(waterIndex);

                    //Resetting the deletion checker:
                    listIndexes.setChecker(0);
                    listIndexes.writeChecker();
                    System.out.println("Reading successfully saved!");
                }
            }
        } catch (DateTimeException e) {
            addNewReading();
        }
    }

    //Tests the input for the date.
    public static boolean isIntOrSpace(String input) {
        int length = input.length();
        if (length < 8) {
            return false;
        } else if (length == 8) {
            return Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(2)) && Character.isDigit(input.charAt(4)) && Character.isDigit(input.charAt(5)) && Character.isDigit(input.charAt(6)) && Character.isDigit(input.charAt(7))
                    && Character.isWhitespace(input.charAt(1)) && Character.isWhitespace(input.charAt(3));
        } else if (length == 9) {
            return Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(3)) && Character.isDigit(input.charAt(5)) && Character.isDigit(input.charAt(6)) && Character.isDigit(input.charAt(7)) && Character.isDigit(input.charAt(8))
                    && Character.isWhitespace(input.charAt(2)) && Character.isWhitespace(input.charAt(4)) ||
                    (Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(2)) && Character.isDigit(input.charAt(3)) && Character.isDigit(input.charAt(5)) && Character.isDigit(input.charAt(6)) && Character.isDigit(input.charAt(7)) && Character.isDigit(input.charAt(8))
                            && Character.isWhitespace(input.charAt(1)) && Character.isWhitespace(input.charAt(4)));
        } else if (length == 10) {
            return Character.isDigit(input.charAt(0)) && Character.isDigit(input.charAt(1)) && Character.isDigit(input.charAt(3)) && Character.isDigit(input.charAt(4)) && Character.isDigit(input.charAt(6)) && Character.isDigit(input.charAt(7)) && Character.isDigit(input.charAt(8)) && Character.isDigit(input.charAt(9))
                    && Character.isWhitespace(input.charAt(2)) && Character.isWhitespace(input.charAt(5));
        }
        return false;
    }

    //Returns the date of the last registered reading.
    public static LocalDate existingDate() {
        if (listIndexes.getList().isEmpty()) {
            return null;
        } else {
            return listIndexes.getList().get(listIndexes.getList().size() - 1).getDate();
        }
    }

}


