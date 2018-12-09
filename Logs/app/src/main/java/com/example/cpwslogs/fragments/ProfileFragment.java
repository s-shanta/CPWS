package com.example.cpwslogs.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.cpwslogs.MainActivity;
import com.example.cpwslogs.R;
import com.example.cpwslogs.interfaces.OnNavigationButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private String username, password, repeatPassword;

    private OnNavigationButtonClickListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            mListener = (OnNavigationButtonClickListener) context;
        } else {
            throw new AssertionError("listener not found");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final EditText userNameET = view.findViewById(R.id.profileUsernameET);
        final EditText passwordET = view.findViewById(R.id.profilePasswordET);
        final EditText repeatPasswordET = view.findViewById(R.id.profileRepeatPasswordET);

        Button saveBtn = view.findViewById(R.id.profileSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = userNameET.getText().toString();
                password = passwordET.getText().toString();
                repeatPassword = repeatPasswordET.getText().toString();
                mListener.onHomeButtonClicked();
            }
        });

        Button cancelBtn = view.findViewById(R.id.profileCancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHomeButtonClicked();
            }
        });

        return view;
    }

}
