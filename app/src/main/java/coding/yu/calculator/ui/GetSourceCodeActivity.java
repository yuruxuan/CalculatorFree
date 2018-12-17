package coding.yu.calculator.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.youmi.android.listener.Interface_ActivityListener;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsManager;

import coding.yu.calculator.R;

/**
 * Created by yuruxuan on 15-2-8.
 */
public class GetSourceCodeActivity extends Activity {

    private TextView mIntegralText;
    private TextView mADText;
    private TextView mDownloadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        bindView();
        initView();
    }

    private void findView() {
        setContentView(R.layout.activity_get_source_code);
        mIntegralText = (TextView) findViewById(R.id.text_integral);
        mADText = (TextView) findViewById(R.id.text_ad);
        mDownloadText = (TextView) findViewById(R.id.text_download);
    }

    private void bindView() {
        mADText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffersManager.getInstance(GetSourceCodeActivity.this).showOffersWall(new Interface_ActivityListener() {
                    @Override
                    public void onActivityDestroy(Context context) {
                        checkIntegral();
                    }
                });
            }
        });
        mDownloadText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PointsManager.getInstance(GetSourceCodeActivity.this).queryPoints() < 200) {
                    Toast.makeText(GetSourceCodeActivity.this, "积分不足", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = Uri.parse("http://pan.baidu.com/s/1jGqYSdC");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        checkIntegral();
    }

    private void checkIntegral() {
        int point = PointsManager.getInstance(this).queryPoints();
        mIntegralText.setText(point + "");
        if (point < 200) {
            mDownloadText.setBackgroundColor(Color.GRAY);
            return;
        }
        mDownloadText.setBackgroundColor(Color.RED);
    }
}
