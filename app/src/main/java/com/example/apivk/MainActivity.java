package com.example.apivk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    public String[] scope = new String[]{VKScope.WALL, VKScope.FRIENDS};

    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;

    List<TaskModel> taskModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecyclerView();

        VKSdk.login(this, scope);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                VKRequest vkRequest = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "firstName,lastName,photo"));
                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKList list = (VKList) response.parsedModel;

                        for(int i = 0; i<list.size(); i++){
                            System.out.println("!!!!!!!!" + list.get(i).fields);
                            TaskModel taskModel = new TaskModel();
                            try {
                                taskModel.setName(list.get(i).fields.getString("first_name"));
                                taskModel.setLastName(list.get(i).fields.getString("last_name"));
                                taskModel.setPhoto(list.get(i).fields.getString("photo"));
                                taskModel.setOnline(list.get(i).fields.getString("online"));
                                taskModels.add(taskModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerAdapter.onChange(taskModels);

                    }
                });
            }

            @Override
            public void onError(VKError error) {

            }
        })) ;



    }

    public void initRecyclerView() {
        recyclerView = findViewById(R.id.list);
        recyclerAdapter = new RecyclerAdapter(this,taskModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
    }

}
