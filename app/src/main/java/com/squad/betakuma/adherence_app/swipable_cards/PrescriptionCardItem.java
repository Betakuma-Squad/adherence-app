package com.squad.betakuma.adherence_app.swipable_cards;


import android.view.View;
import android.widget.LinearLayout;

import com.squad.betakuma.adherence_app.data_model.Medication;
import com.squad.betakuma.adherence_app.data_model.Prescription;

import lombok.NonNull;

public class PrescriptionCardItem {

    private boolean isExpanded = false;
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

    public void setExpandableContent(LinearLayout expandableContent) {
        this.expandableContent = expandableContent;
    }

    public String getTitle() {
        return prescription.getMedication().getGenericName();
    }

    public String getSubtitle() {
        Medication m = prescription.getMedication();
        return m.getBrandName() + ", " + m.getDIN();
    }

    public String getText() {
        return "";
        //return prescription.getMedication().getDescription();
    }

    public void toggleExpanded() {
        if (isExpanded) {
            expandableContent.setVisibility(View.GONE);
        } else {
            expandableContent.setVisibility(View.VISIBLE);
        }
        isExpanded = !isExpanded;
    }
}