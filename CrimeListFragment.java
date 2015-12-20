package com.ehabhamdy.criminalintent2;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by ehabhamdy on 10/4/15.
 */
public class CrimeListFragment extends Fragment{

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int changedItemPositions = 0;
    private boolean mSubtitleVisible;


    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);

        CrimeLab lab = CrimeLab.get(getActivity());

        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(changedItemPositions);
        }

        updateSubtitle();
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Crime mCrime;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        public CrimeHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_title_textview);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_item_date_textview);
            mSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_crime_solved_checkbox);
        }

        public void bindCrime(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedCheckBox.setChecked(mCrime.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
            //Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            changedItemPositions = getLayoutPosition();
            Toast.makeText(getActivity(), "this is item: "+changedItemPositions, Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_crime, viewGroup, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            //holder.mTitleTextView.setText(crime.getTitle());
            holder.bindCrime(crime);
        }

        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleMenu = menu.findItem(R.id.menu_item_new_show_subtitle);
        if(mSubtitleVisible)
            subtitleMenu.setTitle(R.string.hide_subtitle);
        else
            subtitleMenu.setTitle(R.string.show_subtitle);
    }


    private void updateSubtitle(){
        CrimeLab lab = CrimeLab.get(getActivity());
        int crimeCount = lab.getCrimes().size();
        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural,crimeCount, crimeCount);

        if(!mSubtitleVisible)
            subtitle = null;

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).add(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_new_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
