package com.ehabhamdy.criminalintent2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ehabhamdy.criminalintent2.database.CrimeBaseHelper;
import com.ehabhamdy.criminalintent2.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.ehabhamdy.criminalintent2.database.CrimeDbSchema.*;

/**
 * Created by ehabhamdy on 10/4/15.
 */
public class CrimeLab {

    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes ;
    private Context mContext;

    //private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context){
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(context);
        return sCrimeLab;
    }

    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        for (int i=0; i<100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i%2 == 0);
            mCrimes.add(crime);
        }
        mContext = context.getApplicationContext();
      // mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
      // mCrimes = new ArrayList<>();
    }

    public List<Crime> getCrimes() {
        return mCrimes;
        //return new ArrayList<>();
    }

    public Crime getCrime(UUID id){
        for(Crime crime: mCrimes)
            if(crime.getId().equals(id))
                return crime;

        return null;
    }

    public void add(Crime crime){
        mCrimes.add(crime);
        //ContentValues values = getConentValues(crime);
        //mDatabase.insert(CrimeTable.NAME, null, values);
    }
    public void remove(Crime crime) {
        mCrimes.remove(crime);
    }

    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        //ContentValues values = getConentValues(crime);
        //mDatabase.update(CrimeTable.NAME, values, CrimeTable.cols.UUID+ "= ?", new String[] {uuidString});
    }

    /*public static ContentValues getConentValues(Crime crime){
        ContentValues Values = new ContentValues();
        Values.put(CrimeTable.cols.UUID, crime.getId().toString());
        Values.put(CrimeTable.cols.TITLE, crime.getTitle());
        Values.put(CrimeTable.cols.DATE, crime.getDate().getTime());
        Values.put(CrimeTable.cols.SOLVED, crime.isSolved());
        return Values;
    }*/
}
