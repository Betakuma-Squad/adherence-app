package com.squad.betakuma.adherence_app.data_model;

import java.util.ArrayList;
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


}
