package com.squad.betakuma.adherence_app.DataModel;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single prescription
public class Prescription {
    @NonNull final String DIN;
    @NonNull final String quantity;
    @NonNull final String refills;
    @NonNull final String instructions;
    @NonNull final SurveyResponse[] surveyResponses;
}
