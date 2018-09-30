package cn.com.jinke.wh_drugcontrol.customview.chat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;


@SuppressLint("AppCompatCustomView")
public class RecordButton extends Button {

	private static final int MIN_RECORD_TIME = 2; // 最短录音时间，单位秒
	private static final int RECORD_OFF = 0; // 不在录音
	private static final int RECORD_ON = 1; // 正在录音
	private static final int MAX_RECORD_TIME = 60;// 录制最大时间常量

	private static final int HANDLE_MESSAGE_TAG = 10000;// 消息Tag
	private static final int TOAST_DURATION = 250; // toast消失时间为0.25秒

	private Dialog mRecordDialog;
	private RecordStrategy mAudioRecorder;
	private Thread mRecordThread;
	private RecordListener listener;
	private TextView txRecordTime;

	private int recordState = 0; // 录音状态
	private float recodeTime = 0.0f; // 录音时长，如果录音时间太短则录音失败
	private double voiceValue = 0.0; // 录音的音量值
	private boolean isCanceled = false; // 是否取消录音
	private float downY;

	private TextView dialogTextView;
	private ImageView dialogImg;
	private Context mContext;
	private RecordButtonListener rlistener;

	public RecordButtonListener getRlistener() {
		return rlistener;
	}

	public void setRlistener(RecordButtonListener rlistener) {
		this.rlistener = rlistener;
	}

	// 控制只在本页面展示TOAST
	private Toast toast;

	public Toast getToast() {
		return toast;
	}

	public void setToast(Toast toast) {
		this.toast = toast;
	}

	private Handler handle = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLE_MESSAGE_TAG:
				countTime();
				break;

			default:
				break;
			}
		};
	};

	private Timer timer = null;
	// 设置录制时间最大为60秒
	private int maxRecordTime = MAX_RECORD_TIME;

	public RecordButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		this.setText("按住说话");
	}

	// 倒计时
	private void countTime() {
		if (maxRecordTime <= 0) {
			recordState = RECORD_OFF;
			if (mRecordDialog.isShowing()) {
				mRecordDialog.dismiss();
			}
			mAudioRecorder.stop();
			mRecordThread.interrupt();
			// mAudioRecorder.play();
			// 松开手指取消计时器
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
			voiceValue = 0.0;
			if (isCanceled) {
				mAudioRecorder.deleteOldFile();
			} else {
				if (recodeTime < MIN_RECORD_TIME) {
					showWarnToast("时间太短 录音失败");
					mAudioRecorder.deleteOldFile();
				} else {
					if (listener != null) {
						listener.recordEnd(mAudioRecorder.getFilePath());
					}
					MessageProxy.sendEmptyMessage(MsgKey.PLAY);
				}
			}
			recodeTime = 0.0f;
			isCanceled = false;
			this.setText("按住说话");
			
			return;
		}
		maxRecordTime -= 1;

		if (txRecordTime != null) {
			txRecordTime.setText(maxRecordTime + "''");
		}
	}

	// 开始计时
	private void startTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				handle.sendEmptyMessage(HANDLE_MESSAGE_TAG);
			}
		}, 0, 1000);
	}

	public void setAudioRecord(RecordStrategy record) {
		this.mAudioRecorder = record;
	}

	public void setRecordListener(RecordListener listener) {
		this.listener = listener;
	}

	// 录音时显示Dialog
	private void showVoiceDialog(int flag) {
		if (mRecordDialog == null) {
			mRecordDialog = new Dialog(mContext, R.style.Dialogstyle);
			mRecordDialog.setContentView(R.layout.dialog_record);
			dialogImg = (ImageView) mRecordDialog
					.findViewById(R.id.record_dialog_img);
			dialogTextView = (TextView) mRecordDialog
					.findViewById(R.id.record_dialog_txt);
			txRecordTime = (TextView) mRecordDialog
					.findViewById(R.id.record_time);
		}
		switch (flag) {
		case 1:
			dialogImg.setImageResource(R.drawable.record_cancel);
			// dialogTextView.setText("松开手指可取消录音");
			dialogTextView.setBackgroundResource(R.drawable.cancel_record_tip);
			this.setText("松开手指 取消录音");
			break;

		default:
			dialogImg.setImageResource(R.drawable.record_animate_01);
			// dialogTextView.setText("向上滑动可取消录音");
			dialogTextView.setBackgroundResource(R.drawable.cancel_record);
			this.setText("松开手指 完成录音");
			break;
		}
		dialogTextView.setTextSize(14);
		mRecordDialog.show();
	}

	// 录音时间太短时Toast显示
	private void showWarnToast(String toastText) {
		if (toast != null) {
			toast.cancel();
			toast = null;
		}
		toast = new Toast(mContext);
		View warnView = LayoutInflater.from(mContext).inflate(
				R.layout.toast_warn, null);
		toast.setDuration(TOAST_DURATION);
		toast.setView(warnView);
		toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间
		toast.show();
	}

	// 开启录音计时线程
	private void callRecordTimeThread() {
		mRecordThread = new Thread(recordThread);
		mRecordThread.start();
	}

	// 录音Dialog图片随录音音量大小切换
	private void setDialogImage() {
		if (voiceValue < 600.0) {
			dialogImg.setImageResource(R.drawable.record_animate_01);
		} else if (voiceValue > 600.0 && voiceValue < 1000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_02);
		} else if (voiceValue > 1000.0 && voiceValue < 1200.0) {
			dialogImg.setImageResource(R.drawable.record_animate_03);
		} else if (voiceValue > 1200.0 && voiceValue < 1400.0) {
			dialogImg.setImageResource(R.drawable.record_animate_04);
		} else if (voiceValue > 1400.0 && voiceValue < 1600.0) {
			dialogImg.setImageResource(R.drawable.record_animate_05);
		} else if (voiceValue > 1600.0 && voiceValue < 1800.0) {
			dialogImg.setImageResource(R.drawable.record_animate_06);
		} else if (voiceValue > 1800.0 && voiceValue < 2000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_07);
		} else if (voiceValue > 2000.0 && voiceValue < 3000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_08);
		} else if (voiceValue > 3000.0 && voiceValue < 4000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_09);
		} else if (voiceValue > 4000.0 && voiceValue < 6000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_10);
		} else if (voiceValue > 6000.0 && voiceValue < 8000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_11);
		} else if (voiceValue > 8000.0 && voiceValue < 10000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_12);
		} else if (voiceValue > 10000.0 && voiceValue < 12000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_13);
		} else if (voiceValue > 12000.0) {
			dialogImg.setImageResource(R.drawable.record_animate_14);
		}
	}

	// 录音线程
	private Runnable recordThread = new Runnable() {

		@Override
		public void run() {
			recodeTime = 0.0f;
			while (recordState == RECORD_ON) {
				{
					try {
						Thread.sleep(100);
						recodeTime += 0.1;
						// 获取音量，更新dialog
						if (!isCanceled) {
							voiceValue = mAudioRecorder.getAmplitude();
							recordHandler.sendEmptyMessage(1);
						}
					} catch (Exception e) {
						voiceValue = 0;
						recordHandler.sendEmptyMessage(1);
					}
				}
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler recordHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setDialogImage();
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 按下按钮
			if (recordState != RECORD_ON) {
				if (getToast() != null) {
					getToast().cancel();
				}
				
				showVoiceDialog(0);
				downY = event.getY();
				if (mAudioRecorder != null) {
					mAudioRecorder.ready();
					recordState = RECORD_ON;
					mAudioRecorder.start();
					callRecordTimeThread();
				}
				// 刚开始录制时初始化最大时间
				maxRecordTime = MAX_RECORD_TIME;
				startTimer();

				// 调用开始录音的监听
				if (rlistener != null) {
					rlistener.startRecord();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE: // 滑动手指
			float moveY = event.getY();
			if (downY - moveY > 50) {
				isCanceled = true;
				showVoiceDialog(1);
			}
			if (downY - moveY < 20) {
				isCanceled = false;
				showVoiceDialog(0);
			}
			break;
		case MotionEvent.ACTION_UP: // 松开手指
			if (recordState == RECORD_ON) {
				recordState = RECORD_OFF;
				if (mRecordDialog.isShowing()) {
					mRecordDialog.dismiss();
				}
				mAudioRecorder.stop();
				mRecordThread.interrupt();
				// mAudioRecorder.play();
				// 松开手指取消计时器
				if (timer != null) {
					timer.cancel();
					timer = null;
				}
				voiceValue = 0.0;
				if (isCanceled) {
					mAudioRecorder.deleteOldFile();
				} else {
					if (recodeTime < MIN_RECORD_TIME) {
						showWarnToast("时间太短 录音失败");
						mAudioRecorder.deleteOldFile();
					} else {
						if (listener != null) {
							listener.recordEnd(mAudioRecorder.getFilePath());
						}
						MessageProxy.sendEmptyMessage(MsgKey.PLAY);
					}
				}
				recodeTime = 0.0f;
				isCanceled = false;
				this.setText("按住说话");
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			recordState = RECORD_OFF;
			if (mRecordDialog.isShowing()) {
				mRecordDialog.dismiss();
			}
			mAudioRecorder.stop();
			mRecordThread.interrupt();
			// mAudioRecorder.play();
			// 松开手指取消计时器
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
			voiceValue = 0.0;
			mAudioRecorder.deleteOldFile();
			isCanceled = false;
			this.setText("按住说话");
			break;
		}

		return true;
	}

	public interface RecordListener {
		public void recordEnd(String filePath);
	}
}
