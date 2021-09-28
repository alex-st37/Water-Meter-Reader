package com.andustoica;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WaterIndexData {

    private List<WaterIndex> list;
    private int checker;

    public WaterIndexData() {
        this.list = new ArrayList<WaterIndex>();
        checker = 0;
    }

    public List<WaterIndex> getList() {
        return list;
    }

    public void setList(List<WaterIndex> list) {
        this.list = list;
    }

    public int getChecker() {
        return checker;
    }

    public void setChecker(int checker) {
        this.checker = checker;
    }

    // Method for printing the list of water meter records:
    public void printList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (list.isEmpty()) {
            System.out.println("You haven't entered yet a water reading.");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". Date Of The Reading: " + list.get(i).getDate().format(formatter) +
                        ", Cold Water Index: " + list.get(i).getCold() + " m\u00B3" +
                        ", Hot Water Index: " + list.get(i).getHot() + " m\u00B3");
            }
        }
    }

    // Method to determine the current water consumption:
    public String waterConsumption() {
        if (list.size() < 2) {
            return ("To determine your current water consumption you need to enter at least two water meter reading.");
        } else {
            double oldColdWater = list.get(list.size() - 2).getCold();
            double newColdWater = list.get(list.size() - 1).getCold();
            double ColdWaterConsumption = newColdWater - oldColdWater;

            double oldHotWater = list.get(list.size() - 2).getHot();
            double newHotWater = list.get(list.size() - 1).getHot();
            double HotWaterConsumption = newHotWater - oldHotWater;

            return ("Current water consumption is: " + String.format("%.2f", ColdWaterConsumption) + " m\u00B3 cold water  " +
                    String.format("%.2f", HotWaterConsumption) + " m\u00B3 hot water.");
        }
    }

    // Method to write a text file containing the current water consumption:
    public void writeFlyer(int apartmentNumber) throws IOException {
        if (list.size() < 2) {
            System.out.println("Can't generate the flyer because you need to enter at least two water meter readings.");
        } else {
            System.out.println("Flyer successfully generated!");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            try (FileWriter flyer = new FileWriter("Water Consumption at " + LocalDate.now().format(formatter) + ".txt")) {
                flyer.write("\n\n\t Water consumption for apartment no. " + apartmentNumber + " at " + LocalDate.now().format(formatter));
                flyer.write("\n\n\nWater reading for last month (" + list.get(list.size() - 2).formatterToString() + ")" + "\n\t Cold water: " + list.get(list.size() - 2).getCold() + " m\u00B3,   Hot water: " + list.get(list.size() - 2).getHot() + " m\u00B3.");
                flyer.write("\n\nWater reading for the current month (" + list.get(list.size() - 1).formatterToString() + ")" + "\n\t Cold water: " + list.get(list.size() - 1).getCold() + " m\u00B3,   Hot water: " + list.get(list.size() - 1).getHot() + " m\u00B3.");
                flyer.write("\n\n" + waterConsumption());
            }
        }
    }

    // Method to delete the last entry from the list of water meter readings:
    public void deleteLastElement() {
        if (!list.isEmpty()) {
            if (this.checker == 0) {
                System.out.println("The last entry has been deleted (" + list.get(list.size() - 1).toString() + ")");
                this.list.remove(list.size() - 1);
                this.checker = 1;
            } else {
                System.out.println("The last entry has already been deleted");
            }
        } else {
            System.out.println("The list of water meter indexes is empty. It isn't possible to delete a last entry.");
        }
    }

    // Method to delete all the records from the water meter readings list:
    public void clearList() {
        this.list.clear();
        System.out.println("The list of water meter readings has been successfully cleared!");
    }

    // Method to write the value of the deletion checker on a text file:
    public void writeChecker() {
        try (FileWriter file = new FileWriter("DeletionChecker.txt")) {
            file.write(Integer.toString(checker));
        }catch (IOException e){
            System.out.println("Writing of DeletionChecker.txt has failed!");
        }
    }

    // Method to read the value of the deletion checker from the text file:
    public void readChecker() throws IOException {
        try (Scanner scanner = new Scanner(new FileReader("DeletionChecker.txt"))) {
            this.checker = scanner.nextInt();
        }
    }

    // Method to store the list of water meter readings in a text file:
    public void writeDataBase() throws IOException {
        try (FileWriter doc = new FileWriter("DataBase.txt")) {
            for (int i = 0; i < list.size(); i++) {
                doc.write((i + 1) + ", " + list.get(i).formatterToString() + ", " + list.get(i).getCold() + ", " + list.get(i).getHot() + "\n");
            }
        }
    }

    // Method to read the saved water meter records from the text file:
    public void readData() throws IOException {
        try (Scanner scanner = new Scanner(new FileReader("DataBase.txt"))) {
            scanner.useDelimiter(", ");
            int e = 0;
            while (scanner.hasNextLine()) {
                int counter = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String dateString = scanner.next();
                scanner.skip(scanner.delimiter());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(dateString, formatter);
                double cold = scanner.nextDouble();
                scanner.skip(scanner.delimiter());
                String hotString = scanner.nextLine();
                double hot = Double.parseDouble(hotString);
                WaterIndex temp = new WaterIndex(date, cold, hot);
                list.add(e, temp);
                e++;
            }
        }
    }
}
