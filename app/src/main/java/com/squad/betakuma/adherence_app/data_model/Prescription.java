package com.squad.betakuma.adherence_app.data_model;

import com.squad.betakuma.adherence_app.survey.SurveyResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
// data model representation of a single prescription
public class Prescription {
    final boolean shouldSendNotifications;
    final long quantity;
    final long totalQuantity;
    final long refills;
    final long totalRefills;
    @NonNull final Medication medication;
    @NonNull final String instructions;
    @NonNull final List<SurveyResponse> surveyResponses;

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("shouldSendNotifications", shouldSendNotifications);
        map.put("instructions", instructions);
        map.put("medication", medication.DIN);
        map.put("quantity", quantity);
        map.put("refills", refills);
        map.put("totalQuantity", totalQuantity);
        map.put("totalRefills", totalRefills);
        // TODO: serialize responses and pass them in
        map.put("surveyResponses", new ArrayList<>());
        return map;
    }
}
