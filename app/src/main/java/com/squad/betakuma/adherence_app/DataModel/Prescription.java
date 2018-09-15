package com.squad.betakuma.adherence_app.DataModel;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single prescription
public class Prescription {
    @NonNull final Medication medication;
    @NonNull final String quantity;
    @NonNull final String refills;
    @NonNull final String instructions;
    @NonNull final List<SurveyResponse> surveyResponses;
}
