package com.fakenews;

import weka.classifiers.Classifier;
import weka.core.*;
import java.io.File;
import java.util.ArrayList;

public class FakeNewsClassifier {

    private Classifier model;
    private Instances structure;

    public FakeNewsClassifier() throws Exception {

        // Load trained model
        model = (Classifier) weka.core.SerializationHelper
                .read(new File("model/fakeNews.model").getPath());

        // Define attributes
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("text", (ArrayList<String>) null));

        ArrayList<String> classValues = new ArrayList<>();
        classValues.add("fake");
        classValues.add("real");
        attributes.add(new Attribute("label", classValues));

        structure = new Instances("TestNews", attributes, 0);
        structure.setClassIndex(1);
    }

    public String predict(String newsText) throws Exception {

        // Length check
        int wordCount = newsText.trim().split("\\s+").length;
        if (wordCount < 15) {
            return "UNCERTAIN (Insufficient context)";
        }

        // Rule-based REAL detection (Hybrid logic)
        String lower = newsText.toLowerCase();
        if (lower.contains("official statement")
                || lower.contains("press release")
                || lower.contains("government announced")
                || lower.contains("according to official sources")
                || lower.contains("ministry of")) {

            return "REAL (rule-based confidence)";
        }

        // ML-based prediction
        DenseInstance instance = new DenseInstance(2);
        instance.setValue(structure.attribute(0), newsText);
        instance.setDataset(structure);

        double[] probs = model.distributionForInstance(instance);
        int index = (int) model.classifyInstance(instance);

        String label = structure.classAttribute().value(index);
        double confidence = probs[index] * 100;

        return label.toUpperCase() + " (" 
                + String.format("%.2f", confidence) 
                + "% ML confidence)";
    }
}

