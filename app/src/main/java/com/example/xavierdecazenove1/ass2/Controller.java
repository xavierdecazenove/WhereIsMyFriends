package com.example.xavierdecazenove1.ass2;

import android.app.Application;
import android.util.Log;

import com.example.xavierdecazenove1.ass2.ClassStatic.Group;
import com.example.xavierdecazenove1.ass2.ClassStatic.User;
import com.example.xavierdecazenove1.ass2.Connection.Message;
import com.example.xavierdecazenove1.ass2.Connection.ReceiveListener;
import com.example.xavierdecazenove1.ass2.Connection.TCPConnection;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tsroax on 2014-09-30.
 */
public class Controller extends Application {

    public static final String TAG = "Controller";

   // private MainActivity activity;
    private TCPConnection connection;
    public boolean connected = false;
    public ReceiveListener listener;
    public ArrayList<String> currentGroups;
    public User user;
    public Group nameGroupCurrent;
    public boolean locationsReady = false;


    public Controller(){
        currentGroups = new ArrayList<>();
        user = new User("",null);
        nameGroupCurrent = new Group("","",new ArrayList<User>());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ///this.activity = activity;
        listener = new RL();
        connection = new TCPConnection("195.178.227.53",7117,listener);
        connectClicked();
    }

    public void connectClicked() {
        connection.connect();
    }

    public void disconnectClicked() {
        if(connected) {
            connection.disconnect();
        }
    }



    public void sendMessage(String message){
        connection.send(message);
    }


    public void creatUser(String name){
        user = new User(name,null);
    }


    private class RL implements ReceiveListener {
        public void newMessage(final String answer) {
            Thread thread = new MessageHandler(answer);
            thread.start();
        }
    }




    public class MessageHandler extends Thread{
        private String message;

        public MessageHandler(String message){
            this.message = message;
        }

        @Override
        public void run() {
            super.run();

            try {
                JSONObject jsonObject = new JSONObject(this.message);
                Log.d(TAG, "Received message of type: " + jsonObject.getString("type"));
                JSONArray jsonArray;
                String group, id;

                switch (jsonObject.getString("type")){
                    case "register":
                        group = jsonObject.getString("group");
                        id = jsonObject.getString("id");
                        nameGroupCurrent = new Group(group,id,new ArrayList<User>());
                        Log.d(TAG, "Received message of type: (Registration) = " + group + " / " + id);
                        connection.send(Message.membersInAGroup(nameGroupCurrent.getName()));
                        break;
                    case "unregister":
                        id = jsonObject.getString("id");
                        if (id.equals(nameGroupCurrent.getId())){
                            locationsReady = false;
                            Log.d(TAG, "Received message of type: (Unregistration) = " + id);
                        }
                        break;
                    case "members":
                        group = jsonObject.getString("group");
                        jsonArray = jsonObject.getJSONArray("members");
                        ArrayList<User> arrayListUser = new ArrayList<>();
                        for (int i=0; i<jsonArray.length(); ++i){
                            String name = jsonArray.getJSONObject(i).getString("member");
                            arrayListUser.add(new User(name,null));
                            Log.d(TAG, "Received message of Members : " + name);
                        }
                        if (nameGroupCurrent.getName().equals(group)) {
                            nameGroupCurrent.setUsers(arrayListUser);
                        }
                        break;
                    case "groups":
                        jsonArray = jsonObject.getJSONArray("groups");
                        for (int i=0; i<jsonArray.length(); ++i){
                            String name = jsonArray.getJSONObject(i).getString("group");
                            currentGroups.add(name);
                            Log.d(TAG, "Received message of type: (ShowGroups) = " + name);
                        }
                        break;
                    case "location":
                        id = jsonObject.getString("id");
                        String lg = jsonObject.getString("longitude");
                        String lt = jsonObject.getString("latitude");
                        LatLng localisation = new LatLng(Double.parseDouble(lt),Double.parseDouble(lg));
                        user.setCoordonnee(localisation);
                        Log.d(TAG, "Received message of type: (Location) = " + localisation.latitude + " / " + localisation.longitude);

                        break;
                    case "locations":
                        group = jsonObject.getString("group");
                        jsonArray = jsonObject.getJSONArray("location");
                        ArrayList<User> arrayListUser2 = new ArrayList<>();
                        for (int i=0; i<jsonArray.length(); ++i){
                            String name = jsonArray.getJSONObject(i).getString("member");
                            String lg2 = jsonArray.getJSONObject(i).getString("longitude");
                            String lt2 = jsonArray.getJSONObject(i).getString("latitude");
                            LatLng loc = new LatLng(Double.parseDouble(lt2),Double.parseDouble(lg2));
                            arrayListUser2.add(new User(name,loc));
                            Log.d(TAG, "run: LOCATIONS ALL = " + name + " / Lat : "
                                    + Double.toString(loc.latitude) + "/ Long : "
                                    + Double.toString(loc.longitude));
                        }
                        nameGroupCurrent.setName(group);
                        nameGroupCurrent.setUsers(arrayListUser2);
                        locationsReady = true;
                        break;

                    case "exception":
                        String exception = jsonObject.getString("message");
                        Log.d(TAG, "run: EXCEPTION = " + exception);
                        break;
                }

            }catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }


}
