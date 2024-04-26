package com.example.firebaserdb;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    EditText empid, empname, email, dept, phone ;

    Button create, show;
    TextView result;
    employee employee;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        empid = findViewById(R.id.empid);
        empname = findViewById(R.id.empname);

        email = findViewById(R.id.email);
        dept = findViewById(R.id.dept);
        phone = findViewById(R.id.phone);

        create = findViewById(R.id.create);
        show = findViewById(R.id.show);

        result = findViewById(R.id.result);

        employee = new employee();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee.setEmpid(Integer.parseInt(empid.getText().toString()));
                employee.setEmpname(empname.getText().toString());
                employee.setEmail(email.getText().toString());
                employee.setDepartment(dept.getText().toString());
                employee.setPhone(String.valueOf(Integer.parseInt(phone.getText().toString())));

                databaseReference.push().setValue(employee);
            }
        });

        employee = new employee();

        show.setOnClickListener(v -> {
            databaseReference.child("employee").child(empid.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        employee emp = task.getResult().getValue(employee.class);
                        result.setText(String.format("Emp ID: %s\nName: %s\nEmail: %s\nDepartment: %s\nPhone: %s",emp.getEmpid(),emp.getEmpname(),emp.getEmail(),emp.getDepartment(),emp.getPhone()));
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                    }
                }
            });
        });

    }
}