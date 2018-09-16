package com.squad.betakuma.adherence_app.data_model;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squad.betakuma.adherence_app.survey.SurveyResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

import static android.support.constraint.Constraints.TAG;

public class DataManager {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final ArrayList<Prescription> prescriptions = new ArrayList<>();
    private final ArrayList<DataListener> listeners = new ArrayList<>();
    private String userId = "";

    private static DataManager instance = null;

    private DataManager(@NonNull final String id) {
        Log.d("DEBUG", "THIS IS THE ID: " + id);
        userId = id;
        reloadData();
    }

    public static DataManager getInstance(@NonNull final String id) {
        if (instance == null) {
            instance = new DataManager(id);
        }
        return instance;
    }

    // get the backing array of prescription data
    public Prescription[] getDataset() {
        Prescription[] prescriptionsArray = {};
        return prescriptions.toArray(prescriptionsArray);
    }


    public void registerListener(DataListener listener) {
        listeners.add(listener);
    }

    public void deregisterListener(DataListener listener) {
        listeners.remove(listener);
    }

    public void addPrescription(Prescription prescription) {
        if (!prescriptions.contains(prescription)) {
            prescriptions.add(prescription);
        }
        List<Map<String, Object>> prescriptionsMap = new ArrayList<>();
        for (Prescription next: prescriptions) {
            prescriptionsMap.add(next.toMap());
        }
        db.collection("users").document(userId).update("prescriptions",  prescriptionsMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Successfully updated firebase");
                        for (DataListener next: listeners) {
                            next.onDataUpdate();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error writing document", e);
                    }
                });

    }

    // reload data from Firebase
    public void reloadData() {
        prescriptions.clear();
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Log.d("DEBUG", doc.getData().toString());
                        ArrayList prescriptionsFirebase = (ArrayList) doc.getData().get("prescriptions");
                        for (Object o : prescriptionsFirebase) {
                            Log.d("DEBUG", o.toString());
                            // TODO FIX THIS
                            prescriptionFromFirebase((Map) o);
                        }
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
    private void prescriptionFromFirebase(@NonNull final Map firebaseObject) {
        final long quantity = (long) firebaseObject.get("quantity");
        final long totalQuantity = (long) firebaseObject.get("totalQuantity");
        final long refills = (long) firebaseObject.get("refills");
        final long totalRefills = (long) firebaseObject.get("totalRefills");
        @NonNull final String instructions = (String) firebaseObject.get("instructions");
        @NonNull final ArrayList surveyResponsesFirebase = (ArrayList) firebaseObject.get("surveyResponses");
        final ArrayList<SurveyResponse> surveyResponses = new ArrayList<>();
        for (Object o : surveyResponsesFirebase) {
            final Map responseFirebase = (Map) o;
            final String question = (String) responseFirebase.get("question");
            final String response = (String) responseFirebase.get("response");
            surveyResponses.add(new SurveyResponse(question, response));
        }
        final String din = (String) firebaseObject.get("medication");
        DocumentReference docRef = db.collection("medications").document(din);
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
                        for (DataListener listener : listeners) {
                            listener.onDataUpdate();
                        }
                        Log.d("DEBUG", prescriptions.toString());
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
    private static Medication medicationFromFirebase(final String DIN,
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
