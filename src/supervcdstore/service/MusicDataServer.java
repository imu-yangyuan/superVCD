/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
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
public class MusicDataServer {

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static ThreadPool threadPool;
    private final static int POOL_SIZE = 4;//单个CPU时线程池中工作线程的数目
    public static void main(String[] args) {
        try {
            //建立Server Socket并等待连接请求。
            threadPool = new ThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
            ServerSocket server = new ServerSocket(60666);
            System.out.println("服务器启动");
            while (true) {
                Socket socket = null;
                try {
                    socket = server.accept();
                    threadPool.execute(new Handler(socket)); //把与客户通信的任务交给线程池
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

