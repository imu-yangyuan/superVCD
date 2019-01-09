/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yangy
 */
public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader br;
    private InputStream is;
    private OutputStream os;
    private PrintWriter pw;

    public ClientHandler(Socket s) {
        socket = s;
    }

    @Override
    public void run() {

    }

    public String getInfo() {
        String reply = null;
        try {
            String info = "musicinfo";
            System.out.println(info);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.println(info);
            pw.flush();
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            reply = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reply;
    }

    public String getMusic(String muiscId) {
        String filePath = "";
        BufferedOutputStream bos = null;
        try {
            os = socket.getOutputStream();
            //输入流
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            //3.利用流按照一定的操作，对socket进行读写操作
            String info = "getMusicFile###" + muiscId;
            System.out.println("发送得到音乐数据");
            pw.println(info);
            pw.flush();
            DataInputStream is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //1、得到文件名       
            String baseDir = "E:\\musicClent\\temp\\";
            String tempString;
            tempString = is.readUTF();
            String[] strs1 = tempString.split("###");
            long filelength = Long.parseLong(strs1[0]);
            System.out.println(filelength);
            baseDir += strs1[1];
            filePath = baseDir;
            System.out.println("新生成的文件名为:" + baseDir);
            bos = new BufferedOutputStream(new FileOutputStream(baseDir));
            int bufferSize = 65536;
            byte[] buf = new byte[bufferSize];
            int length = 0;
            int read = 0;
            long start = System.currentTimeMillis();
            while (true) {
                if (filelength >= bufferSize) {
                    is.readFully(buf);
                    bos.write(buf, 0, bufferSize);
                } else {
                    byte[] smallBuf = new byte[(int) filelength];
                    is.readFully(smallBuf);
                    bos.write(smallBuf);
                    break;
                }
                filelength -= bufferSize;

            }
            long end = System.currentTimeMillis();

        } catch (Exception e) {
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (Exception e) {
            }
        }
        return filePath;
    }
public String getMusicAlbumPhoto(String MusicAlbumId) {
        String filePath = "";
        BufferedOutputStream bos = null;
        try {
            os = socket.getOutputStream();
            //输入流
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            //3.利用流按照一定的操作，对socket进行读写操作
            String info = "getMusicAlbumPhoto###" + MusicAlbumId;
            System.out.println("发送得到音乐数据");
            pw.println(info);
            pw.flush();
            DataInputStream is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String baseDir = "E:\\musicClent\\temp\\";
            String tempString;
            tempString = is.readUTF();
            String[] strs1 = tempString.split("###");
            long filelength = Long.parseLong(strs1[0]);
            System.out.println(filelength);
            baseDir += strs1[1];
            filePath = baseDir;
            System.out.println("新生成的文件名为:" + baseDir);
            bos = new BufferedOutputStream(new FileOutputStream(baseDir));
            int bufferSize = 65536;
            byte[] buf = new byte[bufferSize];
            int length = 0;
            int read = 0;
            long start = System.currentTimeMillis();
            while (true) {
                if (filelength >= bufferSize) {
                    is.readFully(buf);
                    bos.write(buf, 0, bufferSize);
                } else {
                    byte[] smallBuf = new byte[(int) filelength];
                    is.readFully(smallBuf);
                    bos.write(smallBuf);
                    break;
                }
                filelength -= bufferSize;

            }
            long end = System.currentTimeMillis();

        } catch (Exception e) {
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (Exception e) {
            }
        }
        return filePath;
    }

    public boolean getMusicAlbum(String musicAlbu0mId) {
        try {
            os = socket.getOutputStream();

            //输入流
            
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            //3.利用流按照一定的操作，对socket进行读写操作
            String info = "getMusicAlbum###" + musicAlbu0mId;
            System.out.println("发送得到音乐数据");
            pw.println(info);
            pw.flush();
            DataInputStream is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //1、得到文件名       
            String baseDir = "E:\\musicClent\\Album\\";
            String tempString;
            tempString = is.readUTF();
            String[] strs1 = tempString.split("###");
            String dirNameString = strs1[1];
            int fileNum = Integer.parseInt(strs1[2]);
            baseDir += dirNameString;
            new File(baseDir).mkdir();
            System.out.println(baseDir);
            System.out.println("创建文件夹成功");
            long start = System.currentTimeMillis();
            for (int i = 0; i < fileNum; i++) {
                String fileInfoString = is.readUTF();
                String[] fileInfoStrings = fileInfoString.split("###");
                long filelength = Long.parseLong(fileInfoStrings[1]);
                String fileNameString = fileInfoStrings[2];
                String filePath = baseDir + "\\" + fileNameString;
                System.out.println(filePath);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));//共耗时：1025毫秒 
                int bufferSize = 65536;
                byte[] buf = new byte[bufferSize];
                int length = 0;
                int read = 0;
                while (true) {
                    //2、把socket输入流写到文件输出流中去
                    if (filelength >= bufferSize) {
                        is.readFully(buf);
                        bos.write(buf, 0, bufferSize);
                    } else {
                        byte[] smallBuf = new byte[(int) filelength];
                        is.readFully(smallBuf);
                        bos.write(smallBuf);
                        break;
                    }
                    filelength -= bufferSize;
                    
                }
                bos.flush();
                bos.close();

            }
            long end = System.currentTimeMillis();
            System.out.println("共耗时：" + (end - start) + "毫秒");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean closeClient() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (pw != null) {
                pw.close();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
}
