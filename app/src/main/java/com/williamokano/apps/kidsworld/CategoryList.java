package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eva on 2015/4/11.
 */
public class CategoryList extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ListView listView;
        listView = (ListView)this.findViewById(R.id.listview);

        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.category_item,new String[]{"title","img"},new int[]{R.id.title,R.id.img});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView lv = (ListView)parent;
                HashMap<String,Object> map = (HashMap<String,Object>)lv.getItemAtPosition(position);
                if (map.get("title")=="Shape") {
                    Intent MainGameIntent = new Intent(CategoryList.this, SelectedItems.class);
                    startActivity(MainGameIntent);
                }else if(map.get("title")=="Color") {
                    Intent MainGameIntent1 = new Intent(CategoryList.this, SelectedItems.class);
                    startActivity(MainGameIntent1);
                }else if(map.get("title")=="Animal"){
                    Intent MainGameIntent2 = new Intent(CategoryList.this, SelectedItems.class);
                    startActivity(MainGameIntent2);
                }
            }});
    }

    private List<Map<String,Object>> getData(){
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("title","Shape");
        map.put("img",R.drawable.triangle);
        list.add(map);

        map = new HashMap<String,Object>();
        map.put("title","Color");
        map.put("img",R.drawable.pink);
        list.add(map);

        map = new HashMap<String,Object>();
        map.put("title","Animal");
        map.put("img",R.drawable.cat);
        list.add(map);

        return list;
    }
}
