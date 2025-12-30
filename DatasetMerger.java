   package com.fakenews;

import java.io.*;

public class DatasetMerger {

    public static void main(String[] args) throws Exception {

        BufferedWriter writer = new BufferedWriter(
                new FileWriter("dataset/fakenews.csv"));
        writer.write("text,label\n");

        // Read Fake.csv
        BufferedReader fakeReader =
                new BufferedReader(new FileReader("dataset/Fake.csv"));
        fakeReader.readLine(); // skip header
        String line;

        while ((line = fakeReader.readLine()) != null) {
            String[] parts = line.split(",", 3);
            String text = parts.length > 1 ? parts[1] : parts[0];
            writer.write("\"" + text.replace("\"", "") + "\",fake\n");
        }
        fakeReader.close();

        // Read True.csv
        BufferedReader trueReader =
                new BufferedReader(new FileReader("dataset/True.csv"));
        trueReader.readLine(); // skip header

        while ((line = trueReader.readLine()) != null) {
            String[] parts = line.split(",", 3);
            String text = parts.length > 1 ? parts[1] : parts[0];
            writer.write("\"" + text.replace("\"", "") + "\",real\n");
        }
        trueReader.close();

        writer.close();
        System.out.println("Merged dataset created at dataset/fakenews.csv");
    }
}

