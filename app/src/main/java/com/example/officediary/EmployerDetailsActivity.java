package com.example.officediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.officediary.model.Employer;

public class EmployerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Employer employerDetails;
    private TextView empId, empName, empEmail, empAddress, empPhoneNumber, empCompanyName, empCompanyWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_details);

        employerDetails = (Employer) getIntent().getSerializableExtra("empDetails");

        empId = findViewById(R.id.emp_id);
        empName = findViewById(R.id.emp_name);
        empAddress = findViewById(R.id.emp_address);
        empEmail = findViewById(R.id.emp_email);
        empPhoneNumber = findViewById(R.id.emp_phone_number);
        empCompanyName = findViewById(R.id.emp_company_name);
        empCompanyWebsite = findViewById(R.id.emp_company_website);

        if(employerDetails != null){
            empId.setText("" + employerDetails.getId());
            empName.setText(employerDetails.getName());
            empEmail.setText(employerDetails.getEmail());
            empAddress.setText("" + employerDetails.getAddress().getSuite() + ", "
                    + employerDetails.getAddress().getStreet()
                    + ", " + employerDetails.getAddress().getCity()
                    + " - " + employerDetails.getAddress().getZipcode());
            empPhoneNumber.setText(employerDetails.getPhone());
            empCompanyName.setText(employerDetails.getCompany().getName());
            empCompanyWebsite.setText(employerDetails.getWebsite());

            empEmail.setOnClickListener(this);
            empPhoneNumber.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.emp_phone_number){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + empPhoneNumber.getText().toString()));
            startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{empEmail.getText().toString()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose an Email Client"));
        }
    }
}