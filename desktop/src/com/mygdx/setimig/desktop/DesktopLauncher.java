package com.mygdx.setimig.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.setimig.Setimig;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.title = "Set i mig";
                config.useGL30 = false;
                config.width = 800 ;
                config.height = 480;
		new LwjglApplication(new Setimig(), config);
	}
}
