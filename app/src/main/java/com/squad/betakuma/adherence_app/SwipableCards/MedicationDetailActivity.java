package com.squad.betakuma.adherence_app.SwipableCards;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.squad.betakuma.adherence_app.R;
import com.squad.betakuma.adherence_app.ShadowTransformer;

/**
 * Created by sherryuan on 2018-09-14.
 */

public class MedicationDetailActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setPageMargin(-30);

        mCardAdapter = new CardPagerAdapter(this, mViewPager);

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
}
