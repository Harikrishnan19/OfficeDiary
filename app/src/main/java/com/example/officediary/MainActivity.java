package com.example.officediary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.officediary.adapters.EmployersAdapter;
import com.example.officediary.model.Employer;
import com.example.officediary.services.ApiClient;
import com.example.officediary.services.EmployerApi;
import com.example.officediary.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements EmployersAdapter.CardClickListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<Employer> employerList;
    private EmployersAdapter employersAdapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    private LinearLayout noInternetLayout;
    private Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_circular_emp_list);
        recyclerView = findViewById(R.id.rv_emp_list);
        noInternetLayout = findViewById(R.id.no_internet_connection_layout);
        retry = findViewById(R.id.btn_retry);

        initRecyclerView();
        if(Utils.isConnected(this)){
            fetchEmployeeList();
        } else {
            showNoInternet();
        }

        retry.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(Utils.isConnected(MainActivity.this)){
                hideNoInternet();
                fetchEmployeeList();
            } else {
                Toast.makeText(MainActivity.this, "Please turn on data / wifi. Still not connected to internet.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        employersAdapter = new EmployersAdapter(this, new ArrayList<Employer>(), this);
        recyclerView.setAdapter(employersAdapter);
    }

    private void showNoInternet(){
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    private void hideNoInternet(){
        noInternetLayout.setVisibility(View.GONE);
    }

    private void fetchEmployeeList(){

        // progress bar on
        progressBar.setVisibility(View.VISIBLE);

        EmployerApi services = ApiClient.getRetrofitInstance().create(EmployerApi.class);
        Call<List<Employer>> employeesRequest = services.fetchEmployers();
        employeesRequest.enqueue(new Callback<List<Employer>>() {
            @Override
            public void onResponse(Call<List<Employer>> call, Response<List<Employer>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: Employees list fetch unsuccessfully. Status Code: " + response.code());
                }
                employerList = response.body();
                employersAdapter.setEmployersList(employerList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Employer>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCardItemClick(int position) {
        Intent intent = new Intent(this, EmployerDetailsActivity.class);
        intent.putExtra("empDetails", employerList.get(position));
        startActivity(intent);
    }
}