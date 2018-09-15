package com.squad.betakuma.adherence_app.DataModel;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single medication
public class Medication {
    final public int DIN;
    @NonNull final String genericName;
    @NonNull final String brandName;
    @NonNull final String dose;
    @NonNull final String description;
    @NonNull final Map<SideEffectRarity, ArrayList<String>> sideEffects;

    public static Medication convertFromFirebase(final int DIN,
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
