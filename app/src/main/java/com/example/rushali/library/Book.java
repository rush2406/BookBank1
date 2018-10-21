package com.example.rushali.library;

/**
 * Created by rushali on 11/10/18.
 */

public class Book {
    String name,author,publisher,id;
    int curquantity,totalquantity;
    String reserveid;
    String depttags;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getId() {
        return id;
    }

    public int getCurquantity() {
        return curquantity;
    }

    public int getTotalquantity() {
        return totalquantity;
    }

    public String getReserveid() {
        return reserveid;
    }

    public String getDepttags() {
        return depttags;
    }

    public Book(String name, String author, String publisher, String id, int totalquantity, int curquantity, String reserveid, String depttags) {

        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.id = id;
        this.curquantity = curquantity;
        this.totalquantity = totalquantity;
        this.reserveid = reserveid;
        this.depttags = depttags;
    }

    public void setCurquantity(int curquantity) {
        this.curquantity = curquantity;
    }

    public void setResquantity(int resquantity) {
        this.totalquantity = resquantity;
    }

    }
