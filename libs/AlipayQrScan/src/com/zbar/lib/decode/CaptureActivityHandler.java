package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Message;

import com.zbar.lib.CaptureActivity;
import com.zbar.lib.Id;
import com.zbar.lib.R;
import com.zbar.lib.camera.CameraManager;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 
 * 时间: 2014年5月9日 下午12:23:32
 *
 * 版本: V_1.0.0
 *
 * 描述: 扫描消息转发
 */
public final class CaptureActivityHandler extends Handler {

	DecodeThread decodeThread = null;
	CaptureActivity activity = null;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(CaptureActivity activity) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity);
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {

		switch (message.what) {
		case Id.AUTO_FOCUS:
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, Id.AUTO_FOCUS);
			}
			break;
		case Id.RESTART_PREVIEW:
			restartPreviewAndDecode();
			break;
		case Id.DECODE_SUCCEEDED:
			state = State.SUCCESS;
			activity.handleDecode((String) message.obj);// 解析成功，回调
			break;

		case Id.DECODE_FAILED:
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					Id.DECODE);
			break;
		}

	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(Id.DECODE_SUCCEEDED);
		removeMessages(Id.DECODE_FAILED);
		removeMessages(Id.DECODE);
		removeMessages(Id.AUTO_FOCUS);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					Id.DECODE);
			CameraManager.get().requestAutoFocus(this, Id.AUTO_FOCUS);
		}
	}

}
