package com.squad.betakuma.adherence_app.survey;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.squad.betakuma.adherence_app.R;

import static com.squad.betakuma.adherence_app.swipable_cards.PrescriptionCardAdapter.MAX_ELEVATION_FACTOR;

/**
 * Created by sherryuan on 2018-09-15.
 */

public class SurveyView extends LinearLayout {
    private LinearLayout surveyLayout;
    public SurveyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_survey, this, true);
        surveyLayout = findViewById(R.id.survey_view);
    }

    public void setVisible(boolean isVisible) {
        if (isVisible) {
            surveyLayout.setVisibility(VISIBLE);
        } else {
            surveyLayout.setVisibility(INVISIBLE);
        }
    }
}
