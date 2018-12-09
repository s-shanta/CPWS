package com.example.cpwslogs.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cpwslogs.MainActivity;
import com.example.cpwslogs.R;
import com.example.cpwslogs.interfaces.OnShedItemClickListener;

public class HomeFragment extends Fragment implements View.OnClickListener {


    private OnShedItemClickListener mItemClickListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            mItemClickListener = (OnShedItemClickListener) context;
        } else {
            throw new AssertionError("listener not found");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button button0 = view.findViewById(R.id.shedButton0);
        button0.setOnClickListener(this);

        Button button1 = view.findViewById(R.id.shedButton1);
        button1.setOnClickListener(this);

        Button button2 = view.findViewById(R.id.shedButton2);
        button2.setOnClickListener(this);

        Button button3 = view.findViewById(R.id.shedButton3);
        button3.setOnClickListener(this);

        Button button4 = view.findViewById(R.id.shedButton4);
        button4.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int index;

        switch (v.getId()) {
            case R.id.shedButton0:
                index = 0;
                break;
            case R.id.shedButton1:
                index = 1;
                break;
            case R.id.shedButton2:
                index = 2;
                break;
            case R.id.shedButton3:
                index = 3;
                break;
            case R.id.shedButton4:
                index = 4;
                break;

            default:
                index = 0;
        }

        mItemClickListener.onShedItemClicked(index);

    }
}
