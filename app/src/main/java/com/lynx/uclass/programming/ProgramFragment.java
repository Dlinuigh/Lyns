package com.lynx.uclass.programming;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lynx.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class ProgramFragment extends Fragment {
    private EditText editText;
    private Context context;
    private int id;
    private String filename;
    private String inputText;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context =getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle bundle=this.getArguments();
        if(bundle!=null){
            String title=bundle.getString("title");
            String content=bundle.getString("content");

            View x_view= requireActivity().findViewById(R.id.program_fragment);
            x_view.setVisibility(View.VISIBLE);

            TextView title_view=getActivity().findViewById(R.id.program_title);
            title_view.setText(title);

            TextView content_view=getActivity().findViewById(R.id.program_content);
            content_view.setText(content);

            FloatingActionButton floatingActionButton=getActivity().findViewById(R.id.program_run);

            id=bundle.getInt("id");
            Log.e("ProgramFragment","id: "+id);
            editText=getActivity().findViewById(R.id.program_edit);
            filename="id"+id+"program.py";
            inputText=null;
            try {
                inputText=load(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(inputText.length()!=0) {
                Log.e("ProgramFragment","inputText is not empty");
                editText.setText(inputText);
                editText.setSelection(inputText.length());
                //Toast.makeText(context, R.string.a, Toast.LENGTH_SHORT).show();
            }

            Log.e("ProgramFragment","editText: "+editText.getText().toString());
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("ProgramFragment","floating button is clicked");

                }
            });
            Button button1=getActivity().findViewById(R.id.program_edit_finish);
            Button button2=getActivity().findViewById(R.id.program_edit_destory);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("ProgramFragment","finish button1 clicked");
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.alert_text)
                            .setMessage(R.string.alert_message)
                            .setNegativeButton(R.string.alert_negative, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, R.string.alert_negative_message, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton(R.string.alert_positive, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String editFile = editText.getText().toString();
                                    Log.e("ProgramFragment","editText: "+editFile);
                                    save(editFile, filename);
                                    Toast.makeText(context, R.string.alert_positive_message, Toast.LENGTH_SHORT).show();
                                }
                            }).show();

                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    save("",filename);
                    Log.e("ProgramFragment","finish button2 clicked");
                }
            });
        }
       return inflater.inflate(R.layout.fragment_program,container,false);
    }

    public void save(String inputText, String fileName){
        FileOutputStream out;
        BufferedWriter writer = null;
        try{
            out=context.openFileOutput(fileName,Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public String load(String fileName) throws IOException {
        FileInputStream in;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=context.openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return  content.toString();
    }
}