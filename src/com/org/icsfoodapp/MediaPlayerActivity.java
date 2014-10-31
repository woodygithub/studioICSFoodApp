package com.org.icsfoodapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.TransportMediator;
import android.support.v4.media.TransportPerformer;
import android.view.View;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.fax.utils.view.TopBarContain;
import com.woody.wxj.view.media.MediaController;

@SuppressLint("NewApi")
public class MediaPlayerActivity extends Activity {
	String url = "http://www.finedining.com.cn/Public/Upload/RestaurantVideo/30/a74a76a84ce71529d57695a878666e2c.mp4";
	VideoView videoView;
	ProgressDialog pd;
	View view;
	public static void start(Activity activity, String videoUrl) {
		activity.startActivity(new Intent().setClass(activity, MediaPlayerActivity.class)
			.putExtra(String.class.getName(), videoUrl));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		url = getIntent().getStringExtra(String.class.getName());
		view = getLayoutInflater().inflate(R.layout.media_player, null, false);
		TopBarContain topBarContain = new TopBarContain(this)
			.setTitle("", R.drawable.topbar_ic_logo, 0)
			.setLeftBtn(R.drawable.topbar_ic_back,  new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					MediaPlayerActivity.this.finish();
				}
			}).setContentView(view);
		setContentView(topBarContain);
		videoView = (VideoView)findViewById(R.id.media_player_video);
		Uri uri = Uri.parse(url);
		videoView.setVideoURI(uri);
		pd=new ProgressDialog(MediaPlayerActivity.this);
		pd.setMessage(getString(com.fax_utils.R.string.Task_PleaseWait));
		pd.setCanceledOnTouchOutside(false);
		pd.setCancelable(true);
		pd.setOnCancelListener(new DialogInterface.OnCancelListener(){
			public void onCancel(DialogInterface dialog) {
			}
		});
	    pd.show();
		videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
		    @Override
		    public void onPrepared(MediaPlayer mp) {
			    mp.start();
			    mp.setLooping(false);
			    mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
			    	int currentPosition, duration;
					@Override
					public void onBufferingUpdate(MediaPlayer mp, int percent) {
						 // 获得当前播放时间和当前视频的长度
						currentPosition = videoView.getCurrentPosition();
						duration = videoView.getDuration(); 
						int time = ((currentPosition * 100) / duration);
						// 设置进度条的主要进度，表示当前的播放时间
						SeekBar seekBar = new SeekBar(MediaPlayerActivity.this);
						seekBar.setProgress(time);
						// 设置进度条的次要进度，表示视频的缓冲进度
						seekBar.setSecondaryProgress(percent);
						if (currentPosition>0) {
							pd.dismiss();
						}
					}
				});
		    }
		});
		MediaController mediaController = new MediaController(this);
		mediaController.setMediaPlayer(new TransportMediator(this, transportPerformer));
		videoView.setMediaController(mediaController);
		videoView.start();
	}
	@Override
	protected void onResume() {
		super.onResume();
		videoView.resume();
		videoView.start();
	}
	@Override
	protected void onPause() {
		super.onPause();
		videoView.stopPlayback();
		videoView.pause();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		videoView.stopPlayback();
	}
	TransportPerformer transportPerformer = new TransportPerformer() {
		@Override
		public void onStop() {
			videoView.pause();
		}
		@Override
		public void onStart() {
			videoView.start();
		}
		@Override
		public void onSeekTo(long pos) {
			videoView.seekTo((int)pos);
		}
		@Override
		public void onPause() {
			videoView.pause();
		}
		@Override
		public boolean onIsPlaying() {
			return videoView.isPlaying();
		}
		@Override
		public long onGetDuration() {
			return videoView.getDuration();
		}
		@Override
		public long onGetCurrentPosition() {
			return videoView.getCurrentPosition();
		}
		@Override
		public int onGetBufferPercentage() {
			return videoView.getBufferPercentage();
		}
		@Override
		public int onGetTransportControlFlags() {
			int flags = TransportMediator.FLAG_KEY_MEDIA_PLAY
                    | TransportMediator.FLAG_KEY_MEDIA_PLAY_PAUSE
                    | TransportMediator.FLAG_KEY_MEDIA_STOP;
            if (videoView.canPause()) {
                flags |= TransportMediator.FLAG_KEY_MEDIA_PAUSE;
            }
            if (videoView.canSeekBackward()) {
                flags |= TransportMediator.FLAG_KEY_MEDIA_REWIND;
            }
            if (videoView.canSeekForward()) {
                flags |= TransportMediator.FLAG_KEY_MEDIA_FAST_FORWARD;
            }
			return flags;
		}
		
	};
}
