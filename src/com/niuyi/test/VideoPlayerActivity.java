package com.niuyi.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity{
	
	private View infoLayout;
	
	private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	infoLayout.setVisibility(View.INVISIBLE);
        }
    };

	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.video_player);
		MyVideoView videoView = (MyVideoView)findViewById(R.id.videoView1);
		String path = getIntent().getExtras().getString("VIDEO_URL");
		videoView.setVideoPath(path);
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.start();
		
		infoLayout = findViewById(R.id.infoLayout);
		Message message = mHandler.obtainMessage();
		mHandler.sendMessageDelayed(message, 3000);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		Log.d("Visibility", "event: " + event.getAction());
		if(event.getAction() != MotionEvent.ACTION_DOWN){
			return false;
		}
		Log.d("Visibility", "v: " + infoLayout.getVisibility());
		if(infoLayout.getVisibility() == View.INVISIBLE){
			infoLayout.setVisibility(View.VISIBLE);
			Message message = mHandler.obtainMessage();
			mHandler.sendMessageDelayed(message, 3000);
		}else{
			infoLayout.setVisibility(View.INVISIBLE);
		}

		return true;
	}
}
