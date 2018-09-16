package com.squad.betakuma.adherence_app.swipable_cards;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.transition.TransitionManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squad.betakuma.adherence_app.R;
import com.squad.betakuma.adherence_app.data_model.DataListener;
import com.squad.betakuma.adherence_app.data_model.DataManager;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.notifications.AlarmReceiver;
import com.squad.betakuma.adherence_app.notifications.NotificationScheduler;
import com.squad.betakuma.adherence_app.utilities.Installation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrescriptionCardPagerAdapter extends PagerAdapter implements PrescriptionCardAdapter, DataListener {

    private Context mContext;
    private ViewPager mParentViewPager;
    private List<CardView> mViews;
    private List<PrescriptionCardItem> mData;
    private float mBaseElevation;
    private DataManager manager;
    private CardTapListener mCardTapListener;
    private boolean isEnglish = true;

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
        final ScrollView scrollView = cardView.getRootView().findViewById(R.id.scrollView);
//        scrollView.setY(300);

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
        final LinearLayout expanded = parentView.getRootView().findViewById(R.id.card_expanded_content);
        final ScrollView scrollView = parentView.getRootView().findViewById(R.id.scrollView);
//        scrollView.setY(300);

        ImageView image = parentView.findViewById(R.id.medication_image);
        final TextView titleText = parentView.findViewById(R.id.medication_title);
        TextView subtitleText = parentView.findViewById(R.id.medication_subtitle);
        final TextView collapsedText = parentView.findViewById(R.id.collapsed_content);

        final LinearLayout expandableContent = parentView.findViewById(R.id.card_expanded_content);
        final TextView expandedFullText = parentView.findViewById(R.id.expanded_full_text);
        CardView cardView = parentView.findViewById(R.id.cardView);
        ImageView translateButton = parentView.findViewById(R.id.translate_button);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getTitle().equals("Ramipril") && isEnglish) {
                    isEnglish = false;
                    final String ramiprilChinese =
                            "雷米普利单独使用或与其他药物一起用于治疗高血压（高血压）。高血压增加了心脏和动脉的工作量。如果持续很长时间，心脏和动脉可能无法正常运作。这会损害大脑，心脏和肾脏的血管，导致中风，心力衰竭或肾衰竭。降低血压可以降低中风和心脏病发作的风险。\n\n"
                                    + "雷米普利是一种血管紧张素转换酶（ACE）抑制剂。它的作用是阻断体内导致血管收紧的物质。结果，雷米普利使血管松弛。这降低了血压并增加了心脏的血液和氧气供应。\n\n"
                                    + "雷米普利也用于心脏病发作后的一些患者。心脏病发作后，一些心肌受损并减弱。随着时间的推移，心肌可能继续减弱。这使得心脏泵血更加困难。雷米普利可在心脏病发作后的最初几天内开始，以提高存活率。\n\n"
                                    + "雷米普利还用于减轻55岁或以上患者发生心脏病或中风的机会，并患有严重的心脏病。";
                    final String ramiprilTitleChinese = "雷米普利";
                    collapsedText.setText(ramiprilChinese);
                    expandedFullText.setText(ramiprilChinese);
                    titleText.setText(ramiprilTitleChinese);
                } else if (item.getTitle().equals("Ramipril") && !isEnglish) {
                    isEnglish = true;
                    titleText.setText(item.getTitle());
                    collapsedText.setText(item.getText().replace("\\n", "\n"));
                    expandedFullText.setText(item.getText().replace("\\n", "\n"));
                }
            }
        });
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

//                scrollView.setY(300);
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
                    NotificationScheduler.cancelReminder(mContext, AlarmReceiver.class);
                    notificationImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.inactive_icon));
                } else {
                    item.setShouldSendNotifications(true);
                    showTimePickerDialog(item);
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

    private void showTimePickerDialog(final PrescriptionCardItem item) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                NotificationScheduler.setReminder(mContext,AlarmReceiver.class,
                        selectedHour, selectedMinute, item.getTitle(), item.getDosage());
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

}