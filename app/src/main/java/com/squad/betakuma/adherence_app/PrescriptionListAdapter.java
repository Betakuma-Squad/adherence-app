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
import com.squad.betakuma.adherence_app.data_model.DataListener;
import com.squad.betakuma.adherence_app.data_model.Medication;
import com.squad.betakuma.adherence_app.data_model.DataManager;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.utilities.Installation;

import lombok.NonNull;

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
        public CardView mCardView;
        public TextView mTextView;

        public AdherenceViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.card_view);
            mTextView = v.findViewById(R.id.info_text);
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
                .inflate(R.layout.view_adherence_list_card, parent, false);
        AdherenceViewHolder vh = new AdherenceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AdherenceViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Prescription prescription = mDataset[position];
        Medication medication = prescription.getMedication();
        final String displayString = medication.getGenericName() + " (" + medication.getBrandName() + ", " + medication.getDIN() + ")";
        holder.mTextView.setText(displayString);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent medicationDetailActivityIntent = new Intent(mContext, MedicationDetailActivity.class);
                // TODO: pass in medication info as extra
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
