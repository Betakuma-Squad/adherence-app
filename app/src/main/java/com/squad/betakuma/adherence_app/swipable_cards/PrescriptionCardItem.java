package com.squad.betakuma.adherence_app.swipable_cards;


import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squad.betakuma.adherence_app.data_model.Medication;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.data_model.SideEffectRarity;

import java.util.ArrayList;
import java.util.Map;

import lombok.NonNull;

public class PrescriptionCardItem {

    private boolean isExpanded = false;
    private boolean shouldSendNotifications = false;
    private TextView collapsedContent;
    private LinearLayout expandableContent;
    @NonNull final private Prescription prescription;

    public PrescriptionCardItem(Prescription p) {
        prescription = p;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public void setCollapsedContent(TextView collapsedContent) {
        this.collapsedContent = collapsedContent;
    }

    public boolean shouldSendNotifications() {
        return shouldSendNotifications;
    }

    public void setShouldSendNotifications(boolean shouldSendNotifications) {
        this.shouldSendNotifications = shouldSendNotifications;
    }

    public void setExpandableContent(LinearLayout expandableContent) {
        this.expandableContent = expandableContent;
    }

    public String getTitle() {
        return prescription.getMedication().getGenericName();
    }

    public String getDosage() {
        return prescription.getMedication().getDose();
    }

    public String getSubtitle() {
        Medication m = prescription.getMedication();
        return m.getBrandName() + ", " + m.getDIN();
    }

    public String getText() {
        return prescription.getMedication().getDescription();
    }

    public String getSideEffects() {
        String result = "";
        Map<SideEffectRarity, ArrayList<String>> map = prescription.getMedication().getSideEffects();
        ArrayList<String> sideEffects;

        for (Map.Entry<SideEffectRarity, ArrayList<String>> se : map.entrySet()) {
            result += se.getKey();
            result += "\n";

            sideEffects = se.getValue();

            for (int i = 0; i < sideEffects.size(); i++) {
                result += "- ";
                result += sideEffects.get(i);
                result += "\n";
            }

            result += "\n";
        }

        return result;
    }

    public String getImageFileName() {
        return "m" + prescription.getMedication().getDIN();
    }

    public void toggleExpanded() {
        if (isExpanded) {
            collapsedContent.setVisibility(View.VISIBLE);
            expandableContent.setVisibility(View.GONE);
        } else {
            collapsedContent.setVisibility(View.GONE);
            expandableContent.setVisibility(View.VISIBLE);
        }
        isExpanded = !isExpanded;
    }
}