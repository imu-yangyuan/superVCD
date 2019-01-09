/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supervcdstore;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Client {
    private  ClientHandler clientHandler;
    public boolean login() {
        try {
            //1.建立客户端socket连接，指定服务器位置及端口
           Socket socket = new Socket("localhost", 60666);
            //2.得到socket读写流
            if(socket!=null){
            clientHandler= new ClientHandler(socket);
            return true;
            }
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public ClientHandler getClientHandler() {
        return clientHandler;
    }
}
