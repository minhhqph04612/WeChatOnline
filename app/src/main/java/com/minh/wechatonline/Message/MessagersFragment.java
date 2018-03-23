package com.minh.wechatonline.Message;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minh.wechatonline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagersFragment extends Fragment {


    public MessagersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messagers, container, false);
    }

}
