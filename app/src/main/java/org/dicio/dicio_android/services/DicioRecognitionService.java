package org.dicio.dicio_android.services;

import android.content.Intent;
import android.speech.RecognitionService;
import android.util.Log;

public class DicioRecognitionService extends RecognitionService {

    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {
        Log.e("DICIO_DRS", "onStartListening");
    }

    @Override
    protected void onCancel(Callback listener) {
        Log.e("DICIO_DRS", "onCancel");
    }

    @Override
    protected void onStopListening(Callback listener) {
        Log.e("DICIO_DRS", "onStopListening");
    }
}
