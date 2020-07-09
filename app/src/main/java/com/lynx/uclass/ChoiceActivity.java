package com.lynx.uclass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;

import com.lynx.R;

import java.util.ArrayList;

public class ChoiceActivity extends AppCompatActivity {
    Context context;
    /*
    天煞的 autostart，根本就是一个病态方法，总是出错，包容性不强，还是自己掌握原理比较好。
     */
    /*
    public static void autostart(Context context, ArrayList<String> choices,ArrayList<String> answer, String title, String content){
        Intent intent=new Intent(context,ChoiceActivity.class);
        intent.putStringArrayListExtra("choices",choices);
        intent.putStringArrayListExtra("answer",answer);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        context.startActivity(intent);
    }

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        Intent intent=getIntent();
        Bundle mbundle=intent.getBundleExtra("bundle");
        Log.e("ChoiceActivity",mbundle.toString());
        /*assert bundle != null;
        String title=bundle.getString("title");
        String content=bundle.getString("content");
        ArrayList<String> choices=bundle.getStringArrayList("choices");
        ArrayList<String> answer=bundle.getStringArrayList("answer");

         */
        /*
        String title=getIntent().getStringExtra("title");
        String content=getIntent().getStringExtra("content");
        ArrayList<String> choices=getIntent().getStringArrayListExtra("choices");
        ArrayList<String> answer=getIntent().getStringArrayListExtra("answer");

         */
        ChoiceFragment choiceFragment=new ChoiceFragment();
        //(ChoiceFragment)getSupportFragmentManager().findFragmentById(R.id.choice_fragment);
        /*assert choiceFragment!=null;
        assert choices != null;
        assert answer != null;
        Bundle mbundle = new Bundle();
        mbundle.putString("title",title);
        mbundle.putString("content",content);
        mbundle.putStringArrayList("choices",choices);
        mbundle.putStringArrayList("answer",answer);

         */
        assert choiceFragment != null;
        assert mbundle != null;
        choiceFragment.setArguments(mbundle);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.choice_fragment,choiceFragment);
        fragmentTransaction.commit();
    }
}