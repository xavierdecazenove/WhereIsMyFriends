package com.example.xavierdecazenove1.ass2.Connection;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by tsroax on 2014-09-30.
 */

public class TCPConnection {

    public static final String TAG = "TCPConnection";

    private RunOnThread thread;
    private Receive receive;
    private ReceiveListener listener;

    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;

    private InetAddress address;
    private int connectionPort;
    private String ip;
    private Exception exception;
    private boolean connected = false;

    public TCPConnection(String ip, int connectionPort, ReceiveListener listener) {
        this.ip = ip;
        this.connectionPort = connectionPort;
        this.listener = listener;
        thread = new RunOnThread();

    }

    public void connect() {
        thread.start();
        thread.execute(new Connect());
    }

    public void disconnect() {
        thread.execute(new Disconnect());
    }

    public void send(String expression) {
        if (!connected) {
            thread.execute(new Connect());
        }

        thread.execute(new Send(expression));
    }

    private class Receive extends Thread {
        public void run() {
            String result;
            try {
                while (receive != null) {
                    result = is.readUTF();
                    listener.newMessage(result);
                    Log.d(TAG, "Received message: " + result);
                }
            } catch (Exception e) { // IOException, ClassNotFoundException
                receive = null;
            }
        }
    }

    public Exception getException() {
        Exception result = exception;
        exception = null;
        return result;
    }

    private class Connect implements Runnable {
        public void run() {
            try {
                address = InetAddress.getByName(ip);

                socket = new Socket(address, connectionPort);
                is = new DataInputStream(socket.getInputStream());
                os = new DataOutputStream(socket.getOutputStream());
                os.flush();

                listener.newMessage("CONNECTED");
                receive = new Receive();
                receive.start();
                connected = true;
                Log.d(TAG, "Connected!");
            } catch (Exception e) { // SocketException, UnknownHostException
                Log.d("TCPConnection-Connect",e.toString());
                exception = e;
                listener.newMessage("EXCEPTION");
            }
        }
    }

    public class Disconnect implements Runnable {
        public void run() {
            try {
                if (socket != null)
                    socket.close();
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
                if (receive != null) {
                    receive.interrupt();
                    receive = null;
                }
                thread.stop();
                listener.newMessage("CLOSED");
                Log.d(TAG, "Disconnected");
                connected = false;
            } catch(IOException e) {
                exception = e;
                listener.newMessage("EXCEPTION");
            }
        }
    }

    public class Send implements Runnable {
        private String exp;

        public Send(String exp) {
            this.exp = exp;
        }

        public void run() {
            try {
                os.writeUTF(exp);
                os.flush();
                Log.d(TAG, "Sent message:" + exp);
            } catch (IOException e) {
                exception = e;
                listener.newMessage("EXCEPTION");
            }
        }
    }

}
