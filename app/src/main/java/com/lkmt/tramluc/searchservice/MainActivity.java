package com.lkmt.tramluc.searchservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewmenu;
    ArrayList<Menu> arrMenu;
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        adapter = new MenuAdapter(this,R.layout.row_listview_menu,arrMenu);
        listViewmenu.setAdapter(adapter);

        listViewmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, arrMenu.get(position).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("NameService",arrMenu.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void anhxa(){
        listViewmenu = (ListView) findViewById(R.id.listview);
        arrMenu = new ArrayList<Menu>();

        arrMenu.add(new Menu("ATM",R.mipmap.atm));
        arrMenu.add(new Menu("Khu du lịch",R.mipmap.kvc1));
        arrMenu.add(new Menu("Quán ăn",R.mipmap.quanan1));
        arrMenu.add(new Menu("Cafe/Kem",R.mipmap.cf1));
        arrMenu.add(new Menu("Cửa hàng tiện lợi/Tạp hóa",R.mipmap.chtl1));
        arrMenu.add(new Menu("Khách sạn/Nhà nghỉ",R.mipmap.ks1));
        arrMenu.add(new Menu("Quán bar",R.mipmap.bar1));
        arrMenu.add(new Menu("Trung tâm thương mại",R.mipmap.tttm1));
        arrMenu.add(new Menu("Siêu thị",R.mipmap.sieuthi1));
        arrMenu.add(new Menu("Trạm xăng",R.mipmap.cayxang1));

    }
}
