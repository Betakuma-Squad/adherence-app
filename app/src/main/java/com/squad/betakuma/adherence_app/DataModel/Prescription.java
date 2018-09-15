package com.squad.betakuma.adherence_app.DataModel;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single prescription
public class Prescription {
    final int quantity;
    final int totalQuantity;
    final int refills;
    final int totalRefills;
    @NonNull final Medication medication;
    @NonNull final String instructions;
    @NonNull final List<SurveyResponse> surveyResponses;
}
