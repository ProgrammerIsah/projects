package com.mygdx.example;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class AndroidLauncher extends AndroidApplication implements AdHandler{

	private static final String adUnitId="ca-app-pub-4684895449887564/6804683339";
	private AdView adView;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case SHOW_ADS:
					adView.setVisibility(View.VISIBLE);
					break;
				case HIDE_ADS:
					adView.setVisibility(View.GONE);
					break;
			}
		}
	};

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

		View gameView=initializeForView(new MainGame(this), config);

		RelativeLayout.LayoutParams gameViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		gameViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		gameViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);

		gameView.setLayoutParams(gameViewParams);
		layout.addView(gameView);

		adView = new AdView(this);
		adView.setAdSize(AdSize.LARGE_BANNER);
		adView.setAdUnitId(adUnitId);

		AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
//		adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
		adView.loadAd(adRequestBuilder.build());

		RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		topParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,RelativeLayout.TRUE);
		topParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		layout.addView(adView, topParams);
		adView.setBackgroundColor(android.graphics.Color.TRANSPARENT);

		setContentView(layout);
	}

	@Override
	protected void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		adView.pause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adView.destroy();
	}

	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}

}

//public class AndroidLauncher extends AndroidApplication {
//
//	protected AdView adView;
//	RelativeLayout layout;
//	@Override
//	protected void onCreate (Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new MainGame(), config);
//	}
//}
