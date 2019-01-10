package com.example.xavierdecazenove1.ass2.Connection;

import android.util.JsonWriter;

import org.json.JSONException;

import java.io.IOException;
import java.io.StringWriter;

public class Message{

    public static String registration(String group, String member) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter( stringWriter );
        try {
            writer.beginObject()
                    .name("type").value("register")
                    .name("group").value(group)
                    .name("member").value(member)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static String unregistration(String id) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter( stringWriter );
        try {
            writer.beginObject()
                    .name("type").value("unregister")
                    .name("id").value(id)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static String membersInAGroup(String group) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter( stringWriter );
        try {
            writer.beginObject()
                    .name("type").value("members")
                    .name("group").value(group)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }


    public static String currentGroups() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter(stringWriter);
        try{
            writer.beginObject()
                    .name("type").value("groups")
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public static String setPosition(String id, String latitude, String longitude) {
        StringWriter stringWriter = new StringWriter();
        JsonWriter writer = new JsonWriter( stringWriter );
        try {
            writer.beginObject()
                    .name("type").value("location")
                    .name("id").value(id)
                    .name("longitude").value(longitude)
                    .name("latitude").value(latitude)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }
}
