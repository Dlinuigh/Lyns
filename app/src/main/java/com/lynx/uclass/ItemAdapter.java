package com.lynx.uclass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lynx.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    @SuppressLint("StaticFieldLeak")
    @NonNull
    private static Context context;
    private static ArrayList<lpclass> lpclassArrayList;
    private static Boolean islearn;

    public ItemAdapter(@NonNull Context context, ArrayList<lpclass> arrayList, Boolean islearn){
        Log.e("ItemAdapter","enter Adapter");
        ItemAdapter.context =context;
        lpclassArrayList=arrayList;
        ItemAdapter.islearn =islearn;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            textView=view.findViewById(R.id.items);
            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public void onClick(View v) {
                    lpclass lpclass=lpclassArrayList.get(getLayoutPosition());
                    int key=lpclass.getId();
                    if(islearn){
                        String filename = "choice.json";
                        String json=loadConfig(filename);
                        Gson gson=new Gson();
                        ArrayList<choice> arrayList=gson.fromJson(json,new TypeToken<List<choice>>(){}.getType());
                        assert arrayList != null;
                        Log.e("ItemAdapter","arrayList's size is: "+ arrayList.size());
                        for(choice i:arrayList){
                            if(i.getId()==key){
                                Intent intent=new Intent(context,ChoiceActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putStringArrayList("choices",i.getChoices());
                                bundle.putStringArrayList("answer",i.getAnswer());
                                bundle.putString("title",lpclass.getTitle());
                                bundle.putString("content",lpclass.getContent());
                                intent.putExtra("bundle",bundle);
                                context.startActivity(intent);
                            }else{

                            }
                        }
                    }else{
                        String filename = "choice.json";
                        ArrayList<choice> choiceArrayList=new ArrayList<>();
                        String json=loadConfig(filename);
                        Gson gson=new Gson();
                        ArrayList<choice> choices=gson.fromJson(json,new TypeToken<List<choice>>(){}.getType());
                        assert choices != null;
                        choiceArrayList.addAll(choices);
                        for(choice i:choiceArrayList){
                            if(i.getId()==key){

                            }else{
                                filename = "program.json";
                                ArrayList<program> programArrayList=new ArrayList<>();
                                json=loadConfig(filename);
                                gson=new Gson();
                                ArrayList<program> programs=gson.fromJson(json,new TypeToken<List<program>>(){}.getType());
                                for(program j:programs) {
                                    programArrayList.add(j);
                                }
                                for(program j:programArrayList){
                                    if(j.getId()==key){

                                    }else{

                                    }
                                }
                            }
                        }

                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context=parent.getContext();
        }
        //Log.e("ItemAdapter","init a view");
        View itemView = LayoutInflater.from(context).inflate(R.layout.lpcitems_view,parent,false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        lpclass a= lpclassArrayList.get(position);
        holder.textView.setText(a.getTitle());
    }

    @Override
    public int getItemCount() {
        return lpclassArrayList.size();
    }



    @SuppressLint("RestrictedApi")
    private static String loadConfig(String filename) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = Objects.requireNonNull(getActivity(context)).getAssets().open(filename);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            return bos.toString("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null)
                    bos.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}