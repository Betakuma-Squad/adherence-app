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

import java.util.ArrayList;
import java.util.List;

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private Context mContext;
    private ViewPager mParentViewPager;
    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardPagerAdapter(Context context, ViewPager viewPager) {
        mContext = context;
        mParentViewPager = viewPager;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

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