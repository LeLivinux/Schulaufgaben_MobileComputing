package ch.hftm.schulaufgaben;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class ServerSocketActivity extends AppCompatActivity {

    String ipAdr ="";

String message ="";
ServerSocket serverSocket;

    TextView info ;
    TextView msg ;

    public ServerSocketActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_socket);

         info = (TextView) findViewById(R.id.info);
         msg = (TextView) findViewById(R.id.message);

        try {
            Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
            while (nics.hasMoreElements()) {
                NetworkInterface nic = nics.nextElement();
                Enumeration<InetAddress> enumInetAddress = nic.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();
                    if (inetAddress.isSiteLocalAddress()) {
                        ipAdr += "IP: " +inetAddress.getHostAddress() + "\n";

                    } }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        Thread serverSocketThread = new Thread(new ServerSocketThread());
        serverSocketThread.start();


    }

    private class ServerSocketThread extends Thread { static final int PORT = 8080;
        int count = 0;
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(PORT); ServerSocketActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        info.setText("Warten auf Port: " + serverSocket.getLocalPort());
                    } });
                while (true) {
                    Socket socket = serverSocket.accept(); count++;
                    message += "Verbindungsnr." + count + " von IP " + socket.getInetAddress() + " und Port " +
                            socket.getPort() + "\n"; ServerSocketActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            msg.setText(message);
                        } });
                    ServerSocketReplyThread serverSocketReplyThread = new ServerSocketReplyThread(socket, count);
                    serverSocketReplyThread.run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerSocketReplyThread extends Thread {
        private Socket hostThreadSocket;
        int cnt;

        ServerSocketReplyThread(Socket socket, int c) {
            hostThreadSocket = socket;
            cnt = c;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            String msgReply = "Hallo, hier ist Ihr Android Device, Sie sind meine Nr. " + cnt;
            try {
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgReply);
                printStream.close();
                message += "Ich antwortete: " + msgReply + "\n";
                ServerSocketActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        msg.setText(message);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                message += "Fehler." + e.toString() + "\n";
            }
            ServerSocketActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    msg.setText(message);
                }
            });

        }
    }
}

