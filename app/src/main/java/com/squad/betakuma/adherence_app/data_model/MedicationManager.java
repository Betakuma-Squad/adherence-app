package com.squad.betakuma.adherence_app.data_model;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.NonNull;

public class MedicationManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Prescription> prescriptions = new ArrayList<>();

    public MedicationManager(@NonNull final String id) {
        reloadData(id);
    }

    // get the backing array of prescription data
    public Prescription[] getDataset() {
        Prescription[] prescriptionsArray = {};
        return prescriptions.toArray(prescriptionsArray);
    }

    // reload data from Firebase
    public void reloadData(@NonNull final String id) {
        prescriptions.clear();
        DocumentReference docRef = db.collection("users").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        prescriptionFromFirebase(doc.getData());
                    } else {
                        // TODO error handling
                    }
                } else {
                    // TODO error handling
                }
            }
        });
    }

    // asynchronously convert from Firebase results to Prescription POJO
    // NOTE: this is asynchronous because it has to look up the attached medicine in Firebase
    private void prescriptionFromFirebase(@NonNull final Map<String, Object> firebaseObject) {
        final int quantity = (int) firebaseObject.get("quantity");
        final int totalQuantity = (int) firebaseObject.get("totalQuantity");
        final int refills = (int) firebaseObject.get("refills");
        final int totalRefills = (int) firebaseObject.get("totalRefills");
        @NonNull final String instructions = (String) firebaseObject.get("instructions");
        @NonNull final ArrayList surveyResponsesFirebase = (ArrayList) firebaseObject.get("surveyResponses");
        final ArrayList<SurveyResponse> surveyResponses = new ArrayList<>();
        for (Object o : surveyResponsesFirebase) {
            final Map responseFirebase = (Map) o;
            final String question = (String) responseFirebase.get("question");
            final String response = (String) responseFirebase.get("response");
            surveyResponses.add(new SurveyResponse(question, response));
        }
        final int din = (int) firebaseObject.get("medication");
        DocumentReference docRef = db.collection("medications").document(Integer.toString(din));
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        prescriptions.add(new Prescription(quantity,
                                totalQuantity,
                                refills,
                                totalRefills,
                                medicationFromFirebase(din,
                                        doc.getData()),
                                instructions,
                                surveyResponses));
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

    // convert from Firebase object to Medication POJO
    private static Medication medicationFromFirebase(final int DIN,
                                                     @NonNull final Map<String, Object> firebaseObject) {
        // TODO error handling
        @NonNull final String genericName = (String) firebaseObject.get("genericName");
        @NonNull final String brandName = (String) firebaseObject.get("brandName");
        @NonNull final String dose = (String) firebaseObject.get("dose");
        @NonNull final String description = (String) firebaseObject.get("description");
        @NonNull final Map sideEffectsFirebase = (Map) firebaseObject.get("sideEffects");
        final Map<SideEffectRarity, ArrayList<String>> sideEffects = new HashMap<>();
        if (sideEffectsFirebase.get("rare") != null) {
            final ArrayList effectsFirebase = (ArrayList) sideEffectsFirebase.get("rare");
            final ArrayList<String> effects = new ArrayList<>();
            for (Object o : effectsFirebase) {
                effects.add((String) o);
            }
            sideEffects.put(SideEffectRarity.Rare, effects);
        }
        if (sideEffectsFirebase.get("lessCommon") != null) {
            final ArrayList effectsFirebase = (ArrayList) sideEffectsFirebase.get("lessCommon");
            final ArrayList<String> effects = new ArrayList<>();
            for (Object o : effectsFirebase) {
                effects.add((String) o);
            }
            sideEffects.put(SideEffectRarity.LessCommon, effects);
        }
        if (sideEffectsFirebase.get("moreCommon") != null) {
            final ArrayList effectsFirebase = (ArrayList) sideEffectsFirebase.get("moreCommon");
            final ArrayList<String> effects = new ArrayList<>();
            for (Object o : effectsFirebase) {
                effects.add((String) o);
            }
            sideEffects.put(SideEffectRarity.MoreCommon, effects);
        }

        return new Medication(DIN,
                genericName,
                brandName,
                dose,
                description,
                sideEffects);
    }
}
