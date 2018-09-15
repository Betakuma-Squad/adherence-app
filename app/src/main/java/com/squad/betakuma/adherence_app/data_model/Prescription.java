package com.squad.betakuma.adherence_app.data_model;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single prescription
public class Prescription {
    final long quantity;
    final long totalQuantity;
    final long refills;
    final long totalRefills;
    @NonNull final Medication medication;
    @NonNull final String instructions;
    @NonNull final List<SurveyResponse> surveyResponses;
}
