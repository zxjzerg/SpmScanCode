package com.example.spmscancode.ui.activity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.spmscancode.R;
import com.example.spmscancode.model.ModelContext;
import com.example.spmscancode.ui.adapter.ItemAdapter;
import com.example.spmscancode.ui.view.PlatoTitleBar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 支付界面
 * Created by Andrew on 15/3/4.
 */
public class PayActivity extends BaseActivity {

    @InjectView(R.id.iv_qr_code)
    ImageView mIvQrCode;

    @InjectView(R.id.view_title)
    PlatoTitleBar mTitleBar;

    @InjectView(R.id.lv_item_list)
    ListView mLvItems;

    @InjectView(R.id.tv_total)
    TextView mTvTotal;

    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.inject(this);

        String qrCode = getIntent().getStringExtra("qr_code");
        createQrCodeTask.execute(qrCode);

        mTitleBar.addRightButton("完成");
        mTitleBar.addLeftButton(R.drawable.ic_action_previous_item);
        mTitleBar.setLeftBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                onBackPressed();
            }
        });
        mTitleBar.setRightBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        mAdapter = new ItemAdapter(mContext, ModelContext.getInstance().getCurrentOrder());
        mLvItems.setAdapter(mAdapter);
        mTvTotal.setText(String.format(getString(R.string.current_total),
                ModelContext.getInstance().getCurrentOrder().getTotal().toString()));
    }

    private AsyncTask<String, String, Bitmap> createQrCodeTask = new AsyncTask<String, String, Bitmap>() {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return Create2DCode(params[0]);
            } catch (WriterException e) {
                Log.e(TAG, "Create qr code bitmap failed!", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mIvQrCode.setImageBitmap(bitmap);
            }

        }
    };

    /** 用字符串生成二维码 */
    public Bitmap Create2DCode(String str) throws WriterException {
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 500, 500);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //二维矩阵转为一维像素数组,也就是一直横着排了
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}

