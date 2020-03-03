package com.umair.archiTemplate2.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.blackbox.plog.pLogs.PLog
import java.util.*
import javax.inject.Inject

class TTSHelper @Inject constructor(
    val context: Context
) {

    private lateinit var textToSpeech: TextToSpeech

    fun setUpTextToSpeech() {
        textToSpeech = TextToSpeech(context,
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    val ttsLang = textToSpeech.setLanguage(Locale.US)

                    if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        PLog.logThis("TTS", "The Language is not supported!")
                    } else {
                        PLog.logThis("TTS", "Language Supported.")
                    }
                    PLog.logThis("TTS", "Initialization success.")
                } else {
                    Toast.makeText(
                        context,
                        "TTS Initialization failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

    }

    fun disposeTextToSpeech() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }

    fun speak(text: String) {
        val speechStatus = textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, text)

        if (speechStatus == TextToSpeech.ERROR) {
            PLog.logThis("TTS", "Error in converting Text to Speech!")
        }
    }
}