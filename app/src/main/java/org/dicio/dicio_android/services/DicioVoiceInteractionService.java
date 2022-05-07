package org.dicio.dicio_android.services;

import static org.dicio.dicio_android.input.VoskInputDevice.MODEL_PATH;
import static org.dicio.dicio_android.input.VoskInputDevice.SAMPLE_RATE;

import android.service.voice.VoiceInteractionService;
import android.util.Log;

import androidx.annotation.Nullable;

import org.dicio.dicio_android.BuildConfig;
import org.unbescape.json.JsonEscape;
import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.vosk.android.RecognitionListener;
import org.vosk.android.SpeechService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DicioVoiceInteractionService extends VoiceInteractionService
        implements RecognitionListener {

    public static final String TAG = "DicioVoiceIntServ";

    @Nullable
    private SpeechService speechService = null;

    final List<String> hotwords = new ArrayList<>() {{
        add("assistant");
    }};


    @Override
    public void onReady() {
        super.onReady();
        if (new File(getModelDirectory(), "ivector").exists()) {
            Log.d(TAG, "Vosk model in place");

            try {
                LibVosk.setLogLevel(BuildConfig.DEBUG ? LogLevel.DEBUG : LogLevel.WARNINGS);
                final Model model = new Model(getModelDirectory().getAbsolutePath());

                final Recognizer recognizer = new Recognizer(model, SAMPLE_RATE,
                        hotwordsToGrammar(hotwords));
                recognizer.setMaxAlternatives(5);

                speechService = new SpeechService(recognizer, SAMPLE_RATE);
                speechService.startListening(this);
            } catch (final IOException e) {
                Log.e(TAG, "Failed to setup Vosk", e);
            }
        } else {
            Log.d(TAG, "Vosk model not in place, stopping service");
            stopSelf();
        }
    }

    @Override
    public void onShutdown() {
        super.onShutdown();

        if (speechService != null) {
            speechService.cancel();
            speechService.shutdown();
        }
    }


    @Override
    public void onPartialResult(final String hypothesis) {

    }

    @Override
    public void onResult(final String hypothesis) {
        Log.d(TAG, "onResult called with hypothesis = " + hypothesis);

        final String lowercaseHypothesis = hypothesis.toLowerCase(Locale.getDefault());
        for (final String hotword : hotwords) {
            if (lowercaseHypothesis.contains(hotword)) {
                Log.d(TAG, "Hotword detected!");
            }
        }
    }

    @Override
    public void onFinalResult(final String hypothesis) {

    }

    @Override
    public void onError(final Exception exception) {

    }

    @Override
    public void onTimeout() {

    }


    private File getModelDirectory() {
        // TODO duplicate code
        return new File(getFilesDir(), MODEL_PATH);
    }

    private static String hotwordsToGrammar(final List<String> hotwords) {
        final StringBuilder stringBuilder = new StringBuilder("[");
        for (int i = 0; i < hotwords.size(); ++i) {
            if (i != 0) {
                stringBuilder.append(",");
            }

            stringBuilder.append("\"");
            stringBuilder.append(JsonEscape.escapeJson(hotwords.get(i)));
            stringBuilder.append("\"");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
