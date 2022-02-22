package org.izv.di.acl.eljuegodelaspelotas;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameData implements Parcelable{
    private ArrayList<Integer> colors;
    public int[] ballsColors, ballsCount;
    public int totalNBalls;
    public int nColors;
    public int difficulty;

    public GameData (int difficulty){

        int[] arrayColors = new int[] {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA};
        colors = new ArrayList<>();
        for (int color : arrayColors) {
            colors.add(color);
        }

        this.difficulty = difficulty;
        nColors = ThreadLocalRandom.current().nextInt(2, (colors.size()));
        generateColors(nColors);
    }

    protected GameData(Parcel in) {
        ballsColors = in.createIntArray();
        ballsCount = in.createIntArray();
        totalNBalls = in.readInt();
        nColors = in.readInt();
        difficulty = in.readInt();
    }

    public static final Creator<GameData> CREATOR = new Creator<GameData>() {
        @Override
        public GameData createFromParcel(Parcel in) {
            return new GameData(in);
        }

        @Override
        public GameData[] newArray(int size) {
            return new GameData[size];
        }
    };

    private void generateColors(int nColors){
        if(nColors > colors.size())
            nColors = colors.size();

        ballsColors = new int[nColors];
        ballsCount = new int[nColors];

        for (int i = 0; i < nColors; i++){
            int colorIndice = ThreadLocalRandom.current().nextInt(0, colors.size());
            ballsColors[i] = colors.get(colorIndice);
            colors.remove(colorIndice);
            int nBalls = ThreadLocalRandom.current().nextInt(1, 7);
            ballsCount[i] = nBalls;
            totalNBalls += nBalls;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(ballsColors);
        dest.writeIntArray(ballsCount);
        dest.writeInt(totalNBalls);
        dest.writeInt(nColors);
        dest.writeInt(difficulty);
    }
}
