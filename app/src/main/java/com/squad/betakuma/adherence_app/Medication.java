package com.squad.betakuma.adherence_app;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single medication
public class Medication {
    @NonNull final String genericName;
    @NonNull final String brandName;
    @NonNull final String DIN;
    @NonNull final String dose;
    @NonNull final String instructions;
    @NonNull final String description;
    @NonNull final Map<SideEffectRarity, String[]> sideEffects;
}
