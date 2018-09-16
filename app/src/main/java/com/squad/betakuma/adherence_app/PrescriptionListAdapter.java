package com.squad.betakuma.adherence_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squad.betakuma.adherence_app.swipable_cards.MedicationDetailActivity;
import com.squad.betakuma.adherence_app.swipable_cards.PrescriptionDetailActivity;
import com.squad.betakuma.adherence_app.data_model.DataListener;
import com.squad.betakuma.adherence_app.data_model.Medication;
import com.squad.betakuma.adherence_app.data_model.DataManager;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.utilities.Installation;

import lombok.NonNull;

import static com.squad.betakuma.adherence_app.swipable_cards.MedicationDetailActivity.POSITION;

/**
 * Created by sherryuan on 2018-09-14.
 */

public class PrescriptionListAdapter extends RecyclerView.Adapter<PrescriptionListAdapter.AdherenceViewHolder> implements DataListener {
    public Context mContext;
    final private DataManager manager;
    private Prescription[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class AdherenceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mListItemView;
        public TextView mTitleView;
        public TextView mSubtitleView;
        public TextView mQuantityView;
        public TextView mRefillsView;
        public TextView mDoseView;
        public TextView mInstructionsView;

        public AdherenceViewHolder(View v) {
            super(v);
            mListItemView = v.findViewById(R.id.prescription_list_item_view);
            mTitleView = v.findViewById(R.id.prescription_list_item_title_text);
            mSubtitleView = v.findViewById(R.id.prescription_list_item_subtitle_text);
            mQuantityView = v.findViewById(R.id.prescription_list_item_quantity);
            mRefillsView = v.findViewById(R.id.prescription_list_item_refills);
            mDoseView = v.findViewById(R.id.prescription_list_item_dose_text);
            mInstructionsView = v.findViewById(R.id.prescription_list_item_instructions_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PrescriptionListAdapter(Context context) {
        mContext = context;
        manager = DataManager.getInstance(Installation.id(context));
        manager.registerListener(this);
        mDataset = manager.getDataset();
    }

    public void onDataUpdate() {
        mDataset = manager.getDataset();
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PrescriptionListAdapter.AdherenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_adherence_prescription_list_card, parent, false);
        AdherenceViewHolder vh = new AdherenceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AdherenceViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Prescription prescription = mDataset[position];
        final Medication medication = prescription.getMedication();
        holder.mTitleView.setText(medication.getGenericName());
        final String subtitle = medication.getBrandName() + ", " + medication.getDIN();
        holder.mSubtitleView.setText(subtitle);
        final String quantity = prescription.getQuantity() + "/" + prescription.getTotalQuantity();
        holder.mQuantityView.setText(quantity);
        final String refills = prescription.getRefills() + "/" + prescription.getTotalRefills();
        holder.mRefillsView.setText(refills);
        holder.mDoseView.setText(medication.getDose());
        holder.mInstructionsView.setText(prescription.getInstructions());
        holder.mListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicationDetailActivityIntent = new Intent(mContext, MedicationDetailActivity.class);
                medicationDetailActivityIntent.putExtra(POSITION, position);
                mContext.startActivity(medicationDetailActivityIntent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
