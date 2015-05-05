package com.team.kulkson;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class KTextures
{

    private int[] textures=new int[2];


    //konstruktor przyjmujący instancje klasy GL10
    public KTextures(GL10 gl){
        gl.glGenTextures(2, textures,0);
    }


    public  int[] loadTexture(GL10 gl, int texture, Context context, int textureNumber){
    InputStream imagestream=context.getResources().openRawResource(texture);
        Bitmap bitmap=null;
        try{
            bitmap=BitmapFactory.decodeStream(imagestream);
        }catch(Exception e){

        }finally {
            //zawsze czyść i zamykaj
            try{
                imagestream.close();
                imagestream=null;
            }catch(IOException e){

            }
        }
         gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[textureNumber-1]);//wczytanie obrazka/tekstury

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);//mapowanie wierzchołków: szybkie

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);//mapowanie wierzchołków: wyostrzone piksele

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);//mapowanie tła:przewijanie obrazka w nieskończoność w kierunku S

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);//mapowanie tła:przewijanie obrazka w nieskończoność w kierunku T

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);//skojarzenie stworzonego strumienia bitmapowego z pierwszą teksturą

        bitmap.recycle();

        return  textures;// zwrócenie tablicy
    }


}
