package com.squad.betakuma.adherence_app.swipable_cards;


import android.support.v7.widget.CardView;

public interface PrescriptionCardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}