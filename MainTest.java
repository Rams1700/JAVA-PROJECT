package com.fakenews;

import java.util.Scanner;

public class MainTest {

    public static void main(String[] args) throws Exception {

        FakeNewsClassifier classifier = new FakeNewsClassifier();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter news text:");
        String text = sc.nextLine();

        String result = classifier.predict(text);
        System.out.println("Prediction: " + result);

        sc.close();
    }
}
