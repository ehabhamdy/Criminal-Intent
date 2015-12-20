package com.ehabhamdy.criminalintent2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;


public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra("ExtraData");
        return CrimeFragment.newInstance(crimeId);

    }

    public static final Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra("ExtraData", crimeId);
        return intent;
    }
}