package com.vhn.app_music_vuhoaingoc;

public class Song {
    //Khai bao doi tuong cac bai hat
    private String Title;
    private int File;

    public Song(String title, int file) {
        Title = title;
        File = file;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }
}
