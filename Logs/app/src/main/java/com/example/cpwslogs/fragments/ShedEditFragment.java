package com.example.cpwslogs.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpwslogs.MainActivity;
import com.example.cpwslogs.R;
import com.example.cpwslogs.interfaces.OnNavigationButtonClickListener;
import com.example.cpwslogs.models.ShedLog;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ShedEditFragment extends Fragment {

    private EditText tempET, humidityET, ammoniaET;
    private AppCompatSpinner treatmentSpinner;

    private OnNavigationButtonClickListener mListener;

    public ShedEditFragment() {
        // Required empty public constructor
    }

    public static ShedEditFragment newInstance(int index) {

        Bundle args = new Bundle();
        args.putInt("shedItem", index);

        ShedEditFragment fragment = new ShedEditFragment();
        fragment.setArguments(args);
        return fragment;
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
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shed_edit, container, false);

        if (getArguments() != null) {
            final int shedIndex = getArguments().getInt("shedItem");
            TextView titleText = view.findViewById(R.id.shedEditTitleText);
            titleText.setText(String.format(Locale.getDefault(), "Shed %d", shedIndex));

            Button saveBtn = view.findViewById(R.id.shedEditSaveBtn);
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveLogs(shedIndex);
                }
            });

            Button logListBtn = view.findViewById(R.id.shedEditLogListShowBtn);
            logListBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onShowLogListButtonClicked(shedIndex);
                }
            });
        }

        tempET = view.findViewById(R.id.shedEditTempEditText);
        humidityET = view.findViewById(R.id.shedEditHumidityEditText);
        ammoniaET = view.findViewById(R.id.shedEditAmmoniaEditText);
        treatmentSpinner = view.findViewById(R.id.shedEditTreatmentSpinner);

        Button homeBtn = view.findViewById(R.id.shedEditHomeBtn);
        Button prevBtn = view.findViewById(R.id.shedEditPrevBtn);
        Button nextBtn = view.findViewById(R.id.shedEditNextBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHomeButtonClicked();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPreviousButtonClicked();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNextButtonClicked();
            }
        });

        return view;
    }

    private void saveLogs(int shed) {
        if (tempET != null && humidityET != null && ammoniaET != null) {
            String temp = tempET.getText().toString();
            String humidity = humidityET.getText().toString();
            String ammonia = ammoniaET.getText().toString();

            if (TextUtils.isEmpty(temp) || TextUtils.isEmpty(humidity) || TextUtils.isEmpty(ammonia)
                    || TextUtils.isEmpty((String) treatmentSpinner.getSelectedItem())) {
                Toast.makeText(getContext(), "Entry not saved as not all data entered.\nComplete all entries and try again.", Toast.LENGTH_SHORT).show();
            } else {
                ShedLog log = new ShedLog(
                        shed,
                        temp,
                        humidity,
                        ammonia,
                        (String) treatmentSpinner.getSelectedItem(),
                        new SimpleDateFormat("dd/m/yyyy", Locale.getDefault()).format(System.currentTimeMillis())
                );
                MainActivity.logs.add(log);
                MainActivity.isDataSaved = false;
                Toast.makeText(getContext(), "Entry saved.", Toast.LENGTH_SHORT).show();

                tempET.setText("");
                humidityET.setText("");
                ammoniaET.setText("");
                treatmentSpinner.setSelection(0);
            }
        }
    }
}
