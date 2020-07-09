package com.lynx.uclass.item;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lynx.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment {

    private View view;
    private Boolean islearn;
    private int times;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle bundle=this.getArguments();
        times++;
        if(bundle!=null) {
            islearn = bundle.getBoolean("islearn");
        }else{
            Log.e("ItemFragment","bundle is null");
            islearn=true;
        }
        Log.e("ItemFragment",islearn.toString());
        view=inflater.inflate(R.layout.item_fragment, container, false);
        init_env();
        Log.e("ItemFragment", String.valueOf(times));
        return view;
    }

    //@Override
    //public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    //    super.onActivityCreated(savedInstanceState);
    //}

    private String loadConfig(String filename) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = requireActivity().getAssets().open(filename);
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
    private void init_env(){
        String filename;
        if(islearn) {
            filename= "learn.json";
        }else {
            filename = "practice.json";
        }
        String json=loadConfig(filename);
        Gson gson=new Gson();
        ArrayList<lpclass> arrayList=gson.fromJson(json,new TypeToken<List<lpclass>>(){}.getType());
        assert arrayList != null;
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.arrayList);
        Log.e("ItemFragment", recyclerView.toString());
        ItemAdapter lpclassAdapter = new ItemAdapter(getActivity(), arrayList, islearn);
        recyclerView.setAdapter(lpclassAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL));
        Log.e("ItemFragment","finish init environment");
    }
}

