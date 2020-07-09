package com.lynx.uclass.item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.lynx.R;

public class ItemActivity extends AppCompatActivity {

    /*
    public static void actionStart(Context context, Boolean islearn){
        Intent intent=new Intent(context, ItemActivity.class);
        intent.putExtra("islearn",islearn);
        Log.e("ItemActivity",islearn.toString());
        context.startActivity(intent);
    }

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Bundle mbundle=intent.getBundleExtra("bundle");
        boolean islearn=mbundle.getBoolean("islearn");
        /*
        Bundle bundle=new Bundle();
        boolean islearn =getIntent().hasExtra("islearn");
        Log.e("ItemActivity",String.valueOf(islearn));
        bundle.putBoolean("islearn",islearn);


         */
        setContentView(R.layout.item_activity);
        ItemFragment itemFragment=new ItemFragment();
        itemFragment.setArguments(mbundle);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, itemFragment)
                    .commitNow();
        }
    }
}