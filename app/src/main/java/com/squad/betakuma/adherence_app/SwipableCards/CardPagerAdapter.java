package com.squad.betakuma.adherence_app.SwipableCards;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squad.betakuma.adherence_app.R;
import com.squad.betakuma.adherence_app.data_model.DataListener;
import com.squad.betakuma.adherence_app.data_model.MedicationManager;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.utilities.Installation;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter, DataListener {

    private Context mContext;
    private ViewPager mParentViewPager;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private MedicationManager manager;

    public CardPagerAdapter(Context context, ViewPager viewPager) {
        mContext = context;
        mParentViewPager = viewPager;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        manager = MedicationManager.getInstance(Installation.id(context));
        manager.registerListener(this);
//        for (Prescription prescription : manager.getDataset()) {
//            addPrescriptionCardItem(prescription);
//        }
        addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        addCardItem(new CardItem(R.string.title_4, R.string.text_1));
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

//    public void addPrescriptionCardItem(Prescription prescription) {
//        mViews.add(null);
//        mData.add(...)
//    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.cardview_medication, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    @Override
    public void onDataUpdate() {

    }

    private void bind(final CardItem item, View view) {
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView contentTextView = (TextView) view.findViewById(R.id.contentTextView);
        LinearLayout expandableContent = view.findViewById(R.id.card_expanded_content);
        item.setExpandableContent(expandableContent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition((ViewGroup) view);
                item.toggleExpanded();
                // TODO: make this less hacky
                if (view.getPaddingLeft() > 0) {
                    view.setPadding(-30, 0, -30, 0);

                } else {
                    view.setPadding(mContext.getResources().getDimensionPixelSize(R.dimen.card_padding), mContext.getResources().getDimensionPixelSize(R.dimen.card_padding), mContext.getResources().getDimensionPixelSize(R.dimen.card_padding), mContext.getResources().getDimensionPixelSize(R.dimen.card_padding));
                }
            }
        });
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getText());
    }

}