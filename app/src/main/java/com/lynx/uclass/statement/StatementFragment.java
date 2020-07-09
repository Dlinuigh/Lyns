package com.lynx.uclass.statement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lynx.R;


public class StatementFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statement, container, false);
        final Bundle bundle=this.getArguments();
        if(bundle!=null) {
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            View x_view= view.findViewById(R.id.statement_fragment);
            x_view.setVisibility(View.VISIBLE);
            TextView titleview= view.findViewById(R.id.statement_title);
            TextView contentview= view.findViewById(R.id.statement_content);
            titleview.setText(title);
            contentview.setText(content);
        }
        return view;
    }
}