package coding.yu.calculator.bean;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yuruxuan on 15-2-7.
 */
public class Calculator {
    private static Calculator c;
    private ArrayList<Float> mNumberList;
    private ArrayList<String> mOperatorCharList;

    private Calculator() {
        mNumberList = new ArrayList<Float>();
        mOperatorCharList = new ArrayList<String>();
    }

    public static Calculator getInstance() {
        if (c == null) {
            c = new Calculator();
        }
        return c;
    }

    public void clear() {
        mNumberList.clear();
        mOperatorCharList.clear();
    }

    public float operator() {
        int index1 = 0;
        int index2 = 0;
        float tempSum = 0;
        ArrayList<Float> numberList = new ArrayList<Float>(this.mNumberList);
        ArrayList<String> operatorCharList = new ArrayList<String>(this.mOperatorCharList);
        int size = operatorCharList.size();
        if (size == 0) {
            return numberList.get(0);
        }
        for (int i = 0; i < size; i++) {
            if (operatorCharList.contains("×") || operatorCharList.contains("÷")) {
                index1 = operatorCharList.indexOf("×");
                index2 = operatorCharList.indexOf("÷");
                if ((index1 != -1 && index2 != -1 && index1 < index2) || index2 == -1) {
                    tempSum = numberList.get(index1) * numberList.get(index1 + 1);
                    numberList.set(index1, tempSum);
                    numberList.remove(index1 + 1);
                    operatorCharList.remove(index1);
                } else if ((index1 != -1 && index2 != -1 && index1 > index2) || index1 == -1) {
                    tempSum = numberList.get(index2) / numberList.get(index2 + 1);
                    numberList.set(index2, tempSum);
                    numberList.remove(index2 + 1);
                    operatorCharList.remove(index2);
                }
                numberList.trimToSize();
            } else if (operatorCharList.contains("+") || operatorCharList.contains("-")) {
                index1 = operatorCharList.indexOf("+");
                index2 = operatorCharList.indexOf("-");
                if ((index1 != -1 && index2 != -1 && index1 < index2) || index2 == -1) {
                    tempSum = numberList.get(index1) + numberList.get(index1 + 1);
                    numberList.set(index1, tempSum);
                    numberList.remove(index1 + 1);
                    operatorCharList.remove(index1);
                } else if ((index1 != -1 && index2 != -1 && index1 > index2) || index1 == -1) {
                    tempSum = numberList.get(index2) - numberList.get(index2 + 1);
                    numberList.set(index2, tempSum);
                    numberList.remove(index2 + 1);
                    operatorCharList.remove(index2);
                }
                numberList.trimToSize();
            }
        }
        return tempSum;
    }

    public void analysis(String expression) {
        mNumberList.clear();
        mOperatorCharList.clear();
        String[] nums = expression.split("[+]|-|×|÷");
        for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i].replace('@', '-');
            mNumberList.add(Float.parseFloat(nums[i]));
        }
        String[] chars = expression.split("0|1|2|3|4|5|6|7|8|9|0|@|[.]");
        for (int i = 0; i < chars.length; i++) {
            if (TextUtils.isEmpty(chars[i].trim()))
                continue;
            mOperatorCharList.add(chars[i].trim());
        }
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "mNumberList=" + mNumberList +
                ", mOperatorCharList=" + mOperatorCharList +
                '}';
    }
}
