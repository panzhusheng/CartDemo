package com.example.cartdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private final List<CartBean> cartBeans=new ArrayList<>();
    private TextView total,edit;
    private Button pay;
    private CheckBox allSelect;
    private boolean isEditing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        total=findViewById(R.id.total);
        edit=findViewById(R.id.edit);
        pay=findViewById(R.id.pay);
        allSelect=findViewById(R.id.allSelect);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter=new CartAdapter(cartBeans,this);
        recyclerView.setAdapter(cartAdapter);

        cartAdapter.setOnChange(new CartAdapter.OnChange() {
            @Override
            public void change(List<CartBean> selects) {
                System.out.println(isEditing);
                if (selects.size()!=0){
                    if (isEditing){
                        pay.setText("删除（"+selects.size()+")");
                    }
                    else {
                        pay.setText("结算（"+selects.size()+")");

                    }
                }
                else {
                    if (isEditing){
                        pay.setText("删除");
                    }
                    else {
                        pay.setText("结算");
                    }
                }
                double price=0;
                for (CartBean cartBean:selects){
                    price+=cartBean.getPrice()*cartBean.getNumber();
                }
                total.setText(String.format("合计%1$.2f￥", price));
            }
        });
        getData();
        init();
    }
    private void getData(){
        cartBeans.clear();
        cartBeans.add(new CartBean("华硕天选4 锐龙版高性能电竞游戏本笔记本电脑(新R9-7940H 16G 512G RTX4060 2.5K 165Hz P3广色域)灰",
                "https://img12.360buyimg.com/n7/jfs/t1/130861/24/41158/159178/65b35971F353b32b3/43096338fff7c90b.jpg",false,1,7299));
        cartBeans.add(new CartBean("机械革命（MECHREVO）极光Pro 16英寸 i7HX 游戏本笔记本电脑(i7-13650HX 16G 1T RTX4060 165HZ 2.5K屏 双液金散热)",
                "https://img14.360buyimg.com/n7/jfs/t1/170706/22/37325/128461/65af291dF00402881/04a0f79b17eaf2d1.jpg",false,1,6699));
        cartBeans.add(new CartBean("机械革命（MECHREVO）蛟龙16K 16英寸游戏电竞笔记本电脑（R7-7735H 16G 512G RTX4060 165HZ 2.5K）",
                "https://img12.360buyimg.com/n7/jfs/t1/171355/10/40973/174429/64f92f28Fed75a521/5ae9d737cfec80ee.jpg",false,1,5489));
        cartBeans.add(new CartBean("联想（Lenovo）拯救者R7000 游戏笔记本电脑 15.6英寸超能电竞本(R7-7840H 16G 512G RTX4060显卡 高刷高色域屏)",
                "https://img13.360buyimg.com/n7/jfs/t1/229477/32/11417/131863/65b3ad38Fe3699e37/f9ee4a02757447cd.jpg",false,1,6799));

        cartAdapter.notifyDataSetChanged();
    }

    private void init(){
        allSelect.setOnCheckedChangeListener((compoundButton, b) -> {
            for (CartBean cartBean:cartBeans){
                cartBean.setSelect(b);
            }
            cartAdapter.notifyDataSetChanged();
        });
        pay.setOnClickListener(view -> {
            if (isEditing){
                if (cartAdapter.getSelects()!=null&&cartAdapter.getSelects().size()!=0){
                    for (CartBean cartBean:cartAdapter.getSelects()){
                        cartBeans.remove(cartBean);
                    }
                    cartAdapter.notifyDataSetChanged();
                    pay.setText("删除");
                    Toast.makeText(MainActivity.this,"已删除",Toast.LENGTH_SHORT).show();
                    cartAdapter.getSelects().clear();
                }
                else {
                    Toast.makeText(MainActivity.this, "没有选择商品", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if (cartAdapter.getSelects()!=null&&cartAdapter.getSelects().size()!=0){
                    for (CartBean cartBean:cartAdapter.getSelects()){
                        cartBeans.remove(cartBean);
                    }
                    cartAdapter.notifyDataSetChanged();
                    cartAdapter.getSelects().clear();
                    pay.setText("结算");
                    total.setText("合计0.00￥");
                    allSelect.setChecked(false);
                    Toast.makeText(MainActivity.this, "结算成功", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "没有选择商品", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit.setOnClickListener(view -> {
            if (!isEditing){
                edit.setText("取消编辑");
                if (cartAdapter.getSelects().size()!=0){
                    pay.setText("删除（"+cartAdapter.getSelects().size()+")");
                }
                else {
                    pay.setText("删除");
                }
            }
            else {
                edit.setText("编辑");
                if (cartAdapter.getSelects().size()!=0){
                    pay.setText("结算（"+cartAdapter.getSelects().size()+")");
                }
                else {
                    pay.setText("结算");
                }
            }
            isEditing=!isEditing;
        });
    }
}