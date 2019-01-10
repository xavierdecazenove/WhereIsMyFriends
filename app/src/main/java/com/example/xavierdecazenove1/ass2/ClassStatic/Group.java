package com.example.xavierdecazenove1.ass2.ClassStatic;

import java.util.List;

public class Group {

    private String name;
    private String id;
    private List<User> users;

    public Group(String name, String id, List<User> users){
        this.name = name;
        this.id = id;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
