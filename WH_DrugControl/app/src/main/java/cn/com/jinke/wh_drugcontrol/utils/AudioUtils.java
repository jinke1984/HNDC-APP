package cn.com.jinke.wh_drugcontrol.utils;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AudioUtils {
	public interface PlayState {
		public void onPreparedAndStartPlaySuccess();
		public void onFinishedPlay();
		public void onErrorPlay();
	}
	
	private static AudioUtils single = null;
	private Map<String, String> map =  new HashMap<String, String>();
	private String playUrlStr = null;
	private int playPosition = -1;
	public int getPlayPosition() {
		return playPosition;
	}

	public void setPlayPosition(int playPosition) {
		this.playPosition = playPosition;
	}

	public String getPlayUrlStr() {
		return playUrlStr;
	}

	public void setPlayUrlStr(String playUrlStr) {
		this.playUrlStr = playUrlStr;
	}

	private MediaPlayer mediaPlayer = null;
	// 静态工厂方法
	public static AudioUtils getInstance() {
		if (single == null) {
			single = new AudioUtils();
		}
		return single;
	}

	public AudioUtils() {
		this.mediaPlayer = new MediaPlayer();
	}
	
	public void play(int position, String url, final PlayState state) {
		try {
			this.playPosition = position;
			this.mediaPlayer.reset();
			this.setPlayUrlStr(url);
			this.mediaPlayer.setDataSource(url);
			this.mediaPlayer.prepare();
			this.mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					// 准备好了资源开始播放
					mediaPlayer.start();
					state.onPreparedAndStartPlaySuccess();
				}
			});
			
			this.mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					state.onFinishedPlay();
					setPlayPosition(-1);
				}
			});
			
			this.mediaPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					state.onErrorPlay();
					setPlayPosition(-1);
					return false;
				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isPlaying() {
		return this.mediaPlayer.isPlaying();
	}
	
	public void stopPlay() {
		this.playPosition = -1;
		this.mediaPlayer.stop();
		this.mediaPlayer.reset();
	}
	
	public String gettime(String string) { // 使用此方法可以直接在后台获取音频文件的播放时间，而不会真的播放音频
		String time = this.map.get(MD5Utils.getMD5(string));
		if (time != null && !TextUtils.isEmpty(time)) {
			return time;
		}
		
		MediaPlayer player = new MediaPlayer(); // 首先你先定义一个mediaplayer
		try {
			player.setDataSource(string); // String是指音频文件的路径
			player.prepare(); // 这个是mediaplayer的播放准备 缓冲
		} catch (Exception e) {
			AppLogger.e(e.getMessage());
			e.printStackTrace();
		}

		double size = player.getDuration();// 得到音频的时间
		String timelong1 = (int) Math.ceil((size / 1000)) + "";// 转换为秒 单位为''
		AppLogger.e("voice time  :"+timelong1);
		player.stop();// 暂停播放
		player.release();// 释放资源
		
		this.map.put(MD5Utils.getMD5(string), timelong1);
		
		return timelong1;
	}
}
