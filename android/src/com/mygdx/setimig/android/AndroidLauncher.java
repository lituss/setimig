package com.mygdx.setimig.android;

import android.os.Bundle;

import android.view.WindowManager;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.setimig.Setimig;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		getWindow().addFlags((WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
		//config.useGLSurfaceView20API18 = false;
		initialize(new Setimig(), config);
	}
}
