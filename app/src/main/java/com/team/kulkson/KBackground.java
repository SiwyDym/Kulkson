package com.team.kulkson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;


/**
 * Created by Marco on 2015-04-09.
 */
public class KBackground {

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureBuffer;
    private ByteBuffer indexBuffer;

    private int[] textures = new int[1];

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

    public KBackground(){
    //obiekty ByteBuffer-przechowuje wierzchołki i teksture w tablicach
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuf.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);

        indexBuffer = ByteBuffer.allocate(indices.length);
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }

//pobiera wskąźnik na obrazek, a następnie wczytuje go do strumienia, strumień zostaje wczytany jako tekstura przez OpenGL:
    public void loadTexture(GL10 gl, int texture, Context context){//wywołujemy ją raz, przy inicjalizacji gry

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
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);//mapowanie tła:przewijanie obrazka w nieskończoność w kierunku S
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);//mapowanie tła:przewijanie obrazka w nieskończoność w kierunku T
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0); //skojarzenie stworzonego strumienia bitmapowego z pierwszą teksturą
        bitmap.recycle();
    }
//nanosi teksturę na wierzchołki
    public void draw(GL10 gl){//wywołujemy ją zawsze jak chcemy wyświetlić tło

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
}
