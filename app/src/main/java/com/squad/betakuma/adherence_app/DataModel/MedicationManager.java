package com.squad.betakuma.adherence_app.DataModel;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MedicationManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Prescription> prescriptions;

    public MedicationManager() {
        prescriptions = new ArrayList<>();
        DocumentReference docRef = db.collection("medications").document(Integer.toString(2247918));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        prescriptions.add(new Prescription(0,
                                0,
                                0,
                                0,
                                Medication.convertFromFirebase(2247918,
                                        doc.getData()),
                                "Take one capsule once daily",
                                new ArrayList<SurveyResponse>()));
                        Log.d("debug", doc.getData().toString());
                    } else {
                        // TODO error handling
                    }
                } else {
                    // TODO error handling
                }
            }
        });
    }

    // get the backing array of prescription data
    public Prescription[] getDataset() {
        Prescription[] prescriptionsArray = {};
        return prescriptions.toArray(prescriptionsArray);
    }

    // reload data from Firebase
    public void reloadData() {

    }
}
