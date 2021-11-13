package com.example.ttstest;

import android.os.Bundle;
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
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    int ttsLang = tts.setLanguage(Locale.US);

                    if ( ttsLang == TextToSpeech.LANG_MISSING_DATA
                        || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS","언어 지원 안함");
                    } else {
                        Log.e("TTS","언어 지원함" );
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "TTS 초기화 실패!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                String editData = editText.getText().toString();
                Log.i("TTS",  "editData: " + editData);

                public void
                Papago papago = new Papago();
                String transData = papago.getTranslation(editData, "en");
6
                Log.i("TTS",  "transData: " + transData);

                String data = parseJSON(transData);
                Log.i("TTS", data + "번역됨");

                int speechStatus = tts.speak(data, TextToSpeech.QUEUE_FLUSH, null);

                if (speechStatus == TextToSpeech.ERROR)  {
                    Log.e("TTS", "Error in converting Text to Speech!");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(tts !=null) {
            tts.stop();
            tts.shutdown();
        }
    }

    private String parseJSON(String json){
        try{
            JSONObject jsonObject = new JSONObject(json);
            JSONObject messageObject = (JSONObject) jsonObject.get("message");
            JSONObject resultObject = (JSONObject) messageObject.get("result");

            String data = (String) resultObject.get("translatedText");

            return data;

        }catch (JSONException e) {
            return e.toString();
        }
    }
}
