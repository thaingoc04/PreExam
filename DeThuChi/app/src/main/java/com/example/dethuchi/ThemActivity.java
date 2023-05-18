package com.example.dethuchi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class ThemActivity extends AppCompatActivity {
    private EditText etNgayThang;
    private EditText etSoTien;
    private EditText etTenKhoan;
    private Button btnCancel;
    private Button btnThem;
    private RadioGroup rdoGroup;
    public int id;
    public int chooseRadio;

    public void thongBao(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(s);
        builder.show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them);

        etNgayThang = findViewById(R.id.edNgayThang);
        etSoTien = findViewById(R.id.edSoTien);
        etTenKhoan = findViewById(R.id.edTenKhoan);
        rdoGroup = findViewById(R.id.radioGroup);
        btnThem = findViewById(R.id.btnThem);
        btnCancel = findViewById(R.id.btnThoat);
        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rdoThu)
                    chooseRadio = 1;
                else
                    chooseRadio = 0;
            }
        });
        //Lay arraylist tu main activity
        //MainActivity mainActivity = new MainActivity();
        //Lấy intent từ MainActivity chuyển sang
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NgayThang = etNgayThang.getText().toString();
                String TenKhoan = etTenKhoan.getText().toString();
                int soTien = Integer.parseInt(etSoTien.getText().toString());
                int Logic = chooseRadio;
                thongBao(NgayThang+" "+TenKhoan+" "+soTien+" "+Logic);
                //String s = SoXe+" "+donGia+" "+quangDuong+" "+phanTram;
//                Toast.makeText(AddAndEditActivity.this, s, Toast.LENGTH_LONG).show();
                //thongBao(s);
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putString("NgayThang", NgayThang);
                b.putString("TenKhoan", TenKhoan);
                b.putInt("SoTien", soTien);
                b.putInt("Logic", Logic);
                intent.putExtras(b);
                setResult(150, intent);
                finish();
            }
        });
    }
}