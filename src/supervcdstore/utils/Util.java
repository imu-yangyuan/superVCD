package supervcdstore.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Util {
	   /**
     * 转码的一个方便的方法
     * @param source 要转的字符串
     * @param sourceEnc 字符串原来的编码
     * @param distEnc 要转成的编码
     * @return 转后的字符串
     */
    public static String convertString(String source, String sourceEnc, String distEnc) {
        try {
            byte[] data = source.getBytes(sourceEnc);
            return new String(data, distEnc);
        } catch (UnsupportedEncodingException ex) {
            return source;
        }
    }
	/**
	 * 把long类型的时间转为00：00格式的字符串时间用于显示
	 * @param num long 类型时间
	 * @return	转后的string时间
	 */
	public static String getTimeString(Long num){
		int total =(int) (num/ 1000000);
		int minute=total/60;
		int second=total%60;
		if(minute<10){
			if(second<10)
				return "0"+minute+":0"+second;
			else 
				return "0"+minute+":"+second;
		}else{
			if(second<10)
				return minute+":0"+second;
			else 
				return minute+":"+second;
		}
	}
	/**
	 * 获取音乐总时长	
	 * @param path	音乐路径
	 * @return
	 * @throws LineUnavailableException
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 */
	public static long getTime(String path) throws LineUnavailableException,
			UnsupportedAudioFileException, IOException {
		AudioInputStream in = AudioSystem.getAudioInputStream(new File(path));
		AudioFormat baseFormat = in.getFormat();
		AudioFormat decodedFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
				16, baseFormat.getChannels(), baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(), false);
		AudioInputStream din = AudioSystem.getAudioInputStream(decodedFormat,
				in);
		Clip clip = AudioSystem.getClip();
		clip.open(din);
		clip.close();
		return  clip.getMicrosecondLength();
	}

	/**
	 * 用于文件拷贝
	 * 
	 * @param from
	 *            文件拷贝来源
	 * @param dest
	 *            目的地址
	 * @return 是否成功
	 */
	public static String copyFile(File from, String dest) {
		File file2 = new File(dest + "\\" + from.getName());
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(from));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					file2));
			is.available();
			byte byteArray[] = new byte[1024];// 大小可调
			int count = -1;
			while ((count = is.read(byteArray)) != -1) {
				os.write(byteArray, 0, count);
			}
			os.flush();
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (from.getName() + "拷贝成功");
	}

	/**
	 * 将字节数组转为图片
	 * @param img
	 */
	public static void writPic(byte[] img) {
		try {
			File f = new File("Images/temp.jpg");
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
