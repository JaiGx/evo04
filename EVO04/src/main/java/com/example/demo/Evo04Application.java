package com.example.demo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class Evo04Application {

	 public static void main(String[] args) {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/testbackend-daefb-firebase-adminsdk-ursuq-d36c02da04.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);

            System.out.println("Conectado a Firebase exitosamente");

            // Acceder a Firestore
            Firestore firestore = FirestoreClient.getFirestore();

            // Obtener una colección
            String collectionName = "Producto";
            ApiFuture<QuerySnapshot> query = firestore.collection(collectionName).get();

            // Esperar a que la consulta se complete
            QuerySnapshot querySnapshot = query.get();

            // Iterar sobre los documentos en la colección
            for (QueryDocumentSnapshot document : querySnapshot) {
                System.out.println("ID del Documento: " + document.getId());
                System.out.println("Datos del Documento: " + document.getData());
            }

            SpringApplication.run(Evo04Application.class, args);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}