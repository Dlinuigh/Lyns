package com.lynx.uclass.item;

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
import com.lynx.uclass.mutiplechoice.choice;
import com.lynx.uclass.mutiplechoice.ChoiceActivity;
import com.lynx.uclass.programming.ProgramActivity;
import com.lynx.uclass.programming.program;
import com.lynx.uclass.statement.StatementActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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
                        String filename = "choice_learn.json";
                        String json=loadConfig(filename);
                        Gson gson=new Gson();
                        ArrayList<choice> arrayList=gson.fromJson(json,new TypeToken<List<choice>>(){}.getType());
                        assert arrayList != null;
                        Log.e("ItemAdapter","arrayList's size is: "+ arrayList.size());
                        /*
                        下面这个循环很重要，帮助确定列表里面是否存在某个值。
                         */
                        boolean hasId=false;
                        choice choice= new choice();
                        for(choice i: arrayList){
                            if(i.getId()==key){
                                hasId=true;
                                choice=i;
                                break;
                            }
                        }
                        if(hasId){
                            Intent intent=new Intent(context, ChoiceActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putStringArrayList("choices",choice.getChoices());
                            bundle.putStringArrayList("answer",choice.getAnswer());
                            bundle.putString("title",lpclass.getTitle());
                            bundle.putString("content",lpclass.getContent());
                            intent.putExtra("bundle",bundle);
                            context.startActivity(intent);
                        }else{
                            Intent intent=new Intent(context, StatementActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("title",lpclass.getTitle());
                            bundle.putString("content",lpclass.getContent());
                            intent.putExtra("bundle",bundle);
                            context.startActivity(intent);
                        }
                    }else{
                        String filename = "choice_practice.json";
                        String json=loadConfig(filename);
                        Gson gson=new Gson();
                        ArrayList<choice> choices=gson.fromJson(json,new TypeToken<List<choice>>(){}.getType());
                        assert choices != null;
                        boolean hasId=false;
                        choice choice= new choice();
                        for(choice i: choices){
                            if(i.getId()==key){
                                hasId=true;
                                choice=i;
                                break;
                            }
                        }
                        if(hasId){
                            Intent intent=new Intent(context,ChoiceActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putStringArrayList("choices",choice.getChoices());
                            bundle.putStringArrayList("answer",choice.getAnswer());
                            bundle.putString("title",lpclass.getTitle());
                            bundle.putString("content",lpclass.getContent());
                            intent.putExtra("bundle",bundle);
                            context.startActivity(intent);
                        }else{
                            filename = "program.json";
                            json=loadConfig(filename);
                            gson=new Gson();
                            ArrayList<program> programs=gson.fromJson(json,new TypeToken<List<program>>(){}.getType());
                            assert programs != null;
                            boolean hasid=false;
                            program program= new program();
                            for(program i: programs){
                                if(i.getId()==key){
                                    hasid=true;
                                    program=i;
                                    break;
                                }
                            }
                            if(hasid){
                                Intent intent=new Intent(context, ProgramActivity.class);
                                Bundle bundle=new Bundle();
                                bundle.putStringArrayList("values",program.getArgument_value());
                                bundle.putStringArrayList("names",program.getArgument_name());
                                bundle.putString("title",lpclass.getTitle());
                                bundle.putString("content",lpclass.getContent());
                                bundle.putString("answer",program.getAnswer());
                                bundle.putInt("id",program.getId());
                                intent.putExtra("bundle",bundle);
                                context.startActivity(intent);
                            }else{
                                Random random=new Random();
                                int i=random.nextInt(programs.size());
                                program new_program=programs.get(i);
                                    Intent intent=new Intent(context, ProgramActivity.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putStringArrayList("values",new_program.getArgument_value());
                                    bundle.putStringArrayList("names",new_program.getArgument_name());
                                    bundle.putString("title",lpclass.getTitle());
                                    bundle.putString("content",lpclass.getContent());
                                    bundle.putString("answer",new_program.getAnswer());
                                    intent.putExtra("bundle",bundle);
                                    context.startActivity(intent);
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