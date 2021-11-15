package com.example.ttstest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private Button btn;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        editText = (EditText) findViewById(R.id.et);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int state) {
                if (state == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.KOREAN);
                } else {
                    Toast.makeText(MainActivity.this, "TTS 객체 초기화 오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new Thread(() -> {
                    String word = editText.getText().toString();
                    Papago papago = new Papago();
                    String resultWord = papago.getTranslation(word, "en");

                    Bundle papagoBundle = new Bundle();
                    papagoBundle.putString("resultWord", resultWord);

                    Message msg = papago_handler.obtainMessage();
                    msg.setData(papagoBundle);
                    papago_handler.sendMessage(msg);
                }).start();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler papago_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String resultWord = bundle.getString("resultWord");
            Log.i("papago", resultWord);
            tts.speak(resultWord, TextToSpeech.QUEUE_FLUSH, null);
        }
    };

}

