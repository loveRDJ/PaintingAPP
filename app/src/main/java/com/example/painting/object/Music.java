package com.example.painting.object;

public class Music {
    private String title;
    private String singer;
    private int image;

    public Music (String name,String singer, int id){
        this.title=name;
        this.singer=singer;
        this.image=id;
    }

    public String getTitle() {
        return title;
    }

    public String getSinger() {
        return singer;
    }

    public int getImage() {
        return image;
    }
}
