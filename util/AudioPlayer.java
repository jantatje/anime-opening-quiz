package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	
	private Clip clip;
	private FloatControl gainControl;
	
	private String userDir = System.getProperty("user.dir");
	private Path home = Paths.get(userDir);
	
	public AudioPlayer(String fileName) {
		try {
			Path pl = home.resolve(Paths.get("Resources"));
			FileInputStream in = new FileInputStream(new File(pl.toString()+"/music/"+fileName));
			
				AudioInputStream as = AudioSystem.getAudioInputStream(in);
				
				AudioFormat baseFormat = as.getFormat();
				
				AudioFormat decodeFormat = new AudioFormat(
						AudioFormat.Encoding.PCM_SIGNED,
						baseFormat.getSampleRate(),
						16,
						baseFormat.getChannels(),
						baseFormat.getChannels()*2,
						baseFormat.getSampleRate(),
						false);
				
				AudioInputStream das = AudioSystem.getAudioInputStream(decodeFormat,as);
				
				clip = AudioSystem.getClip();
				clip.open(das);
			
			} catch (NullPointerException | UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
	}
	
	public void start(){
		if(clip==null)return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	public void stop() {
		if(clip.isRunning())
			clip.stop();
		
	}
	public void close(){
		stop();
		clip.close();
	}
	
	public void setLooping(boolean loop){
		if(loop==true){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}else{
			clip.loop(0);
		}
		
	}
	public void setVolume(float value) {
		 gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		 float dB = (float)(Math.log(value)/Math.log(10.0)*20.0);
		 gainControl.setValue(dB);
	}
	public float getVolume(){
		gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float value = (float) Math.exp(gainControl.getValue()*Math.log(10.0)/20.0);
		return value;
	}
	public int getPosition(){
		return clip.getFramePosition();
	}
	public void setPosition(int pos){
		clip.setFramePosition(pos);
	}
	
}
