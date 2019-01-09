package supervcdstore.utils;

import javax.sound.sampled.*;
import supervcdstore.UI.MusicPlayerFrame;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicPlayer extends Thread {
	private MusicPlayerFrame frame=null;
	private String filePath = null;
	private AudioInputStream din = null;
	private AudioFormat decodedFormat=null;
	private SourceDataLine line=null;
	private int flag=1;
	public MusicPlayer(String filename,MusicPlayerFrame frame)  {
		this.frame=frame;
		this.filePath = filename;
		AudioInputStream in=null;
            try {
                in = AudioSystem.getAudioInputStream(new File(filename));
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
		AudioFormat baseFormat = in.getFormat();
		decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
				baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
				false);
		din = AudioSystem.getAudioInputStream(decodedFormat, in);
	}

	private void rawplay() throws LineUnavailableException, IOException {
		byte[] data = new byte[4096];
		line= getLine(decodedFormat);
		if (line != null) {
			int nBytesRead = 0, nBytesWritten = 0;
			while (nBytesRead != -1) {
				line.start();
				if(flag==1){
					nBytesRead = din.read(data, 0, data.length);
					if (nBytesRead != -1)
						nBytesWritten = line.write(data, 0, nBytesRead);
					frame.setProgressBar(line.getMicrosecondPosition());
				}else if(flag==2){
					break;
				}
			}
			// Stop
			line.drain();
			line.stop();
			line.close();
			din.close();
		}
	}

	private SourceDataLine getLine(AudioFormat audioFormat)
			throws LineUnavailableException {
		SourceDataLine res = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
		res = (SourceDataLine) AudioSystem.getLine(info);
		res.open(audioFormat);
		return res;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			rawplay();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void beginPlay() {
		// TODO Auto-generated method stub
		if(this.isAlive()){
			flag=1;
		}else{
			this.start();
		}
	}
	public void stopPlay() {
		// TODO Auto-generated method stub
		flag=0;
	}
	public void closePlay() {
		// TODO Auto-generated method stub
		flag=2;
		line.drain();
		line.stop();
		line.close();
		try {
			din.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public long getTime(){
		return line.getMicrosecondPosition();
	}
	public int getStatu(){
		return flag;
	}
	public String getPath(){
		return this.filePath;
	}
	public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		MusicPlayer a = null;
		//a = new MusicPlayer("Black Eyed Peas - My Humps - Lil Jon Remix.mp3");
		a.beginPlay();
		//sleep(1000);
		a.stopPlay();
	}

}
