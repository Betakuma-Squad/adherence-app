package com.squad.betakuma.adherence_app.DataModel;

import com.squad.betakuma.adherence_app.DataModel.Medication;
import com.squad.betakuma.adherence_app.DataModel.SideEffectRarity;

import java.util.HashMap;
import java.util.Map;

public class MedicationManager {
    // get the backing array of Medication data
    public Medication[] getDataset() {
        Medication[] dataset = new Medication[1];
        Map<SideEffectRarity, String[]> ramiprilSideEffects = new HashMap<>();
        String[] moreCommonSideEffects = {"Blurred vision",
                "Confusion",
                "Dizziness, faintness, or lightheadedness when getting up suddenly from a lying or sitting position",
                "Sweating",
                "Unusual tiredness or weakness"};
        ramiprilSideEffects.put(SideEffectRarity.MoreCommon, moreCommonSideEffects);
        String instructions = "Take one capsule once daily";
        dataset[0] = new Medication("Ramipril",
                "PMS-Ramipril",
                "02247918",
                "5mg",
                "Ramipril is used alone or together with other medicines to treat high blood pressure (hypertension). High blood pressure adds to the workload of the heart and arteries. If it continues for a long time, the heart and arteries may not function properly. This can damage the blood vessels of the brain, heart, and kidneys, resulting in a stroke, heart failure, or kidney failure. Lowering blood pressure can reduce the risk of strokes and heart attacks.\n" +
                        "\n" +
                        "Ramipril is an angiotensin-converting enzyme (ACE) inhibitor. It works by blocking a substance in the body that causes blood vessels to tighten. As a result, ramipril relaxes the blood vessels. This lowers blood pressure and increases the supply of blood and oxygen to the heart.\n" +
                        "\n" +
                        "Ramipril is also used in some patients after a heart attack. After a heart attack, some of the heart muscle is damaged and weakened. The heart muscle may continue to weaken as time goes by. This makes it more difficult for the heart to pump blood. Ramipril may be started within the first few days after a heart attack to increase survival rate.\n" +
                        "\n" +
                        "Ramipril is also used to lessen the chance of heart attacks or strokes in patients 55 years of age or older and have serious heart disease. \n",
                ramiprilSideEffects);
        return dataset;
    }

    // reload data from Firebase
    public void reloadData() {

    }
}
