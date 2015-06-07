package com.team.kulkson;

import android.app.AlertDialog;
import android.opengl.GLSurfaceView.Renderer;
import android.view.View;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * Created by Marco on 2015-04-09.
 */
public class KGameRenderer implements Renderer {


    private KBackground background = new KBackground();

    private KGoodGuy player1=new KGoodGuy();

    //zmienne dla przeciwników ...
    KEngine silnik=new KEngine();


    private KEnemy[] enemies=new KEnemy[2];
//    private KEnemy1=new KEnemy1(1);
//    private KEnemy2=new KEnemy2(2);
    private KEnemy1 enemy1= new KEnemy1(KEngine.TYPE_KOLCE);
    private KEnemy2 enemy2= new KEnemy2(KEngine.TYPE_BLOK);
    private KTextures textureLoader;
    private int[] spriteSheets1=new int[1];
    private int[] spriteSheets2=new int[1];
    private long loopStart=0;
    private long loopEnd=0;
    private long loopRunTime=0;


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


    @Override
    public void onDrawFrame(GL10 gl){ //dla wyświetlenia nowej ramki
        loopStart=System.currentTimeMillis();
        try{ //dzięki tej funkcji kod pod nią wykonuje się tylko 60 razy na sekundę
            if(loopRunTime<KEngine.GAME_THREA_FPS_SLEEP){
                Thread.sleep(KEngine.GAME_THREA_FPS_SLEEP-loopRunTime);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//czyszczenie buforów OpenGL

        scrollBackground1(gl);

        movePlayer(gl);
        moveEnemy(gl);
        //metody rysujące grtr TUTAJ
        detectCollisions();
        gl.glEnable(GL10.GL_BLEND); //blendowanie przezroczystości
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
        loopEnd=System.currentTimeMillis();
        loopRunTime=((loopEnd-loopStart));
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
       // initializeKolce();
        //initializeBlok();

    //inicjalizujemy środowisko OpenGl i wcywujemy tekstury


        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

    //przenikanie:
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        background.loadTexture(gl, KEngine.BACKGROUND_LAYER_ONE,KEngine.context);

        player1.loadTexture(gl,KEngine.BALL,KEngine.context); //wczytanie tekstury kulki
        //textureLoader=new KTextures(gl);
        enemy1.loadTexture(gl, KEngine.CHARACTER_SHEET1, KEngine.context);//wczytanie tekstury kolce

        //textureLoader=new KTextures(gl);
       enemy2.loadTexture(gl, KEngine.CHARACTER_SHEET2, KEngine.context);//wczytanie tekstury blok
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

        gl.glTranslatef(bgScroll1, 0.0f, 0.0f);  //przewijanie tekstury
        background.draw(gl); //rysowanie tła
        gl.glPopMatrix();    //odłożenie macierzy na stos
        bgScroll1 += KEngine.SCROLL_BACKGROUND_1;
        gl.glLoadIdentity();
    }

        private void movePlayer(GL10 gl){  //poruszanie postacią gracza
            if(KEngine.boolflag1==true){
                KEngine.playerFlightAction=0;
            }


            switch(KEngine.playerFlightAction){
                case KEngine.PLAYER_BANK_JUMP:
                    gl.glMatrixMode(GL10.GL_MODELVIEW);
                    gl.glLoadIdentity();
                    gl.glPushMatrix();
                    gl.glScalef(.1f,.1f,1f);


                    if(KEngine.boolflag==false) {
                        KEngine.playerBankPosY += KEngine.PLAYER_BANK_SPEED;
                        if(KEngine.playerBankPosY>6f){
                            KEngine.boolflag=true;
                        }
                        gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);



                    }
                    else{
                         if((enemy2.posX <= (2.67f) )&&(enemy2.posX > (1.1f))) {

                             if (KEngine.playerBankPosY > 1.5f) {

                                 KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                                 gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);

                             }
                             else {

                                 gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);

                                 KEngine.playerFlightAction = 0;
                                 KEngine.boolflag = false;

                             }
                         }

                             else {

                             if (KEngine.playerBankPosY > 0) {
                                 KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                                 gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                             }

                             else {

                                 gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                                 KEngine.playerFlightAction = 0;
                                 KEngine.boolflag = false;

                             }

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
                    gl.glScalef(.1f,.1f,1f);
//
                    if((enemy2.posX < (1.1))&&KEngine.playerBankPosY>0){
                        KEngine.playerBankPosY -= KEngine.PLAYER_BANK_SPEED2;
                            gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                        KEngine.playerFlightAction=0;
                        KEngine.boolflag1=true;
                    }
                    else {
                        gl.glTranslatef(3f, KEngine.playerBankPosY, 0f);
                        KEngine.boolflag1=false;
                    }


                    player1.draw(gl);
                    gl.glPopMatrix();
                    gl.glLoadIdentity();
                    break;
            }
        }




    private void moveEnemy(GL10 gl){



        if(enemy1.posX<-1)
        {
            KEngine.wynik+=2;
            enemy1.posX=(randomPos.nextFloat()*7)+7;
            if(enemy1.posX < (enemy2.posX+3) &&enemy2.posX  >(enemy2.posX)-3 ) {
                enemy1.posX+=4;
            }



        }





        if(enemy1.posX>=-1)
        {
            enemy1.posX-=KEngine.KOLCE_SPEED;
        }


        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.15f, .15f, 1f);

        gl.glTranslatef(enemy1.posX, 0f, 0f);


        enemy1.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();







        if(enemy2.posX<-1)
        {

            enemy2.posX=(randomPos.nextFloat()*7)+7;

             if(enemy2.posX < (enemy1.posX+3) &&enemy2.posX  >(enemy1.posX)-3 ) {
                 enemy2.posX+=6;
             }
        }




        if(enemy2.posX>=-1)
        {

            enemy2.posX-=KEngine.BLOK_SPEED;
        }



//        gl.glMatrixMode(GL10.GL_MODELVIEW);
//        gl.glLoadIdentity();
//        gl.glPushMatrix();
//
//        gl.glScalef(.15f, .15f, 2f);
//
//        gl.glTranslatef(enemy2.posX, 0f, 0f);
//        gl.glMatrixMode(GL10.GL_TEXTURE);
//        gl.glLoadIdentity();
//        gl.glTranslatef(0.25f, 0.25f, 0.0f);
//        enemy2.draw(gl);
//        gl.glPopMatrix();
//        gl.glLoadIdentity();

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.15f, .15f, 1f);

        gl.glTranslatef(enemy2.posX, 0f, 0f);


        enemy2.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();

    }
    //okienko popup gdy występuje kolizja
//    public void showAlert(View view){
//        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
//        myAlert.setMessage("You dead!")
//                .create();
//
//        myAlert.showAlert();
//
//    }

    //sprawdzenie kolizji jeśli przeszkoda jest na pozycji Kulksona, to kolizja
    private void detectCollisions(){

           if((enemy1.posX <= (2.67f) )&& (KEngine.playerBankPosY <= 1.4f)&&(enemy1.posX > (1.1f))) {
               enemy1.posX = (randomPos.nextFloat() * 7) + 7;
               //show.alert
           }
            if((enemy2.posX <= (2.67f) )&& (KEngine.playerBankPosY <= 1.4f)&&(enemy2.posX > (1.1f))){
                   enemy2.posX = (randomPos.nextFloat() * 7) + 7;
               }



   }

}







