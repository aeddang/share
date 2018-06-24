package com.ironraft.bitboy.model.bitdata;


import java.util.ArrayList;

public class BitGraphInfo extends Object
{
    public long top,bottom;
    public int topIdx,bottomIdx;
    public ArrayList<BitGraphObject> graphs;
    public BitObject setting;

    public BitGraphInfo()
    {
        top = 0;
        bottom = 1000000000;
        topIdx = 0;
        bottomIdx = 0;
        graphs = new ArrayList<BitGraphObject>();

    }

}






