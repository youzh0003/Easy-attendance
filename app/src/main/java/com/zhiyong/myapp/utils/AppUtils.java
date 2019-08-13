package com.zhiyong.myapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zhiyong.myapp.R;

import java.util.UUID;

public class AppUtils {
    public static final String ANNUAL_PATTERN = "#,###,###,###";
    public static final String HOURLY_PATTERN = "#,###,###,##0.00";
    private static final String CLOUDINARY_BASE_URL = "http://res.cloudinary.com/chris-mackie/";

    public static boolean isConnectivityAvailable(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnectedOrConnecting();
    }

    public static MaterialDialog createProgress(Context context, String title) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content("Please wait")
                .progress(true, 0)
                .cancelable(false)
                .build();
    }

    public static MaterialDialog createAlertDialog(Context context, String title) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText("OK")
                .build();
    }

    public static boolean validateString(String input) {
        if (input == null || input.length() == 0 || input.equals("null")) {
            return false;
        }
        return true;
    }

    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.show();
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null && activity.getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(
                        activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void openKeyboard(EditText edt, Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static int convertDpToPixels(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static boolean validateEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isTablet(Activity activity) {
        float maxSizePhone = 6.5f;
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= maxSizePhone) {
            return true;
        } else {
            return false;
        }
    }

    public static void showSnackbar(Activity context, final int mainTextStringId, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(context.findViewById(android.R.id.content),
                context.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.WHITE)
                .setAction(context.getString(actionStringId), listener);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        snackbar.show();
    }
}


