package com.example.cpwslogs.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.cpwslogs.MainActivity;
import com.example.cpwslogs.R;
import com.example.cpwslogs.interfaces.OnShedItemClickListener;
import com.example.cpwslogs.models.ShedLog;

import java.util.ArrayList;
import java.util.List;

public class LogListFragment extends Fragment {

    private OnShedItemClickListener mItemClickListener;

    public static LogListFragment newInstance(int shedIndex) {

        Bundle args = new Bundle();
        args.putInt("shedIndex", shedIndex);

        LogListFragment fragment = new LogListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LogListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_log_list, container, false);

        ListView logListView = view.findViewById(R.id.shedLogLisView);
        if (getArguments() != null) {
            final int shedIndex = getArguments().getInt("shedIndex");
            List<String> logStringList = new ArrayList<>();
            for (ShedLog log : MainActivity.logs) {
                if (log.getShed() == shedIndex) {
                    String logShowingString = log.getTreatment()
                            + " " + log.getDate()
                            + " " + log.getTemp()
                            + " " + log.getHumidity()
                            + " " + log.getAmmonia();
                    logStringList.add(logShowingString);
                }
            }

            ListAdapter adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, logStringList);
            logListView.setAdapter(adapter);

            Button goBackBtn = view.findViewById(R.id.shedLogListBackBtn);
            goBackBtn.setText("back to shed " + shedIndex);
            goBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onShedItemClicked(shedIndex);
                }
            });
        }

        return view;
    }

}
