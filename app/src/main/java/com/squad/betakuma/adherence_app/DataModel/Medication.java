package com.squad.betakuma.adherence_app.DataModel;

import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single medication
public class Medication {
    @NonNull final public String genericName;
    @NonNull final public String brandName;
    @NonNull final public String DIN;
    @NonNull final public String dose;
    @NonNull final public String description;
    @NonNull final public Map<SideEffectRarity, String[]> sideEffects;
}
