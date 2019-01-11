package com.sai.interviewtaskapp;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.sai.interviewtaskapp.Model.ApiData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListScreenActivity extends AppCompatActivity {

    RecyclerView rv_list;
    RequestQueue requestQueue;
    private String url = "https://interview-e18de.firebaseio.com/media.json?print=pretty";
    ArrayList<ApiData> apiDataArrayList = new ArrayList<>();

    LinearLayoutManager listManager;
    DataListAdapter dataListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        rv_list = findViewById(R.id.rv_list);
        requestQueue = Volley.newRequestQueue(this);
        listManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ApiCalling();
    }

    private void ApiCalling()
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                for(int i=0;i<response.length();i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        ApiData apiData = new ApiData();
                        apiData.setDescription(jsonObject.getString("description"));
                        apiData.setId(jsonObject.getString("id"));
                        apiData.setThumb(jsonObject.getString("thumb"));
                        apiData.setTitle(jsonObject.getString("title"));
                        apiData.setUrl(jsonObject.getString("url"));

                        apiDataArrayList.add(apiData);
                        Log.i("!!!", "Amb -->" + apiDataArrayList);
                        Log.i("!!!", "Amb -->" + apiDataArrayList.size());





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(apiDataArrayList!=null && apiDataArrayList.size()>0){
                    rv_list.setLayoutManager(listManager);
                    rv_list.setHasFixedSize(true);
                    dataListAdapter = new DataListAdapter(apiDataArrayList);
                    rv_list.setAdapter(dataListAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder>{

        ArrayList<ApiData> apiDataArrayList ;
        public DataListAdapter(ArrayList<ApiData> apiDataArrayList) {
            this.apiDataArrayList = apiDataArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_display, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

            Picasso.get().load(apiDataArrayList.get(position).getThumb()).fit().centerInside().into(holder.img_display);
            holder.tv_title.setText(apiDataArrayList.get(position).getTitle());
            holder.tv_description.setText(apiDataArrayList.get(position).getDescription());
            holder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("^^^","selected data --> "+apiDataArrayList.get(position).toString());
                    ApiData selectedItemList = apiDataArrayList.get(position);

                    Intent in = new Intent(ListScreenActivity.this, VideoDisplayActivity.class);
                    in.putExtra("title",  selectedItemList.getTitle());
                    in.putExtra("description",  selectedItemList.getDescription());
                    in.putExtra("thumb",  selectedItemList.getThumb());
                    in.putExtra("url",  selectedItemList.getUrl());
                    startActivity(in);
                }
            });
        }

        @Override
        public int getItemCount() {
            return apiDataArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tv_description,tv_title;
            ImageView img_display;
            LinearLayout ll_item;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                img_display = itemView.findViewById(R.id.img_display);
                tv_title = itemView.findViewById(R.id.tv_title);
                tv_description = itemView.findViewById(R.id.tv_description);
                ll_item = itemView.findViewById(R.id.ll_item);
            }
        }
    }


}
