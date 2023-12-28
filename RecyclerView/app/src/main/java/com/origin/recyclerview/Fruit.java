package com.origin.recyclerview;
public class Fruit {
    private  String name;
    private  int ImageId;

    public Fruit(String name, int imageId) {
        this.name = name;
        ImageId = imageId;
    }
    public String getName(){
        return  name;
    }
    public int getImageId()
    {
        return ImageId;
    }
}
