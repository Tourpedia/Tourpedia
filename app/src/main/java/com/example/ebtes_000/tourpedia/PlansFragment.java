package com.example.ebtes_000.tourpedia;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import javax.annotation.Nullable;

/**
 * Created by ebtes_000 on 14/04/16.
 */
public class PlansFragment extends Fragment {
    ListView listSavedPlans;
    String[] SavedPlans;
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.plans_fragment_layout, container,false);
    }





}
