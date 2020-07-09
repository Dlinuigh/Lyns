package com.lynx.uclass.mutiplechoice;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lynx.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ChoiceFragment extends Fragment {

    private Context context;
    /*
    在Fragment中如果找不到上下文，也就是context，那么需要重写下面这个方法。
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice, container, false);
        final Bundle bundle=this.getArguments();
        if(bundle!=null){
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            final ArrayList<String> choices = bundle.getStringArrayList("choices");
            final ArrayList<String> answer = bundle.getStringArrayList("answer");

            /*
            Log.e("ChoiceFragment","answer: "+answer.size());
            Log.e("ChoiceFragment","choices: "+choices.size());
            Log.e("ChoiceFragment","title: "+title);
            Log.e("ChoiceFragment","content: "+content);

             */

            View x_view= view.findViewById(R.id.choice_fragment);
            x_view.setVisibility(View.VISIBLE);
            TextView titleview= view.findViewById(R.id.choice_title);
            TextView contentview= view.findViewById(R.id.choice_content);
            titleview.setText(title);
            contentview.setText(content);
            RadioGroup radioGroup= view.findViewById(R.id.choice_group);
            /*
            下面是一个动态添加选项的方法，在这里面设置选项的各个布局参数即可。
             */
            assert choices != null;
            for(int i = 0; i<choices.size(); i++){
                RadioButton tempButton = new RadioButton(getContext());
                tempButton.setText(choices.get(i));
                tempButton.setBackgroundColor(Color.WHITE);
                radioGroup.addView(tempButton, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            }

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                    final Button button=radioGroup.findViewById(checkedId);
                    Log.e("ChoiceFragment","checkedId is: "+checkedId);
                    assert answer != null;
                    for(String j:answer){
                        int i=Integer.parseInt(j);
                        if(button.getText().toString().equals(choices.get(i-1))){
                            button.setBackgroundColor(Color.GREEN);
                            Toast.makeText(context,"恭喜！选择正确。",Toast.LENGTH_SHORT).show();
                            /*
                            这里是一个延时写法，因为我的目的是让正确选项先变绿后变成无色。不正确的选项不变色，并且会Toast提示正确。
                             */
                            TimerTask task = new TimerTask() {
                               @Override
                               public void run() {
                                   button.setBackgroundColor(Color.WHITE);
                               }
                            };
                            Timer timer=new Timer();
                            timer.schedule(task,1000);
                        }
                    }

                }
            });
        }

        return view;
    }
}