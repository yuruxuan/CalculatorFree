package coding.yu.calculator.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.youmi.android.AdManager;
import net.youmi.android.offers.OffersManager;

import coding.yu.calculator.R;
import coding.yu.calculator.bean.Calculator;

/**
 * Created by yuruxuan on 15-2-6.
 */
public class MainActivity extends Activity {

    private TextView mSourceCodeText;
    private TextView mAnalyseText;
    private TextView mResultText;
    private TextView mProcessText;

    private TextView mClearText;
    private TextView mDeleteText;
    private TextView mDivText;
    private TextView mMulText;
    private TextView mSevenText;
    private TextView mEightText;
    private TextView mNineText;
    private TextView mSubText;
    private TextView mFourText;
    private TextView mFiveText;
    private TextView mSixText;
    private TextView mAddText;
    private TextView mOneText;
    private TextView mTwoText;
    private TextView mThreeText;
    private TextView mNullEqualText;
    private TextView mNullZeroText;
    private TextView mZeroText;
    private TextView mDotText;
    private TextView mEqualText;
    private TableLayout mTableLayout;

    private Calculator mCalculator;
    private boolean hasDot;
    private StringBuffer mExpression;//负号转化后的表达式
    private StringBuffer mInput;//所有输入
    private Animation mProcessAnim;
    private Animation mResultAnim;
    private Animation mSourceCodeResultAnim;
    private Animation mSourceCodeTableAnim;
    private NumberClickListener mNumberClickListener = new NumberClickListener();
    private OperatorClickListener mOperatorClickListener = new OperatorClickListener();

    private String APP_ID = "3bcdd2f19e5759b";
    private String APP_SECRET = "78e8b826650c7ae";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, GetSourceCodeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_get_source_code_activity_in, R.anim.anim_get_source_code_activity_out);
                    break;
            }
        }
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        bindEvent();
        init();
        initAD();
    }

    private void initAD() {
        AdManager.getInstance(this).init(APP_ID, APP_SECRET, false);
        OffersManager.getInstance(this).onAppLaunch();
    }

    private void findView() {
        setContentView(R.layout.activity_main);
        mSourceCodeText = (TextView) findViewById(R.id.text_source_code);
        mAnalyseText = (TextView) findViewById(R.id.text_analyse);
        mResultText = (TextView) findViewById(R.id.text_result);
        mProcessText = (TextView) findViewById(R.id.text_process);
        mClearText = (TextView) findViewById(R.id.text_clear);
        mDeleteText = (TextView) findViewById(R.id.text_delete);
        mDivText = (TextView) findViewById(R.id.text_div);
        mMulText = (TextView) findViewById(R.id.text_mul);
        mSevenText = (TextView) findViewById(R.id.text_7);
        mEightText = (TextView) findViewById(R.id.text_8);
        mNineText = (TextView) findViewById(R.id.text_9);
        mSubText = (TextView) findViewById(R.id.text_sub);
        mFourText = (TextView) findViewById(R.id.text_4);
        mFiveText = (TextView) findViewById(R.id.text_5);
        mSixText = (TextView) findViewById(R.id.text_6);
        mAddText = (TextView) findViewById(R.id.text_add);
        mOneText = (TextView) findViewById(R.id.text_1);
        mTwoText = (TextView) findViewById(R.id.text_2);
        mThreeText = (TextView) findViewById(R.id.text_3);
        mNullEqualText = (TextView) findViewById(R.id.text_null_equal);
        mNullZeroText = (TextView) findViewById(R.id.text_null_0);
        mZeroText = (TextView) findViewById(R.id.text_0);
        mDotText = (TextView) findViewById(R.id.text_dot);
        mEqualText = (TextView) findViewById(R.id.text_equal);
        mTableLayout = (TableLayout) findViewById(R.id.layout_table);
    }

    private void bindEvent() {
        mResultText.setOnClickListener(mOperatorClickListener);
        mProcessText.setOnClickListener(mOperatorClickListener);
        mClearText.setOnClickListener(mOperatorClickListener);
        mDeleteText.setOnClickListener(mOperatorClickListener);
        mDivText.setOnClickListener(mOperatorClickListener);
        mMulText.setOnClickListener(mOperatorClickListener);
        mSevenText.setOnClickListener(mNumberClickListener);
        mEightText.setOnClickListener(mNumberClickListener);
        mNineText.setOnClickListener(mNumberClickListener);
        mSubText.setOnClickListener(mOperatorClickListener);
        mFourText.setOnClickListener(mNumberClickListener);
        mFiveText.setOnClickListener(mNumberClickListener);
        mSixText.setOnClickListener(mNumberClickListener);
        mAddText.setOnClickListener(mOperatorClickListener);
        mOneText.setOnClickListener(mNumberClickListener);
        mTwoText.setOnClickListener(mNumberClickListener);
        mThreeText.setOnClickListener(mNumberClickListener);
        mNullEqualText.setOnClickListener(mOperatorClickListener);
        mNullZeroText.setOnClickListener(mNumberClickListener);
        mZeroText.setOnClickListener(mNumberClickListener);
        mDotText.setOnClickListener(mNumberClickListener);
        mEqualText.setOnClickListener(mOperatorClickListener);

        mNullEqualText.setOnTouchListener(new OnEqualTouchListener());
        mEqualText.setOnTouchListener(new OnEqualTouchListener());
        mNullZeroText.setOnTouchListener(new OnZeroTouchListener());
        mZeroText.setOnTouchListener(new OnZeroTouchListener());

        mSourceCodeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResultText.startAnimation(mSourceCodeResultAnim);
                mTableLayout.startAnimation(mSourceCodeTableAnim);
                handler.sendEmptyMessageDelayed(100, 200);
            }
        });
        mAnalyseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AnalyseActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {
        mProcessAnim = AnimationUtils.loadAnimation(this, R.anim.anim_process);
        mResultAnim = AnimationUtils.loadAnimation(this, R.anim.anim_result);
        mSourceCodeResultAnim = AnimationUtils.loadAnimation(this, R.anim.anim_source_code_result);
        mSourceCodeTableAnim = AnimationUtils.loadAnimation(this, R.anim.anim_source_code_table);
        mCalculator = Calculator.getInstance();
        mExpression = new StringBuffer();
        mInput = new StringBuffer();
    }

    private void checkOperatorChar() {
        if (mInput.length() >= 2 && mInput.substring(mInput.length() - 2, mInput.length()).matches("([+]|-|×|÷)-")) {
            mInput = new StringBuffer(mInput.substring(0, mInput.length() - 2));
            mExpression = new StringBuffer(mExpression.substring(0, mExpression.length() - 2));
            return;
        }
        if (mInput.length() >= 1 && mInput.substring(mInput.length() - 1, mInput.length()).matches("[+]|-|×|÷")) {
            mInput = new StringBuffer(mInput.substring(0, mInput.length() - 1));
            mExpression = new StringBuffer(mExpression.substring(0, mExpression.length() - 1));
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OffersManager.getInstance(this).onAppExit();
    }

    private class OperatorClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.text_add:
                    checkOperatorChar();
                    mInput.append("+");
                    mExpression.append("+");
                    break;
                case R.id.text_sub:
                    if (mInput.length() == 0) {
                        mExpression.append("@");
                        mInput.append("-");
                        break;
                    }
                    if (mInput.length() >= 2 && mInput.substring(mInput.length() - 2, mInput.length()).matches("([+]|-|×|÷)-")) {
                        mInput = new StringBuffer(mInput.substring(0, mInput.length() - 1));
                        mExpression = new StringBuffer(mExpression.substring(0, mExpression.length() - 1));
                        break;
                    }
                    if (mInput.substring(mInput.length() - 1, mInput.length()).matches("[+]|-|×|÷")) {
                        mExpression.append("@");
                    }
                    if (mInput.substring(mInput.length() - 1, mInput.length()).matches("[0-9]")) {
                        mExpression.append("-");
                        hasDot = false;
                    }
                    mInput.append("-");
                    break;
                case R.id.text_mul:
                    checkOperatorChar();
                    mInput.append("×");
                    mExpression.append("×");
                    break;
                case R.id.text_div:
                    checkOperatorChar();
                    mInput.append("÷");
                    mExpression.append("÷");
                    break;
                case R.id.text_null_equal:
                case R.id.text_equal:
                    if (mInput.length() == 0) {
                        mResultText.setText("0");
                        return;
                    }
                    try {
                        mCalculator.analysis(mExpression.toString());
                        mResultText.setText(mCalculator.operator() + "");
                        mResultText.startAnimation(mResultAnim);
                        mProcessText.setText(mInput.toString().trim() + "=");
                        mProcessText.startAnimation(mProcessAnim);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "不合法的表达式", Toast.LENGTH_SHORT).show();
                    }
                    return;
                case R.id.text_clear:
                    mInput = new StringBuffer();
                    mExpression = new StringBuffer();
                    mCalculator.clear();
                    mResultText.setText("0");
                    hasDot = false;
                    return;
                case R.id.text_delete:
                    if (mInput.length() == 0) {
                        return;
                    }
                    if (mInput.substring(mInput.length() - 1, mInput.length()).matches("[.]")) {
                        hasDot = false;
                    }
                    mInput = new StringBuffer(mInput.substring(0, mInput.length() - 1));
                    mExpression = new StringBuffer(mExpression.substring(0, mExpression.length() - 1));

                    if (mInput.length() == 0) {
                        mResultText.setText(0 + "");
                        return;
                    }
                    break;
            }
            mResultText.setText(mInput.toString().trim());
        }
    }

    private class NumberClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.text_1:
                    mExpression.append("1");
                    mInput.append("1");
                    break;
                case R.id.text_2:
                    mExpression.append("2");
                    mInput.append("2");
                    break;
                case R.id.text_3:
                    mExpression.append("3");
                    mInput.append("3");
                    break;
                case R.id.text_4:
                    mExpression.append("4");
                    mInput.append("4");
                    break;
                case R.id.text_5:
                    mExpression.append("5");
                    mInput.append("5");
                    break;
                case R.id.text_6:
                    mExpression.append("6");
                    mInput.append("6");
                    break;
                case R.id.text_7:
                    mExpression.append("7");
                    mInput.append("7");
                    break;
                case R.id.text_8:
                    mExpression.append("8");
                    mInput.append("8");
                    break;
                case R.id.text_9:
                    mExpression.append("9");
                    mInput.append("9");
                    break;
                case R.id.text_0:
                case R.id.text_null_0:
                    mExpression.append("0");
                    mInput.append("0");
                    break;
                case R.id.text_dot:
                    if (hasDot) {
                        return;
                    }
                    if (mInput.length() == 0) {
                        mExpression.append("0");
                        mInput.append("0");
                    }
                    mExpression.append(".");
                    mInput.append(".");
                    hasDot = true;
                    break;
            }
            mResultText.setText(mInput.toString().trim());
        }
    }

    private class OnEqualTouchListener implements View.OnTouchListener {

        int orange = Color.parseColor("#ffff7435");
        int dark_orange = Color.parseColor("#ffe16931");

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    mEqualText.setBackground(new ColorDrawable(orange));
                    mNullEqualText.setBackground(new ColorDrawable(orange));
                    break;
                case MotionEvent.ACTION_DOWN:
                    mEqualText.setBackground(new ColorDrawable(dark_orange));
                    mNullEqualText.setBackground(new ColorDrawable(dark_orange));
                    break;
            }
            return false;
        }
    }

    private class OnZeroTouchListener implements View.OnTouchListener {

        int white = Color.WHITE;
        int light_gray = Color.parseColor("#ffeaeaea");

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    mNullZeroText.setBackground(new ColorDrawable(white));
                    mZeroText.setBackground(new ColorDrawable(white));
                    break;
                case MotionEvent.ACTION_DOWN:
                    mNullZeroText.setBackground(new ColorDrawable(light_gray));
                    mZeroText.setBackground(new ColorDrawable(light_gray));
                    break;
            }
            return false;
        }
    }
}
