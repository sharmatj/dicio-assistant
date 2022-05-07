package org.dicio.dicio_android.services;

import android.content.Intent;
import android.speech.RecognitionService;
import android.util.Log;

public class DicioRecognitionService extends RecognitionService {

    public static final String TAG = "DicioRecognServ";

    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {
        Log.e(TAG, "onStartListening");
    }

    @Override
    protected void onCancel(Callback listener) {
        Log.e(TAG, "onCancel");
    }

    @Override
    protected void onStopListening(Callback listener) {
        Log.e(TAG, "onStopListening");
    }
}
