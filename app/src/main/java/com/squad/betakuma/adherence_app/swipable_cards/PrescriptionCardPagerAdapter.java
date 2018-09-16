package com.squad.betakuma.adherence_app.swipable_cards;

import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squad.betakuma.adherence_app.R;
import com.squad.betakuma.adherence_app.data_model.DataListener;
import com.squad.betakuma.adherence_app.data_model.DataManager;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.utilities.Installation;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionCardPagerAdapter extends PagerAdapter implements PrescriptionCardAdapter, DataListener {

    private Context mContext;
    private ViewPager mParentViewPager;
    private List<CardView> mViews;
    private List<PrescriptionCardItem> mData;
    private float mBaseElevation;
    private DataManager manager;
    private CardTapListener mCardTapListener;

    public PrescriptionCardPagerAdapter(Context context, ViewPager viewPager, CardTapListener cardTapListener) {
        mContext = context;
        mCardTapListener = cardTapListener;
        mParentViewPager = viewPager;
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        manager = DataManager.getInstance(Installation.id(context));
        manager.registerListener(this);
        for (Prescription prescription : manager.getDataset()) {
            addPrescriptionCardItem(prescription);
        }
    }

    public void addCardItem(PrescriptionCardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public void addPrescriptionCardItem(Prescription prescription) {
        mViews.add(null);
        mData.add(new PrescriptionCardItem(prescription));
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

    @Override
    public void onDataUpdate() {

    }

    private void bind(final PrescriptionCardItem item, final View parentView) {
        ImageView image = parentView.findViewById(R.id.medication_image);
        TextView titleText = parentView.findViewById(R.id.medication_title);
        TextView subtitleText = parentView.findViewById(R.id.medication_subtitle);
        TextView collapsedText = parentView.findViewById(R.id.collapsed_content);
        LinearLayout expandableContent = parentView.findViewById(R.id.card_expanded_content);
        TextView expandedFullText = parentView.findViewById(R.id.expanded_full_text);
        CardView cardView = parentView.findViewById(R.id.cardView);
        item.setCollapsedContent(collapsedText);
        item.setExpandableContent(expandableContent);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition((ViewGroup) parentView);
                item.toggleExpanded();
                mCardTapListener.onCardTap();
                // TODO: make this less hacky
                if (parentView.getPaddingLeft() > 0) {
                    parentView.setPadding(-30, 0, -30, 0);

                } else {
//                    parentView.setPadding(0, 0, 0, 0);
//                    parentView.setPadding(mContext.getResources().getDimensionPixelSize(R.dimen.card_padding), 0, mContext.getResources().getDimensionPixelSize(R.dimen.card_padding), 0);
                }
            }
        });


        final ImageView notificationImage = parentView.findViewById(R.id.notification_image);
        if (item.shouldSendNotifications()) {
            notificationImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_selected));
        } else {
            notificationImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.inactive_icon));
        }
        notificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.shouldSendNotifications()) {
                    item.setShouldSendNotifications(false);
                    notificationImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.inactive_icon));
                } else {
                    item.setShouldSendNotifications(true);
                    notificationImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.notification_selected));
                }
            }
        });
        titleText.setText(item.getTitle());
        subtitleText.setText(item.getSubtitle());
        collapsedText.setText(item.getText().replace("\\n", "\n"));
        expandedFullText.setText(item.getText().replace("\\n", "\n"));

        image.setImageResource(
                mContext.getResources().getIdentifier(mContext.getPackageName() + ":drawable/" + item.getImageFileName(),
                        null,
                        null));
    }

}