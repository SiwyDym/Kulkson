package com.team.kulkson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Dawid on 2015-06-04.
 */
public class KEnemy1 {
    public  float posX=0f;// współrzędna x wroga
    //public  float posY=0f;// współrzędna y wroga

    public int enemyType=0;// jakiego typu jest wróg




    public Random randomPos=new Random();

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private ByteBuffer indexBuffer;
    private int[] textures=new int[1];

    private  float vertices[] = {
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
    };
    private  float texture[] = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
            0.0f, 1.0f,
    };
    private  byte indices[] = {
            0, 1, 2,
            0, 2, 3
    };
    public KEnemy1(int type)
    {
        enemyType=type;
        posX=(randomPos.nextFloat()*4)+4;

        ByteBuffer byteBuf=ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer=byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        byteBuf=ByteBuffer.allocateDirect(texture.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer=byteBuf.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

        indexBuffer=ByteBuffer.allocateDirect(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);



    }

    public void draw(GL10 gl){
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);//wczytanie tekstury
        gl.glFrontFace(GL10.GL_CCW);    //usuwa niewidoczne dla user powierzchnie
        gl.glEnable(GL10.GL_CULL_FACE); //usuwa niewidoczne dla user powierzchnie
        gl.glCullFace(GL10.GL_BACK);    //usuwa niewidoczne dla user powierzchnie
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);           //odblokowuje stan wierzchołków
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);    //odblokowuje stan tekstur
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);  //wczytuje buf wierzchołków
        gl.glTexCoordPointer(2,GL10.GL_FLOAT, 0, textureBuffer);//wczytuje buf tekstur
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,GL10.GL_UNSIGNED_BYTE, indexBuffer); //nałożenie tekstury na wierzchołki
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);          //odblokowane wierzchołki zostają zablokowane
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);   //odblokowane tekstruy zostają zablokowane
        gl.glDisable(GL10.GL_CULL_FACE);
    }
    public void loadTexture(GL10 gl,int texture, Context context){
        InputStream imagestream = context.getResources().openRawResource(texture);
        Bitmap bitmap = null;
        try{
            bitmap = BitmapFactory.decodeStream(imagestream);
        }catch (Exception e){
        }finally {
            //zawsze czyść i zamykaj
            try {
                imagestream.close();
                imagestream = null;
            } catch (IOException e) {
            }
        }
        gl.glGenTextures(1,textures,0); // wskanik na teksture
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textures[0]);//wczytanie obrazka/tekstury
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);//mapowanie wierzchołków: szybkie
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);//mapowanie wierzchołków: wyostrzone piksele
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);//mapowanie tła:przewijanie obrazka w nieskończoność w kierunku S
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);//mapowanie tła:przewijanie obrazka w nieskończoność w kierunku T
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0); //skojarzenie stworzonego strumienia bitmapowego z pierwszą teksturą
        bitmap.recycle();
    }
}
