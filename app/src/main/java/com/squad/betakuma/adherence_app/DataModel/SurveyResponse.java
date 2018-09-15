package com.squad.betakuma.adherence_app.DataModel;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single survey response
public class SurveyResponse {
    @NonNull final String question;
    @NonNull final String response;
}
