package com.squad.betakuma.adherence_app.data_model;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single survey response
public class SurveyResponse {
    @NonNull final String question;
    @NonNull final String response;
}
