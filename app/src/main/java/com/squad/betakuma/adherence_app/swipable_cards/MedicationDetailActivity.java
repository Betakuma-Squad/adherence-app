package com.squad.betakuma.adherence_app.swipable_cards;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.squad.betakuma.adherence_app.R;
import com.squad.betakuma.adherence_app.ShadowTransformer;
import com.squad.betakuma.adherence_app.survey.SurveyView;

/**
 * Created by sherryuan on 2018-09-14.
 */

public class MedicationDetailActivity extends AppCompatActivity implements  CardTapListener {
    public static final String POSITION = "position";

    private ViewPager mViewPager;
    private SurveyView mSurveyView;
    private PrescriptionCardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private boolean isCardExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);

        mViewPager = findViewById(R.id.viewPager);
        mSurveyView = findViewById(R.id.survey_view);
        mViewPager.setPageMargin(-30);

        mCardAdapter = new PrescriptionCardPagerAdapter(this, mViewPager, this);

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        int position = getIntent().getIntExtra(POSITION, 0);
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onCardTap() {
        isCardExpanded = !isCardExpanded;
        mSurveyView.setVisible(!isCardExpanded);
    }
}
