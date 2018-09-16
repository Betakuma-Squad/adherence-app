/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squad.betakuma.adherence_app.ocr;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.squad.betakuma.adherence_app.data_model.DataManager;
import com.squad.betakuma.adherence_app.data_model.Medication;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.data_model.SideEffectRarity;
import com.squad.betakuma.adherence_app.survey.SurveyResponse;
import com.squad.betakuma.adherence_app.ui.camera.GraphicOverlay;
import com.squad.betakuma.adherence_app.utilities.Installation;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 * TODO: Make this implement Detector.Processor<TextBlock> and add text to the GraphicOverlay
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private Context context;
    private OcrPrescriptionListener ocrPrescriptionListener;
    private GraphicOverlay<OcrGraphic> graphicOverlay;

    OcrDetectorProcessor(Context context, OcrPrescriptionListener listener, GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        this.context = context;
        ocrPrescriptionListener = listener;
        graphicOverlay = ocrGraphicOverlay;
    }

    // TODO:  Once this implements Detector.Processor<TextBlock>, implement the abstract methods.
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        graphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                Log.d("Processor", "Text detected! " + item.getValue());
                OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
                graphicOverlay.add(graphic);
                parsePrescription(item);
            }
        }
    }

    private void parsePrescription(TextBlock item) {
        DataManager dataManager = DataManager.getInstance(Installation.id(context));
        if (item.getValue().contains("ramipril")) {
            Prescription ramiprilPrescription = new Prescription(
                    120,
                    120,
                    2,
                    3,
                    new Medication("02247918", "ramipril", "PMS-Ramipril", "5mg", "", new HashMap<SideEffectRarity, ArrayList<String>>()),
                    "Ramipril is used alone or together with other medicines to treat " +
                            "high blood pressure (hypertension). High blood pressure adds to the " +
                            "workload of the heart and arteries. If it continues for a long time, " +
                            "the heart and arteries may not function properly. This can damage the " +
                            "blood vessels of the brain, heart, and kidneys, resulting in a stroke, " +
                            "heart failure, or kidney failure. Lowering blood pressure can reduce the " +
                            "risk of strokes and heart attacks.\\nRamipril is an angiotensin-converting " +
                            "enzyme (ACE) inhibitor. It works by blocking a substance in the body " +
                            "that causes blood vessels to tighten. As a result, ramipril relaxes the blood " +
                            "vessels. This lowers blood pressure and increases the supply of blood and " +
                            "oxygen to the heart.\\nRamipril is also used in some patients after a heart " +
                            "attack. After a heart attack, some of the heart muscle is damaged and weakened. " +
                            "The heart muscle may continue to weaken as time goes by. This makes it more " +
                            "difficult for the heart to pump blood. Ramipril may be started within the first " +
                            "few days after a heart attack to increase survival rate.\\nRamipril is also used " +
                            "to lessen the chance of heart attacks or strokes in patients 55 years of age or " +
                            "older and have serious heart disease.",
                    new ArrayList<SurveyResponse>()
            );
            dataManager.addPrescription(ramiprilPrescription);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("advil")) {
            Prescription advilPrescription = new Prescription(
                    120,
                    120,
                    2,
                    3,
                    new Medication("012334", "advil", "advil", "5mg", "", new HashMap<SideEffectRarity, ArrayList<String>>()),
                    "test",
                    new ArrayList<SurveyResponse>()
            );
            dataManager.addPrescription(advilPrescription);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        }
    }

    @Override
    public void release() {
        graphicOverlay.clear();
    }
}
