package com.example.dethuchi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class MainActivity extends AppCompatActivity {
    public EditText edSearch;
    public ListView lstThuChi;
    public TextView tvSoTien;
    public DatabaseTaxi db;
    public Adapter ListAdapter;
    public ArrayList<ThuChi> ThuChiList = new ArrayList<>();
    public int SelectedID;
    public FloatingActionButton btnAdd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edSearch = findViewById(R.id.edSearch);
        lstThuChi = findViewById(R.id.lvThuChi);
        tvSoTien = findViewById(R.id.tvSoTien);
        btnAdd = findViewById(R.id.btnAdd);
        db = new DatabaseTaxi(this, "Sqlite_19112002", null, 2);
//        db.addContact(new ThuChi(1,"Luong", "11/12/2023",10000,1));
//        db.addContact(new ThuChi(2,"Mua sam", "23/04/2023",10000,0));
//        db.addContact(new ThuChi(3,"An uong", "11/02/2023",10000,0));
        ThuChiList = db.getAllContact();
        int soDu=0;
        for(ThuChi thuChi: ThuChiList){
            if(thuChi.isLogic()==1)
                soDu+= thuChi.getSoTien();
            else
                soDu-= thuChi.getSoTien();
        }
        tvSoTien.setText(String.valueOf(soDu));
        Collections.sort(ThuChiList, new Comparator<ThuChi>() {
            @Override
            public int compare(ThuChi t1, ThuChi t2) {
                return t1.getNgayThang().compareTo(t2.getNgayThang());
            }
        });
        ListAdapter = new com.example.dethuchi.Adapter(ThuChiList, this);
        lstThuChi.setAdapter(ListAdapter);
        lstThuChi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                SelectedID = i;
                return false;
            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListAdapter.getFilter().filter(charSequence.toString());
                ListAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ThemActivity.class);
                startActivityForResult(intent,100);
            }
        });
        registerForContextMenu(lstThuChi);
    }
    public void thongBao(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(s);
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Toast.makeText(MainActivity.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
                ThuChi contactDelete = ThuChiList.get(SelectedID);
                //Toast.makeText(MainActivity.this,String.valueOf(contactDelete.getId()),Toast.LENGTH_SHORT).show();
                db.deleteContact(contactDelete.getId());
                ThuChiList = db.getAllContact();
                ListAdapter = new Adapter(ThuChiList, MainActivity.this);
                lstThuChi.setAdapter(ListAdapter);
            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_context, menu);
        menu.setHeaderTitle("Select Option");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        ThuChi c1 = ThuChiList.get(SelectedID);
        switch (item.getItemId()) {
            case R.id.mnuSua:
                break;
            case R.id.mnuXoa:
                ThuChi thuChi = ThuChiList.get(SelectedID);
                thongBao( String.format("Bạn có chắc muốn xóa khoản %d\nNgày tháng: %s\nKhoản thu: %s\nSố tiền: %d",
                        thuChi.isLogic(), thuChi.getNgayThang(), thuChi.getTenKhoan(), thuChi.getSoTien()));
                break;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Bundle b = data.getExtras();
        String NgayThang = b.getString("NgayThang");
        String TenKhoan = b.getString("TenKhoan");
        int SoTien=b.getInt("SoTien");
        int Logic=b.getInt("Logic");
        ThuChiList = db.getAllContact();
        ThuChi taxiModel = new ThuChi(ThuChiList.size()+1, TenKhoan, NgayThang, SoTien, Logic);
        //Trường hợp thêm
        if (requestCode == 100 && resultCode == 150) {

            db.addContact(taxiModel);
            ThuChiList = db.getAllContact();
            ListAdapter = new Adapter(ThuChiList, this);
            lstThuChi.setAdapter(ListAdapter);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}