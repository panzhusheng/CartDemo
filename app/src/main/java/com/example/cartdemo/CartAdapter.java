package com.example.cartdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {
    private final List<CartBean> list;
    private final Context context;
    private final List<CartBean> selects=new ArrayList<>();

    public CartAdapter(List<CartBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_cart,null,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        CartBean cartBean =list.get(position);
        holder.name.setText(cartBean.getName());
        holder.number.setText(String.valueOf(cartBean.getNumber()));
        holder.price.setText(String.format("%1$.2f￥", cartBean.getPrice()));
        holder.reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n;
                n = Integer.parseInt(holder.number.getText().toString());
                if (n >1){
                    n = n -1;
                    holder.number.setText(String.valueOf(n));
                    cartBean.setNumber(n);
                }
                else {
                    Toast.makeText(context,"最少选择一件",Toast.LENGTH_SHORT).show();
                }
                updateItem();
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n;
                n = Integer.parseInt(holder.number.getText().toString());
                n = n + 1;
                cartBean.setNumber(n);
                holder.number.setText(String.valueOf(n));
                updateItem();
            }
        });
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartBean.setSelect(b);
                updateItem();
            }
        });
        holder.checkBox.setChecked(cartBean.isSelect());
        Glide.with(context).load(cartBean.getCover()).into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        ImageView cover;
        TextView name,number,reduce,add,price;
        public Holder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox);
            cover=itemView.findViewById(R.id.cover);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.tv_num);
            reduce=itemView.findViewById(R.id.tv_reduce);
            add=itemView.findViewById(R.id.tv_add);
            price=itemView.findViewById(R.id.price);
        }
    }
    private void updateItem(){
        selects.clear();
        for (CartBean cartBean:list){
            if (cartBean.isSelect()){
                selects.add(cartBean);
            }
        }
        onChange.change(selects);
    }
    public OnChange onChange;

    public void setOnChange(OnChange onChange) {
        this.onChange = onChange;
    }

    public List<CartBean> getSelects() {
        return selects;
    }
    //条目改变-接口回调
    public interface OnChange{
        void change(List<CartBean> selects);
    }
}
