package com.example.firebaserdb;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText empid, empname, email, dept, phone ;

    Button create, show;
    TextView result;
    Employee emp;

    DatabaseReference db;

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

        emp = new Employee();
        db = FirebaseDatabase.getInstance().getReference();

        create.setOnClickListener(v -> {
            emp.setEmpid(Integer.parseInt(empid.getText().toString()));
            emp.setEmpname(empname.getText().toString());
            emp.setEmail(email.getText().toString());
            emp.setDepartment(dept.getText().toString());
            emp.setPhone(Long.parseLong(phone.getText().toString()));

            db.child("employee").child(empid.getText().toString()).setValue(emp).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this,"success",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();

                    }
                }
            });
        });

        show.setOnClickListener(v -> {
            db.child("employee").child(empid.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        Employee emp = task.getResult().getValue(Employee.class);
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