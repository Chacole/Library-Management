package com.example.tuanq.ExampleValues;

import com.example.tuanq.Documents;
import com.example.tuanq.ManagementID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DocumentFactory {

    public static ObservableList<Documents> createDocuments() {
        ObservableList<Documents> documents = FXCollections.observableArrayList(
                new Documents("Micheal", "World Class", "football", 2018, 1, "/com/example/tuanq/images/cr7.jpeg"),
                new Documents("Ben B", "GOAT", "football", 2007, 1, "/com/example/tuanq/images/Cristiano.jpg"),
                new Documents("Peter", "Sleepy", "comedy", 2015, 3, "/com/example/tuanq/images/Night.jpg"),
                new Documents("Tuan Tran", "Great of all time", "football", 2024, 2, "/com/example/tuanq/images/ronaldo.jpg"),
                new Documents("Tuan", "M10 & CR7", "football", 2023, 0, "/com/example/tuanq/images/m10 and cr7.png"),
                new Documents("Sophia Rain", "Language of love", "theory", 2017, 4, "/com/example/tuanq/images/IMG_6193.JPG")
        );
        ManagementID idManager = new ManagementID();
        documents.forEach(doc -> doc.assignID(idManager));

        return documents;
    }
}