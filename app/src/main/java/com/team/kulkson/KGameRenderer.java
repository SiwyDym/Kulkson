package com.team.kulkson;

import android.opengl.GLSurfaceView.Renderer;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Marco on 2015-04-09.
 */
public class KGameRenderer implements Renderer {


    private KBackground background = new KBackground();
    //private KBackground background2 = new KBackground();//nowa instancja klasy KGameRenderer
    private KGoodGuy player1=new KGoodGuy();

    //zmienne dla przeciwników ...



    private KEnemy[] enemies=new KEnemy[2];
    private KTextures textureLoader;
    private int[] spriteSheets1=new int[1];
    private int[] spriteSheets2=new int[1];



    private Random randomPos=new Random();
    //metody dla trzech przeciwników
    private void initializeKolce()
    {
        //dodawanie nowych przeciwników tego typu do tablicy

            KEnemy kolce=new KEnemy(KEngine.TYPE_KOLCE);
            //KEnemy kolce=new KEnemy(KEngine.TYPE_KOLCE, KEngine.ATTACK_RIGHT);
            enemies[0]=kolce;

    }

    private void initializeBlok()
    {

           // KEnemy blok=new KEnemy(KEngine.TYPE_BLOK,KEngine.ATTACK_RIGHT);
            KEnemy blok=new KEnemy(KEngine.TYPE_BLOK);
            enemies[1]=blok;

    }


    private  float bgScroll1;
    //private  float bgScroll2;

    @Override
    public void onDrawFrame(GL10 gl){ //dla wyświetlenia nowej ramki
        try{ //dzięki tej funkcji kod pod nią wykonuje się tylko 60 razy na sekundę
            Thread.sleep(KEngine.GAME_THREA_FPS_SLEEP);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//czyszczenie buforów OpenGL

        scrollBackground1(gl);
        //scrollBackground2(gl);
        movePlayer(gl);
        moveEnemy(gl);
        //metody rysujące grtr TUTAJ

        gl.glEnable(GL10.GL_BLEND); //blendowanie przezroczystości
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height){ //podczas zmiany widoku
     //konfigurowanie renderowania i wyświetlania gry
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);//daje dostęp do sposobu renderowania sceny
        gl.glLoadIdentity(); //resetowanie macierzy
        gl.glOrthof(0f,1f,0f,1f,-1f,1f);








    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){ //gdy tworzony jest obiekt GLSurface
        //inicjalizacja wrogów
        initializeKolce();
        initializeBlok();

    //inicjalizujemy środowisko OpenGl i wcywujemy tekstury


        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

    //przenikanie:
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        background.loadTexture(gl, KEngine.BACKGROUND_LAYER_ONE,KEngine.context);
       // background2.loadTexture(gl, KEngine.BACKGROUND_LAYER_TWO,KEngine.context);//wczytanie tekstury do background2
        player1.loadTexture(gl,KEngine.BALL,KEngine.context); //wczytanie tekstury kulki
        textureLoader=new KTextures(gl);
        spriteSheets1=textureLoader.loadTexture(gl, KEngine.CHARACTER_SHEET1, KEngine.context, 1);//wczytanie tekstury kolce

        textureLoader=new KTextures(gl);
       spriteSheets2=textureLoader.loadTexture(gl, KEngine.CHARACTER_SHEET2, KEngine.context, 1);//wczytanie tekstury blok
    }
//przewijanie pierwszego tła
    private void scrollBackground1(GL10 gl){

        if(bgScroll1 == Float.MAX_VALUE){ bgScroll1= 0f;}//zabezpieczenie przed przekroczeniem wartości

        //poniższy kod resetuje skale i przesuniecia trybu macierzy modelu
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity(); // resetowanie macierzy
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
       // gl.glTranslatef(0.0f, bgScroll1, 0.0f); //przewijanie tekstury
        gl.glTranslatef(bgScroll1, 0.0f, 0.0f);
        background.draw(gl); //rysowanie tła
        gl.glPopMatrix();    //odłożenie macierzy na stos
        bgScroll1 += KEngine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }

        private void movePlayer(GL10 gl){  //poruszanie postacią gracza
            switch(KEngine.playerFlightAction){
                case KEngine.PLAYER_BANK_JUMP:
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    gl.glScalef(.25f,.25f,1f);


                    if(KEngine.boolflag==false) {
                        KEngine.playerBankPosY += KEngine.PLAYER_BANK_SPEED;
                        if(KEngine.playerBankPosY>1.75f){
                            KEngine.boolflag=true;
                        }
                        gl.glTranslatef(1.75f, KEngine.playerBankPosY, 0f);



                    }
                    else{
                        if(KEngine.playerBankPosY>0) {
                            KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                            gl.glTranslatef(1.75f, KEngine.playerBankPosY, 0f);
                        }
                        else{
                            gl.glTranslatef(1.75f, KEngine.playerBankPosY, 0f);
                            KEngine.playerFlightAction=0;
                            KEngine.boolflag=false;

                        }


                    }



                    player1.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();

                    break;

                default:
                    gl.glMatrixMode(GL10.GL_MODELVIEW); //przeskalowanie postaci gracza
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    gl.glScalef(.25f,.25f,1f);
                    gl.glTranslatef(1.75f,KEngine.playerBankPosY,0f);

                    player1.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();
                    break;
            }
        }




    private void moveEnemy(GL10 gl){



        if(enemies[0].posX<-1)
        {

            enemies[0].posX=(randomPos.nextFloat()*4)+4;
            //if(enemies[0].posX < (enemies[1].posX+4) &&enemies[0].posX  >(enemies[1].posX)-4 ) {
            //    enemies[0].posX+=4;
            //}
            //enemies[x].posX=15;
            // enemies[x].isLockedOn=false;
            //enemies[x].posT=KEngine.BLOK_SPEED;
            //enemies[x].lockOnPosX=enemies[x].getNextScoutX();
            // enemies[x].lockOnPosY=enemies[x].getNextScoutY();
            //enemies[x].posY=3;


        }

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.25f, .25f, 1f);



        if(enemies[0].posX>=-1)
        {
            enemies[0].posX-=KEngine.KOLCE_SPEED;
        }
                       /* else
                        {
                            enemies[x].posX=enemies[x].getNextScoutX();
                            enemies[x].posY=enemies[x].getNextScoutY();

                        }*/

        gl.glTranslatef(enemies[0].posX, 0f, 0f);
        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.25f, .25f, 0.0f);
        enemies[0].draw(gl, spriteSheets1);
        gl.glPopMatrix();
        gl.glLoadIdentity();







        if(enemies[1].posX<-1)
        {

            enemies[1].posX=(randomPos.nextFloat()*5)+5;
            // enemies[x].posX=5;
                         /*   enemies[x].isLockedOn=false;
                            enemies[x].posT=KEngine.BLOK_SPEED;
                            enemies[x].lockOnPosX=enemies[x].getNextScoutX();
                            enemies[x].lockOnPosY=enemies[x].getNextScoutY();
                            enemies[x].posY=3; */
            // if(enemies[1].posX < (enemies[0].posX+4) &&enemies[1].posX  >(enemies[0].posX)-4 ) {
            //     enemies[1].posX+=4;
            // }
        }

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        //gl.glScalef(1f, 1f, 0.25f);
        gl.glScalef(.2f, .2f, 2f);


        if(enemies[1].posX>=-1)
        {
            enemies[1].posX-=KEngine.BLOK_SPEED;
        }

                    /*    else
                        {
                            enemies[x].posX=enemies[x].getNextScoutX();
                            enemies[x].posY=enemies[x].getNextScoutY();

                        } */

        gl.glTranslatef(enemies[1].posX, 0f, 0f);
        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        gl.glTranslatef(0.75f, 0.25f, 0.0f);
        enemies[1].draw(gl, spriteSheets2);
        gl.glPopMatrix();
        gl.glLoadIdentity();





    }
    //private void detectCollisions(){
    //    for(int y=0;y<2;y++){
    //        if((enemies[y]).posX)
    //    }
   // }

}







