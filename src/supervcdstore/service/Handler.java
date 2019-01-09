/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import supervcdstore.bean.MusicAlbumPath;
import supervcdstore.bean.MusicKind;
import supervcdstore.bean.MusicPath;

/**
 *
 * @author Administrator
 */
public class Handler extends Thread implements StoneForestProtocol {
    protected Socket clientSocket;
    protected OutputStream outputStream;
    protected InputStream inputStream;
    private BufferedReader br;
    private List<MusicKind> infos;
    private List<MusicKind> musicKinds = new ArrayList<MusicKind>();
    private List<MusicAlbumPath> musicAlbumPaths = new ArrayList<MusicAlbumPath>();
    private List<MusicPath> musicPaths = new ArrayList<MusicPath>();
    private Gson gson;
    public Handler(Socket socket) throws IOException {
        clientSocket = socket;
        outputStream = clientSocket.getOutputStream();
        String musicKindsString = initData("E:\\NetBeansworkmulu\\SuperVCDStore\\musicKindsJson.txt");
        String musicAlbumPathsString = initData("E:\\NetBeansworkmulu\\SuperVCDStore\\musicAlbumsPathJson.txt");
        String musicPathString = initData("E:\\NetBeansworkmulu\\SuperVCDStore\\musicPathJson.txt");
        Gson gson = new Gson();
        musicKinds = gson.fromJson(musicKindsString, new TypeToken<List<MusicKind>>() {
        }.getType());
        musicAlbumPaths = gson.fromJson(musicAlbumPathsString, new TypeToken<List<MusicAlbumPath>>() {
        }.getType());
        musicPaths = gson.fromJson(musicPathString, new TypeToken<List<MusicPath>>() {
        }.getType());
    }

  
    public void run() {
        try {
            String msg = null;
            gson=new Gson();
            inputStream = clientSocket.getInputStream();
            br = new BufferedReader(new InputStreamReader(inputStream));
            while (!"bye".equals(msg = br.readLine())) {
                System.out.println(msg);
                String[] strs = msg.split("###");
                if (strs[0].equals("bye")) {
                    break;
                } else if ("musicinfo".equals(strs[0])) {
                    PrintWriter pw = new PrintWriter(outputStream);
                    String reply = gson.toJson(musicKinds);
                    pw.println(reply);
                    pw.flush();
                } else if ("getMusicFile".equals(strs[0])) {
                    String musicId=strs[1];
                    String musicPath="";
                    for (int i = 0; i < musicPaths.size(); i++) {
                        if(musicId.equals(musicPaths.get(i).getMusicId())){
                            musicPath=musicPaths.get(i).getMusicPath();
                            break;
                        }
                    }
                    DownMusic(musicPath);
                } else if ("getMusicAlbum".equals(strs[0])) {
                         String albumId=strs[1];
                    String albumPath="";
                    System.out.println("getMusicAlbum");
                    for (int i = 0; i < musicAlbumPaths.size(); i++) {
                        if(albumId.equals(musicAlbumPaths.get(i).getAlbumId())){
                            albumPath=musicAlbumPaths.get(i).getAlbumPath();
                            break;
                        }
                    }
                    DownAlbum(albumPath);
                }else if ("getMusicAlbumPhoto".equals(strs[0])) {
                         String albumId=strs[1];
                    String albumPhotoPath="";
                    for (int i = 0; i < musicAlbumPaths.size(); i++) {
                        if(albumId.equals(musicAlbumPaths.get(i).getAlbumId())){
                            albumPhotoPath=musicAlbumPaths.get(i).getPhotoPath();
                            break;
                        }
                    }
                    DownMusicAlbumPhoto(albumPhotoPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void DownMusic(String filePath) {
         try {
                        File fi = new File(filePath);
                        System.out.println(filePath);
                        DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                        DataOutputStream ps = new DataOutputStream(outputStream);
                        String fileInfoString = fi.getName();
                        long fileLength = fi.length();
                        fileInfoString = fileLength + "###" + fileInfoString;
                        ps.writeUTF(fileInfoString);
                        ps.flush();
                        int bufferSize = 65536;
                        byte[] buf = new byte[bufferSize];
                        while (true) {
                            System.out.println("11");
                            int read = 0;
                            if (fis != null) {
                                read = fis.read(buf);
                            }
                            if (read == -1) {
                                break;
                            }
                            ps.write(buf, 0, read);
                        }
                        ps.flush();
                        //ps.close();
                        fis.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
    }
public void DownMusicAlbumPhoto(String filePath) {
         try {
                        File fi = new File(filePath);
                        System.out.println(filePath);
                        DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                        String fileInfoString = fi.getName();
                        long fileLength = fi.length();
                        fileInfoString = fileLength + "###" + fileInfoString;
                        dataOutputStream.writeUTF(fileInfoString);
                        dataOutputStream.flush();
                        int bufferSize = 65536;
                        byte[] buf = new byte[bufferSize];
                        while (true) {
                            int read = 0;
                            if (fis != null) {
                                read = fis.read(buf);
                            }
                            if (read == -1) {

                                System.out.println("break");

                                break;
                            }
                            dataOutputStream.write(buf, 0, read);
                        }
                        dataOutputStream.flush();
                        fis.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
    }
    private static String readMusicInfo() {
        String str = "";
        File file = new File("G:\\JavaSE大作业\\SuperVCDStore\\src\\musicInfo.txt");
        try {
            FileInputStream in = new FileInputStream(file);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "utf-8");
            System.out.println(str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("读取文件失败");
            return " ";

        }
        return str;
    }

    private  String initData(String configFilePath) {
        String str = "";
        File file = new File(configFilePath);
        try {
            FileInputStream in = new FileInputStream(file);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            str = new String(buffer, "utf-8");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("读取文件失败");
            return " ";

        }
        //System.out.println("sdaf"+str);
        return str;
    }
    private void DownAlbum(String AlbumPath){
           try {
                        File[] files = (new File(AlbumPath)).listFiles();

                        DataOutputStream ps = new DataOutputStream(outputStream);
                        //文件夹名称文件夹文件个数
                        //String dirNameString=dirpath.substring(dirpath.lastIndexOf("\\"), dirpath.length());
                        String dirNameString = new File(AlbumPath).getName();
                        System.out.println("dirNameString"+dirNameString);
                        String fileDirInfoString = "dirInfo###" + dirNameString + "###" + files.length;
                        ps.writeUTF(fileDirInfoString);
                        ps.flush();
                        int bufferSize = 65536;
                        byte[] buf = new byte[bufferSize];
                        for (int i = 0; i < files.length; i++) {
                            if (files[i].isFile()) {
                                String fileInfoString = "fileInfo###" + files[i].length() + "###" + files[i].getName();
                                ps.writeUTF(fileInfoString);
                                ps.flush();
                                System.out.println(files[i].getAbsolutePath());
                                DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(files[i].getAbsolutePath())));
                                while (true) {
                                    int read = 0;
                                    if (fis != null) {
                                        read = fis.read(buf);
                                    }
                                    if (read == -1) {
                                        break;
                                    }
                                    ps.write(buf, 0, read);
                                }
                                ps.flush();
                                fis.close();
                            }

                        }

                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

    }
}
