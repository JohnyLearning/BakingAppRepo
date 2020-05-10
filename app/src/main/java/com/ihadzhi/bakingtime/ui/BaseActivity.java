package com.ihadzhi.bakingtime.ui;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.model.OnError;

public class BaseActivity extends AppCompatActivity implements OnError {

    @Override
    public void error(Throwable throwable) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getString(R.string.generic_error));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok_button), (dialog, which) -> {
            alertDialog.dismiss();
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

}
