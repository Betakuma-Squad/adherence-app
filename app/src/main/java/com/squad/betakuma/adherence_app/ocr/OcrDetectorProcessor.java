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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squad.betakuma.adherence_app.data_model.DataManager;
import com.squad.betakuma.adherence_app.data_model.Medication;
import com.squad.betakuma.adherence_app.data_model.Prescription;
import com.squad.betakuma.adherence_app.data_model.SideEffectRarity;
import com.squad.betakuma.adherence_app.survey.SurveyResponse;
import com.squad.betakuma.adherence_app.ui.camera.GraphicOverlay;
import com.squad.betakuma.adherence_app.utilities.Installation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("medication.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return json;

    }


    private void parsePrescription(TextBlock item) {
        DataManager dataManager = DataManager.getInstance(Installation.id(context));
        Gson gson = new Gson();
        Type mapType = new TypeToken<HashMap<String, Prescription>>(){}.getType();
        HashMap<String, Prescription> jsonPrescriptions =
                gson.fromJson(loadJSONFromAsset(), mapType);
        if (item.getValue().contains("Ramipril")) {
            Prescription ramipril = jsonPrescriptions.get("PMS-Ramipril");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Simvastatin")) {
            Prescription ramipril = jsonPrescriptions.get("Mylan-Simvastatin");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Metoprolol")) {
            Prescription ramipril = jsonPrescriptions.get("Apo-Metoprolol");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Paroxetine")) {
            Prescription ramipril = jsonPrescriptions.get("Pms-Paroxetine");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Temazepam")) {
            Prescription ramipril = jsonPrescriptions.get("Mylan-Temazepam");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Docusate sodium")) {
            Prescription ramipril = jsonPrescriptions.get("Docusate sodium Capsules");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Acetaminophen")) {
            Prescription ramipril = jsonPrescriptions.get("Apo Acetaminophen");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Centrum")) {
            Prescription ramipril = jsonPrescriptions.get("Centrum Multivitamin");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Ventolin HFA")) {
            Prescription ramipril = jsonPrescriptions.get("Ventolin HFA");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        } else if (item.getValue().contains("Advair Diskus")) {
            Prescription ramipril = jsonPrescriptions.get("Advair Diskus");
            dataManager.addPrescription(ramipril);
            ocrPrescriptionListener.onPrescriptionFound(dataManager.getDataset().length - 1);
        }
    }

    @Override
    public void release() {
        graphicOverlay.clear();
    }
}
