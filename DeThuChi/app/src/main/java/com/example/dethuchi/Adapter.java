package com.example.dethuchi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dethuchi.R;
import com.example.dethuchi.ThuChi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Adapter extends BaseAdapter implements Filterable  {
    private ArrayList<ThuChi> dataBackUp;
    // Nguồn dữ liệu cho dapter
    private ArrayList<ThuChi> data;
    // Ngữ cảnh của ứng dụng
    private Activity context;
    //Đối tượng phân tích layout
    private LayoutInflater inflater;
    public Adapter(){

    }
    public Adapter(ArrayList<ThuChi> data, Activity activity){
        this.data = data;
        this.context=activity;
        this.inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null){
            v = inflater.inflate(R.layout.activity_adapter,null);
        }
        TextView tvLogic = v.findViewById(R.id.tvLogic);
        ConstraintLayout layout = v.findViewById(R.id.bigLayout);
        if(data.get(i).isLogic()==1){
            layout.setBackgroundColor(Color.CYAN);
            tvLogic.setText("Thu");
        }
        else{
            layout.setBackgroundColor(Color.YELLOW);
            tvLogic.setText("Chi");
        }
        TextView tvNgay = v.findViewById(R.id.tvNgay);
        tvNgay.setText(data.get(i).getNgayThang());
        TextView tvSoTien = v.findViewById(R.id.tvTien);
        tvSoTien.setText(String.valueOf(data.get(i).getSoTien()));
        TextView tvTenKhoan = v.findViewById(R.id.tvTenKhoan);
        tvTenKhoan.setText(data.get(i).getTenKhoan());
        return v;
    }

    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                if(dataBackUp==null){
                    dataBackUp=new ArrayList<>(data);
                }
                if(charSequence==null||charSequence.length()==0){
                    fr.count=dataBackUp.size();
                    fr.values=dataBackUp;
                }
                else {
                    ArrayList<ThuChi> newData=new ArrayList<>();
                    for(ThuChi c:dataBackUp){
//                        if(c.getTongTien()>Float.parseFloat(charSequence.toString())){
                            newData.add(c);
//                        }
                    }
                    fr.count=newData.size();
                    fr.values=newData;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data=new ArrayList<ThuChi>();
                ArrayList<ThuChi> tmp = (ArrayList<ThuChi>) filterResults.values;
                for(ThuChi c: tmp)
                    data.add(c);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}
