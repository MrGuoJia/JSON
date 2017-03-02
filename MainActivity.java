package com.example.yls.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_1,btn_2,btn_4;
    private TextView mTextView;
    private Student[] stu_list;
    private String msg;
    private String install;
    /*
    * JsonObject与JSONObject中的方法不太一致，必须注意
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStudents();
        initViews();
    }

    private void initStudents() {
        Student s1=new Student(101,"张三",18);
        Student s2=new Student(102,"李四",19);
        Student s3=new Student(103,"王五",20);
        stu_list= new Student[]{s1, s2, s3};
    }


    private void initViews() {
        mTextView= (TextView) findViewById(R.id.tv);
        btn_1= (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               JSONArray student_array=new JSONArray();
                for(int i=0;i<stu_list.length;i++){
                    JSONObject stu_object=getStudentObject(stu_list[i]);
                    student_array.put(stu_object);
                }
                JSONObject jsonObject=new JSONObject();

                try {
                    jsonObject.put("StudentMessage",student_array);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                msg=jsonObject.toString();
                mTextView.setText(msg);

            }
        });

        btn_2= (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg==null){
                    Toast.makeText(MainActivity.this,"没有解析的信息，请按第一个按钮后才能解析",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    JSONObject object=new JSONObject(msg);
                    JSONArray array=object.getJSONArray("StudentMessage");
                    ArrayList<Student> stu_lists=new ArrayList<Student>();
                    for(int y=0;y<array.length();y++){
                      JSONObject j=array.getJSONObject(y);
                       int id=j.getInt("id") ;
                       String name=j.getString("name");
                        int age=j.getInt("age");
                        Student s=new Student(id,name,age);
                        stu_lists.add(s);
                    }
                    String id= String.valueOf(stu_lists.get(0).getId());
                    install=id+"  ";
                    String name=stu_lists.get(0).getName();
                    install+=name+"  ";
                    String age= String.valueOf(stu_lists.get(0).getAge());
                    install+=age+"  ";
                    for (int k=1;k<stu_lists.size();k++){
                         id= String.valueOf(stu_lists.get(k).getId());
                             install+=id+"  ";
                             name=stu_lists.get(k).getName();
                            install+=name+"  ";
                             age= String.valueOf(stu_lists.get(k).getAge());
                            install+=age+"   ";
                    }
                    mTextView.setText(install);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_4= (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str= "{\"id\":101,\"name\":\"jack\",\"age\":25}";;
                Gson gson=new Gson();
                Student s=gson.fromJson(str,Student.class);
                mTextView.setText(String.valueOf(s.getId())+"  "+s.getName()+"  "+String.valueOf(s.getAge())  );
            }
        });
    }

    private JSONObject getStudentObject(Student stu) {
        JSONObject stu_object=new JSONObject();
        try {
            stu_object.put("id",stu.getId());
            stu_object.put("name",stu.getName());
            stu_object.put("age",stu.getAge());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stu_object;

    }
}
