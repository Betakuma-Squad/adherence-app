package com.squad.betakuma.adherence_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sherryuan on 2018-09-14.
 */

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.AdherenceViewHolder> {
    public Context mContext;
    private Medication[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class AdherenceViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView mCardView;
        public TextView mTextView;

        public AdherenceViewHolder(CardView v) {
            super(v);
            mCardView = v.findViewById(R.id.card_view);
            mTextView = v.findViewById(R.id.info_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicationAdapter(Context context) {
        mContext = context;
        mDataset = new Medication[1];
        Map<SideEffectRarity, String[]> ramiprilSideEffects = new HashMap<>();
        String[] moreCommonSideEffects = {"Blurred vision",
                "Confusion",
                "Dizziness, faintness, or lightheadedness when getting up suddenly from a lying or sitting position",
                "Sweating",
                "Unusual tiredness or weakness"};
        ramiprilSideEffects.put(SideEffectRarity.MoreCommon, moreCommonSideEffects);

        mDataset[0] = new Medication("Ramipril",
                "PMS-Ramipril",
                "02247918",
                "5mg",
                "Take one capsule once daily",
                "Ramipril is used alone or together with other medicines to treat high blood pressure (hypertension). High blood pressure adds to the workload of the heart and arteries. If it continues for a long time, the heart and arteries may not function properly. This can damage the blood vessels of the brain, heart, and kidneys, resulting in a stroke, heart failure, or kidney failure. Lowering blood pressure can reduce the risk of strokes and heart attacks.\n" +
                        "\n" +
                        "Ramipril is an angiotensin-converting enzyme (ACE) inhibitor. It works by blocking a substance in the body that causes blood vessels to tighten. As a result, ramipril relaxes the blood vessels. This lowers blood pressure and increases the supply of blood and oxygen to the heart.\n" +
                        "\n" +
                        "Ramipril is also used in some patients after a heart attack. After a heart attack, some of the heart muscle is damaged and weakened. The heart muscle may continue to weaken as time goes by. This makes it more difficult for the heart to pump blood. Ramipril may be started within the first few days after a heart attack to increase survival rate.\n" +
                        "\n" +
                        "Ramipril is also used to lessen the chance of heart attacks or strokes in patients 55 years of age or older and have serious heart disease. \n",
                ramiprilSideEffects);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MedicationAdapter.AdherenceViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_adherence_list_card, parent, false);
        AdherenceViewHolder vh = new AdherenceViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AdherenceViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Medication medication = mDataset[position];
        holder.mTextView.setText(medication.genericName + " (" + medication.brandName + ", " + medication.DIN + ")");
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
