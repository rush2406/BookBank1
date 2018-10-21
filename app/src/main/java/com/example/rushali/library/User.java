package com.example.rushali.library;

import java.util.ArrayList;

/**
 * Created by rushali on 18/10/18.
 */

public class User {

    String name,id,email,dept,password;

    public String getPassword() {
        return password;
    }

    int fine;
    ArrayList<Integer> issue,reserved;

    public User(String name, String id, String email, String dept,String password) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.dept = dept;
        this.fine = 0;
        this.password=password;
        this.issue = new ArrayList<>();
        this.reserved = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDept() {
        return dept;
    }

    public int getFine() {
        return fine;
    }

    public ArrayList<Integer> getIssue() {
        return issue;
    }

    public ArrayList<Integer> getReserved() {
        return reserved;
    }
}
