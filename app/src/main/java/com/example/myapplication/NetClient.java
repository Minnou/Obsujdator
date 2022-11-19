package com.example.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetClient {

    /**
     * Maximum size of buffer
     */
    public static final int BUFFER_SIZE = 2048;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String host;
    private int port;


    /**
     * Constructor with Host, Port
     * @param host
     * @param port
     */
    public NetClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connectWithServer() {
        try {
            if (socket == null) {
                socket = new Socket(this.host, this.port);
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disConnectWithServer() {
        if (socket != null) {
            if (socket.isConnected()) {
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendDataWithString(String message) {
        if (message != null) {
            connectWithServer();
            out.write(message);
            out.flush();
        }
    }

    public void postReply(String id, String title, String text){
        int charsRead = 0;
        id +='\0';
        title += '\0';
        text += '\0';
        char[] buffer = new char[BUFFER_SIZE];
        String str = "";
        sendDataWithString("post_reply");
        try{
            while (!in.ready()) {
            }
            while ((charsRead = in.read(buffer)) != -1) {
                str += new String(buffer).substring(0, charsRead);
                if (!in.ready()) {
                    break;
                }
            }
        }catch (IOException e) {
            return;
        }
        if (str.equals("go")){
            sendDataWithString(id);
            str = "";
            try{
                while (!in.ready()) {
                }
                while ((charsRead = in.read(buffer)) != -1) {
                    str += new String(buffer).substring(0, charsRead);
                    if (!in.ready()) {
                        break;
                    }
                }
            }catch (IOException e) {
                return;
            }
            if (str.equals("go")){
                sendDataWithString(title);
                str = "";
                try{
                    while (!in.ready()) {
                    }
                    while ((charsRead = in.read(buffer)) != -1) {
                        str += new String(buffer).substring(0, charsRead);
                        if (!in.ready()) {
                            break;
                        }
                    }
                }catch (IOException e) {
                    return;
                }
                if (str.equals("go")){
                    sendDataWithString(text);
                }
            }
        }

    }

    public void postDiscussion(String title, String text){
        int charsRead = 0;
        title += '\0';
        text += '\0';
        char[] buffer = new char[BUFFER_SIZE];
        String str = "";
        sendDataWithString("post_disc");
        try{
            while (!in.ready()) {
            }
            while ((charsRead = in.read(buffer)) != -1) {
                str += new String(buffer).substring(0, charsRead);
                if (!in.ready()) {
                    break;
                }
            }
        }catch (IOException e) {
            return;
        }
        if (str.equals("go")){
            sendDataWithString(title);
            str = "";
            try{
                while (!in.ready()) {
                }
                while ((charsRead = in.read(buffer)) != -1) {
                    str += new String(buffer).substring(0, charsRead);
                    if (!in.ready()) {
                        break;
                    }
                }
            }catch (IOException e) {
                return;
            }
            if (str.equals("go")){
                sendDataWithString(text);
            }
        }

    }
    public ArrayList<DiscussionClass> receiveDiscussionsFromServer() {
        String[] ids = new String[1];
        String[] titles = new String[1];
        String[] texts = new String[1];
        try {
            int j =0;
            while (j < 3) {
                String str = "";
                int charsRead = 0;
                char[] buffer = new char[BUFFER_SIZE];
                int fields = 0;
                if (j ==0)
                    sendDataWithString("get_disc");
                else
                    sendDataWithString("go");
                while (!in.ready()) {
                }
                while ((charsRead = in.read(buffer)) != -1) {
                    fields += Integer.parseInt(new String(buffer).substring(0, charsRead));
                    if (!in.ready()) {
                        break;
                    }
                }
                String[] data = new String[fields];
                sendDataWithString("go");
                for (int i = 0; i < fields; i++) {
                    while (!in.ready()) {
                    }
                    while ((charsRead = in.read(buffer)) != -1) {
                        str += new String(buffer).substring(0, charsRead);
                        if (!in.ready()) {
                            break;
                        }
                    }
                    Log.d("JIEST", "Получены данные:" + str);
                    data[i] = str;
                    str = "";
                    sendDataWithString("go");
                }
                switch (j){
                    case 0:
                        ids = data.clone();
                        break;
                    case 1:
                        titles = data.clone();
                        break;
                    case 2:
                        texts = data.clone();
                }
                j++;
            }
            disConnectWithServer(); // disconnect server
            ArrayList<DiscussionClass> discussions = new ArrayList<>();
            for (int i = 0; i< ids.length; i++)
                discussions.add(new DiscussionClass(ids[i],titles[i],texts[i]));
            Log.d("JIEST", "Размер списка:" + discussions.size());
            return discussions;
        } catch (IOException e) {
            return null;
        }
    }

    public ArrayList<ReplyClass> receiveRepliesFromServer(String id) {
        String[] ids = new String[1];
        String[] titles = new String[1];
        String[] texts = new String[1];
        try {
            int j =0;
            while (j < 3) {
                String str = "";
                int charsRead = 0;
                char[] buffer = new char[BUFFER_SIZE];
                int fields = 0;
                if (j ==0)
                {
                    sendDataWithString("get_replies");
                    try{
                        while (!in.ready()) {
                        }
                        while ((charsRead = in.read(buffer)) != -1) {
                            str += new String(buffer).substring(0, charsRead);
                            if (!in.ready()) {
                                break;
                            }
                        }
                    }catch (IOException e) {
                        return null;
                    }
                    if (str.equals("go")) {
                        sendDataWithString(id+'\0');
                        str = "";
                    }
                }
                else
                    sendDataWithString("go");
                while (!in.ready()) {
                }
                while ((charsRead = in.read(buffer)) != -1) {
                    fields += Integer.parseInt(new String(buffer).substring(0, charsRead));
                    if (!in.ready()) {
                        break;
                    }
                }
                String[] data = new String[fields];
                sendDataWithString("go");
                for (int i = 0; i < fields; i++) {
                    while (!in.ready()) {
                    }
                    while ((charsRead = in.read(buffer)) != -1) {
                        str += new String(buffer).substring(0, charsRead);
                        if (!in.ready()) {
                            break;
                        }
                    }
                    Log.d("JIEST", "Получены данные:" + str);
                    data[i] = str;
                    str = "";
                    sendDataWithString("go");
                }
                switch (j){
                    case 0:
                        ids = data.clone();
                        break;
                    case 1:
                        titles = data.clone();
                        break;
                    case 2:
                        texts = data.clone();
                }
                j++;
            }
            disConnectWithServer(); // disconnect server
            ArrayList<ReplyClass> replies = new ArrayList<>();
            for (int i = 0; i< ids.length; i++)
                replies.add(new ReplyClass(ids[i],titles[i],texts[i]));
            Log.d("JIEST", "Размер списка:" + replies.size());
            return replies;
        } catch (IOException e) {
            return null;
        }
    }


}