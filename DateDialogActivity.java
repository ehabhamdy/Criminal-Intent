package com.ehabhamdy.criminalintent2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Ehab on 11/1/15.
 */
public class DateDialogActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra("ExtraData");
        return DatePickerFragment.newInstance(date);
    }

    public static final Intent newIntent(Context packageContext, Date crimeDate){
        Intent intent = new Intent(packageContext, DateDialogActivity.class);
        intent.putExtra("ExtraData", crimeDate);
        return intent;
    }
}
