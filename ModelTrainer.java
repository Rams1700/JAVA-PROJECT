package com.fakenews;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.io.File;

public class ModelTrainer {

    public static void main(String[] args) throws Exception {

        // Load merged CSV dataset
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("dataset/fakenews.csv"));
        Instances data = loader.getDataSet();

        // Set the label (class) column
        data.setClassIndex(data.numAttributes() - 1);

        // Convert text into numeric features
        StringToWordVector filter = new StringToWordVector();
        filter.setIDFTransform(true);
        filter.setTFTransform(true);
        filter.setLowerCaseTokens(true);

        // Naive Bayes classifier with text filter
        FilteredClassifier classifier = new FilteredClassifier();
        classifier.setFilter(filter);
        classifier.setClassifier(new NaiveBayes());

        System.out.println("Training model, please wait...");

        // Train model
        classifier.buildClassifier(data);

        // Save trained model
        weka.core.SerializationHelper.write("model/fakeNews.model", classifier);

        System.out.println("Model training complete!");
        System.out.println("Saved trained model to: model/fakeNews.model");
    }
}
