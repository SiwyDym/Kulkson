package com.team.kulkson;

import android.opengl.GLSurfaceView.Renderer;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Marco on 2015-04-09.
 */
public class KGameRenderer implements Renderer {

    KGameView.KGameInterface kGameInterface;


    private KBackground background = new KBackground();

    private KGoodGuy player1 = new KGoodGuy();

    //zmienne dla przeciwników ...
    KEngine silnik = new KEngine();


    <<<<<<<Updated upstream

    private KEnemy1 enemy1= new KEnemy1(KEngine.TYPE_KOLCE);
    private KEnemy2 enemy2= new KEnemy2(KEngine.TYPE_BLOK);

    private long loopStart=0;
    private long loopEnd=0;
    private long loopRunTime=0;

int i=0;
    private Random randomPos=new Random();

//    private void initializeKolce()
//    {
        //dodawanie nowych przeciwników tego typu do tablicy

//            KEnemy kolce=new KEnemy(KEngine.TYPE_KOLCE);
//
//            enemies[0]=kolce;


//    }
//
//    private void initializeBlok()
//    {


//            KEnemy blok=new KEnemy(KEngine.TYPE_BLOK);
//            enemies[1]=blok;

   // }


    private  float bgScroll1;
    =======
    private KEnemy[] enemies = new KEnemy[2];
    //    private KEnemy1=new KEnemy1(1);
    //    private KEnemy2=new KEnemy2(2);
    private KEnemy1 enemy1 = new KEnemy1(KEngine.TYPE_KOLCE);
    private KEnemy2 enemy2 = new KEnemy2(KEngine.TYPE_BLOK);
    private KTextures textureLoader;
    private int[] spriteSheets1 = new int[1];
    private int[] spriteSheets2 = new int[1];
    private long loopStart = 0;
    private long loopEnd = 0;
    private long loopRunTime = 0;

    int i = 0;
    private Random randomPos = new Random();
    private float bgScroll1;

    public KGameRenderer(KGameView.KGameInterface kGameInterface) {
        this.kGameInterface = kGameInterface;
    }

    >>>>>>>
    Stashed changes


    @Override
    public void onDrawFrame(GL10 gl) { //dla wyświetlenia nowej ramki
        loopStart = System.currentTimeMillis();
        try { //dzięki tej funkcji kod pod nią wykonuje się tylko 60 razy na sekundę
            if (loopRunTime < KEngine.GAME_THREA_FPS_SLEEP) {
                Thread.sleep(KEngine.GAME_THREA_FPS_SLEEP - loopRunTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//czyszczenie buforów OpenGL

        scrollBackground1(gl);

        movePlayer(gl);
        moveEnemy(gl);
        i++;
        if(i%300==0)
        {
            KEngine.PLAYER_BANK_SPEED2 = (float) (KEngine.PLAYER_BANK_SPEED2+0.007);
            KEngine.PLAYER_BANK_SPEED= (float) (KEngine.PLAYER_BANK_SPEED+0.006);
            KEngine.KOLCE_SPEED= (float) (KEngine.KOLCE_SPEED+0.002);
            KEngine.BLOK_SPEED= (float) (KEngine.BLOK_SPEED+0.002);
        }

        <<<<<<<Updated upstream


        =======
        i++;

    >>>>>>>
    Stashed changes
        //metody rysujące grtr TUTAJ
        detectCollisions();
        gl.glEnable(GL10.GL_BLEND); //blendowanie przezroczystości
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
    loopEnd=System.currentTimeMillis();
    loopRunTime=((loopEnd-loopStart));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) { //podczas zmiany widoku
        //konfigurowanie renderowania i wyświetlania gry
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION);//daje dostęp do sposobu renderowania sceny
        gl.glLoadIdentity(); //resetowanie macierzy
        gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) { //gdy tworzony jest obiekt GLSurface
        //inicjalizujemy środowisko OpenGl i wcywujemy tekstury
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        //przenikanie:
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        background.loadTexture(gl, KEngine.BACKGROUND_LAYER_ONE, KEngine.context);

        player1.loadTexture(gl, KEngine.BALL, KEngine.context); //wczytanie tekstury kulki
        //textureLoader=new KTextures(gl);
        enemy1.loadTexture(gl, KEngine.CHARACTER_SHEET1, KEngine.context);//wczytanie tekstury kolce

        //textureLoader=new KTextures(gl);
        enemy2.loadTexture(gl, KEngine.CHARACTER_SHEET2, KEngine.context);//wczytanie tekstury blok
    }

    //przewijanie pierwszego tła
    private void scrollBackground1(GL10 gl) {

        if (bgScroll1 == Float.MAX_VALUE) {
            bgScroll1 = 0f;
        }//zabezpieczenie przed przekroczeniem wartości

        //poniższy kod resetuje skale i przesuniecia trybu macierzy modelu
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity(); // resetowanie macierzy
        gl.glPushMatrix();
        gl.glScalef(1f, 1f, 1f);
        gl.glTranslatef(0f, 0f, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();

        gl.glTranslatef(bgScroll1, 0.0f, 0.0f);  //przewijanie tekstury
        background.draw(gl); //rysowanie tła
        gl.glPopMatrix();    //odłożenie macierzy na stos
        bgScroll1 += KEngine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }


    private void movePlayer(GL10 gl) {  //poruszanie postacią gracza
        if (KEngine.boolflag1 == true) {
            KEngine.playerFlightAction = 0;
        }


        switch (KEngine.playerFlightAction) {
            case KEngine.PLAYER_BANK_JUMP:
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
                gl.glPushMatrix();
                gl.glScalef(.1f, .1f, 1f);

                <<<<<<<Updated upstream


                =======
                >>>>>>>Stashed changes

                if (KEngine.boolflag == false) {
                    KEngine.playerBankPosY += KEngine.PLAYER_BANK_SPEED;

                    <<<<<<<Updated upstream
                        if(KEngine.playerBankPosY>6f){
                            KEngine.boolflag=true;
                        }
                        gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                    =======
                    >>>>>>>Stashed changes

                    if (i % 900 == 0) {
                        KEngine.PLAYER_BANK_SPEED = (float) (KEngine.PLAYER_BANK_SPEED * 1.7);
                        KEngine.playerBankPosY += KEngine.PLAYER_BANK_SPEED;
                    }


                    if (KEngine.playerBankPosY > 6f) {
                        KEngine.boolflag = true;
                    }
                    <<<<<<<Updated upstream
                    else{
                         if((enemy2.posX < (2f) )&&(enemy2.posX > (0.43f))) {

                             if (KEngine.playerBankPosY > 1.6f) {

                                KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;

                                 gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                                 =======
                                 gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);


                             } else {
                                 if ((enemy2.posX <= (2.67f)) && (enemy2.posX > (1.1f))) {

                                     if (KEngine.playerBankPosY > 1.5f) {
                                         >>>>>>>Stashed changes

                                         KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                                         if (i % 900 == 0) {

                                             <<<<<<<Updated upstream
                                 gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                                             =======
                                             KEngine.PLAYER_BANK_SPEED2 = (float) (KEngine.PLAYER_BANK_SPEED2 * 1.7);
                                             KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                                         }
                                         gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                                         >>>>>>>Stashed changes

                                     } else {

                                         gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);

                                         KEngine.playerFlightAction = 0;
                                         KEngine.boolflag = false;

                                         <<<<<<<Updated upstream
                             if (KEngine.playerBankPosY > 0.1f) {
                                 KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;



                                 gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                             }
                                         =======
                                     }
                                 } else {

                                     if (KEngine.playerBankPosY > 0) {
                                         KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;

                                         if (i % 900 == 0) {
                                             KEngine.PLAYER_BANK_SPEED2 = (float) (KEngine.PLAYER_BANK_SPEED2 * 1.7);
                                             KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                                         }
                                         >>>>>>>Stashed changes

                                         gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                                     } else {

                                         <<<<<<<Updated upstream
                                 gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                                 KEngine.playerFlightAction = 0;
                                 KEngine.boolflag = false;


                             }
                                     =======
                                     gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                                     KEngine.playerFlightAction = 0;
                                     KEngine.boolflag = false;

                                 }
                                 >>>>>>>Stashed changes

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
                            gl.glScalef(.1f, .1f, 1f);
//
                            <<<<<<<Updated upstream
                    if((enemy2.posX < (0.43))&&KEngine.playerBankPosY>0.1f){

                        =======
                        if ((enemy2.posX < (1.1)) && KEngine.playerBankPosY > 0) {
                            >>>>>>>Stashed changes


                            <<<<<<<Updated upstream


                            gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                        KEngine.playerFlightAction=0;
                        KEngine.boolflag1=true;
                    }
                    else {
                        gl.glTranslatef(2f, KEngine.playerBankPosY, 0f);
                        KEngine.boolflag1=false;

                            =======
                            KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;

                            if (i % 900 == 0) {
                                KEngine.PLAYER_BANK_SPEED2 = (float) (KEngine.PLAYER_BANK_SPEED2 * 1.7);
                                KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                                >>>>>>>Stashed changes
                    }

                            gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                            KEngine.playerFlightAction = 0;
                            KEngine.boolflag1 = true;
                        }else{
                            gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                            KEngine.boolflag1 = false;
                        }


                        player1.draw(gl);
                        gl.glPopMatrix();
                        gl.glLoadIdentity();
                        break;
        }
                    }


                private void moveEnemy (GL10 gl){


                if (enemy1.posX < -1) {
                    MainActivity.aktualny += 2;
                    if (MainActivity.aktualny > MainActivity.najlepszy) {
                        MainActivity.najlepszy = MainActivity.aktualny;
            }
                    enemy1.posX = (randomPos.nextFloat() * 7) + 7;
                    if (enemy1.posX < (enemy2.posX + 3) && enemy2.posX > (enemy2.posX) - 3) {
                        enemy1.posX += 4;
            }
        }

                if (enemy1.posX >= -1) {
                    enemy1.posX -= KEngine.KOLCE_SPEED;

                    if (i % 900 == 0) {

                        <<<<<<<Updated upstream


        if(enemy1.posX>=-1)
        {
            enemy1.posX-=KEngine.KOLCE_SPEED;


            =======
            KEngine.KOLCE_SPEED = (float) (KEngine.KOLCE_SPEED * 1.2);
            enemy1.posX -= KEngine.KOLCE_SPEED;
        }
                        >>>>>>>Stashed changes
        }


        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.15f, .15f, 1f);

        gl.glTranslatef(enemy1.posX, 0f, 0f);


        enemy1.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();


                    if (enemy2.posX < -1) {
                        MainActivity.aktualny += 1;
                        if (MainActivity.aktualny > MainActivity.najlepszy) {
                            MainActivity.najlepszy = MainActivity.aktualny;
            }
                        enemy2.posX = (randomPos.nextFloat() * 7) + 7;

                        if (enemy2.posX < (enemy1.posX + 3) && enemy2.posX > (enemy1.posX) - 3) {
                            enemy2.posX += 6;
                        }
        }

                    if (enemy2.posX >= -1) {
                        enemy2.posX -= KEngine.BLOK_SPEED;

                        <<<<<<<Updated upstream


        if(enemy2.posX>=-1)
        {

            enemy2.posX-=KEngine.BLOK_SPEED;


            =======
            if (i % 900 == 0) {
                KEngine.BLOK_SPEED = (float) (KEngine.BLOK_SPEED * 1.2);
                enemy2.posX -= KEngine.BLOK_SPEED;
            }
            >>>>>>>Stashed changes
        }
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.15f, .15f, 1f);

        gl.glTranslatef(enemy2.posX, 0f, 0f);

        enemy2.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();

    }

    //sprawdzenie kolizji jeśli przeszkoda jest na pozycji Kulksona, to kolizja
                    <<<<<<<Updated upstream
    private void detectCollisions(){

           if((enemy1.posX < (2f) )&& (KEngine.playerBankPosY <= 1.3f)&&(enemy1.posX > (0.43f))) {
               enemy1.posX = (randomPos.nextFloat() * 7) + 7;
               //show.alert
           }
            if((enemy2.posX < (2f) )&& (KEngine.playerBankPosY <= 1.3f)&&(enemy2.posX > (0.43f))){
                   enemy2.posX = (randomPos.nextFloat() * 7) + 7;
               }

                        =======
                        public void detectCollisions () {
                            if ((enemy1.posX <= (2.67f)) && (KEngine.playerBankPosY <= 1.4f) && (enemy1.posX > (1.1f))) {
                                enemy1.posX = (randomPos.nextFloat() * 7) + 7;
                                >>>>>>>Stashed changes

                                kGameInterface.onColisionDetection();

                            }
                            if ((enemy2.posX <= (2.67f)) && (KEngine.playerBankPosY <= 1.4f) && (enemy2.posX > (1.1f))) {
                                enemy2.posX = (randomPos.nextFloat() * 7) + 7;
                                kGameInterface.onColisionDetection();
                            }

                        }

}







