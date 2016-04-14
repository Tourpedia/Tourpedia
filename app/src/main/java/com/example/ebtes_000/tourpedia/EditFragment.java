package com.example.ebtes_000.tourpedia;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.annotation.Nullable;

/**
 * Created by ebtes_000 on 14/04/16.
 */
public class EditFragment extends Fragment{

    private String planName;

    public EditFragment(String pName){
        planName = pName;
    }
    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        TextView textView = (TextView) container.findViewById(R.id.date);
        // textView.setText(stringBuffer.toString());
        // textView.setVisibility(View.VISIBLE);
        return inflater.inflate(R.layout.edit_fragment_layout, container,false);
    }
}
