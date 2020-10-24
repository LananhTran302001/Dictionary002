package services;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class TextToSpeech {
    private String text;
    private String voice;

    public TextToSpeech(String spelling) {
        this.text = spelling;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void read() {
        if (text == null) {
            throw new NullPointerException();
        }
        VoiceManager manager = VoiceManager.getInstance();
        Voice kevin = manager.getVoice("kevin16");
        kevin.allocate();
        kevin.speak(text);
        kevin.deallocate();

    }

}
