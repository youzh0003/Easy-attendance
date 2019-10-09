package com.zhiyong.easy_attendance.utils;

import android.graphics.Paint;
import android.widget.TextView;

public class TextViewUtils {
    public static void setUnderLineText(TextView textview) {
        textview.setPaintFlags(textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}
