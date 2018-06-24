package lib;

import android.media.MediaRecorder;



import java.io.IOException;

/**
 * Created by kimjeongcheol on 8/14/16.
 */
public class SoundMeter implements CustomTimer.TimerDelegate{

    private MediaRecorder mRecorder = null;
    private CustomTimer timer;
    private SoundMeterDelegate delegate;
    public SoundMeter(SoundMeterDelegate _delegate){
        timer = new CustomTimer(50,-1,this);
        delegate = _delegate;

    }
    public void removeSoundMeter(){
        stop();
        if(timer!=null){
            timer.removeTimer();
            timer=null;
        }

    }
    public void start() {
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");
            try {
                mRecorder.prepare();
                mRecorder.start();
                timer.timerStart();
            }
            catch (IOException e)
            {

            }

        }
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        timer.timerStop();
    }

    public double getAmplitude() {
        if (mRecorder != null)

            return  mRecorder.getMaxAmplitude();
        else
            return 0;

    }

    public void onTime(CustomTimer timer){
        if(delegate!=null){
            delegate.onCatchSound(mRecorder.getMaxAmplitude());
        }
    }
    public void onComplete(CustomTimer timer){

    }

    public interface SoundMeterDelegate
    {
        void onCatchSound(double rmsdB);

    }
}