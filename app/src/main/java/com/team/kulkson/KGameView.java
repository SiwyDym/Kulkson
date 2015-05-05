package com.team.kulkson;


import android.content.Context;
import android.opengl.GLSurfaceView;



/**
 * Created by Marco on 2015-04-09.
 */

    public class  KGameView extends GLSurfaceView {

    private KGameRenderer renderer;

        public KGameView(Context context){
            super(context);

            renderer = new KGameRenderer();
            this.setRenderer(renderer);


        }

}
