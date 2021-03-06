package cn.com.jinke.wh_drugcontrol.chat.manager;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.com.jinke.wh_drugcontrol.customview.chat.RecordStrategy;

public class AudioRecorder implements RecordStrategy {

	private MediaRecorder recorder;
	private String fileName;
	private String fileFolder = Environment.getExternalStorageDirectory()
			.getPath() + "/Drugcontrol_play";

	private boolean isRecording = false;
	private MediaPlayer player = null;

	@Override
	public void ready() {
		// TODO Auto-generated method stub
		File file = new File(fileFolder);
		if (!file.exists()) {
			file.mkdir();
		}
		player = new MediaPlayer();
		fileName = getCurrentDate();
		if (recorder == null) {
			recorder = new MediaRecorder();
		}
		
		recorder.setOutputFile(fileFolder + "/" + fileName + ".amr");
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置MediaRecorder的音频源为麦克风
		recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);// 设置MediaRecorder录制的音频格式
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 设置MediaRecorder录制音频的编码为amr
	}

	// 以当前时间作为文件名
	private String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if (!isRecording) {
			try {
				recorder.prepare();
				recorder.start();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			isRecording = true;
		}

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		if (isRecording) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;
			isRecording = false;
		}

	}

	@Override
	public void deleteOldFile() {
		// TODO Auto-generated method stub
		File file = new File(fileFolder + "/" + fileName + ".amr");
		file.deleteOnExit();

	}

	@Override
	public double getAmplitude() {
		// TODO Auto-generated method stub
		if (!isRecording) {
			return 0;
		}
		try {
			return recorder.getMaxAmplitude();
		} catch (IllegalStateException e) {
			return 0;
		}
	}

	@Override
	public String getFilePath() {
		// TODO Auto-generated method stub
		return fileFolder + "/" + fileName + ".amr";
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		try {
			player.setDataSource(getFilePath());
			player.prepare();
			player.start();
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

}
