package coding.yu.calculator.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import coding.yu.calculator.R;

/**
 * Created by yuruxuan on 15-2-8.
 */
public class AnalyseActivity extends Activity implements View.OnClickListener {

    private TextView mTip1Text;
    private TextView mTip2Text;
    private TextView mTip3Text;
    private TextView mTip4Text;
    private TextView mTipTopText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        bindView();
    }

    private void findView() {
        setContentView(R.layout.activity_analyse);
        mTip1Text = (TextView) findViewById(R.id.text_tip1);
        mTip2Text = (TextView) findViewById(R.id.text_tip2);
        mTip3Text = (TextView) findViewById(R.id.text_tip3);
        mTip4Text = (TextView) findViewById(R.id.text_tip4);
        mTipTopText = (TextView) findViewById(R.id.tip);
    }

    private void bindView() {
        mTip1Text.setOnClickListener(this);
        mTip2Text.setOnClickListener(this);
        mTip3Text.setOnClickListener(this);
        mTip4Text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_tip1:
                mTipTopText.setText("1.结果和过程添加了Translate(位移)和的Scale(缩放)动画，让用户有了良好的视觉体验");
                break;
            case R.id.text_tip2:
                mTipTopText.setText("2.这是一个TableLayout(表格布局)，表格部分和上边的比例为5:3");
                break;
            case R.id.text_tip3:
                mTipTopText.setText("3.这其实是两个按钮，只不过左下角的白色部分没有文字内容，也没有设置框线，就看起来是两个了，这两个按钮的点击效果和事件也是分别设置的，只不是一样的罢了^_^");
                break;
            case R.id.text_tip4:
                mTipTopText.setText("4.这个框线呢，其实是表格布局的背景颜色而已，每个单元格之间留个一点缝隙，就看起来像框线啦");
                break;
        }
    }
}
