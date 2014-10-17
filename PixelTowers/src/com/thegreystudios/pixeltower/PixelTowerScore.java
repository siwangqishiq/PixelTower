// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PixelTowerScore.java

package com.thegreystudios.pixeltower;


public class PixelTowerScore
{

    public PixelTowerScore(String name, double score, int rank)
    {
        this.name = name;
        this.score = score;
        this.rank = rank;
    }

    public void reset()
    {
        score = -1D;
        rank = -1;
    }

    public String name;
    public double score;
    public int rank;
}
