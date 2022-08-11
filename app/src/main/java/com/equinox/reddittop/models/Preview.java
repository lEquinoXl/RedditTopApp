package com.equinox.reddittop.models;

import java.util.ArrayList;

public class Preview{
    public ArrayList<Image> images;
    public boolean enabled;
}
class Image{
    public Source source;
    public ArrayList<Resolution> resolutions;
    public Variants variants;
    public String id;
}
class Source{
    public String url;
    public int width;
    public int height;
}
class Resolution{
    public String url;
    public int width;
    public int height;
}
class Variants{
}