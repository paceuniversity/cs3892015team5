package com.williamokano.apps.kidsworld;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.williamokano.apps.kidsworld.bll.CategoryBLL;
import com.williamokano.apps.kidsworld.models.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Eva on 2015/4/11.
 */
public class CategoryList extends Activity {

    Spinner spinner;
    String category;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        DBHelper dbHelper = new DBHelper(this);

        ArrayList<Category> categories = CategoryBLL.GetCategories(dbHelper);
        ArrayList<String> categoriesNames = new ArrayList<String>();

        for (Category i : categories) {
            categoriesNames.add(i.getName());
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setPrompt("Categories");

        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriesNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);

        final Button goButton = (Button) findViewById(R.id.goButton);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = (parent.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent MainGameIntent = new Intent(CategoryList.this, SelectedItems.class);

                switch (category) {

                    case "shapes":

                        MainGameIntent.putExtra("Key", 1);
                        break;

                    case "colors":

                        MainGameIntent.putExtra("Key", 2);
                        break;

                    case "animals":

                        MainGameIntent.putExtra("Key", 3);
                        break;

                }

                startActivity(MainGameIntent);
            }

        });

/*
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
                    MainGameIntent.putExtra("Key",1);
                    startActivity(MainGameIntent);
                }else if(map.get("title")=="Color") {
                    Intent MainGameIntent1 = new Intent(CategoryList.this, SelectedItems.class);
                    MainGameIntent1.putExtra("Key",2);
                    startActivity(MainGameIntent1);
                }else if(map.get("title")=="Animal"){
                    Intent MainGameIntent2 = new Intent(CategoryList.this, SelectedItems.class);
                    MainGameIntent2.putExtra("Key",3);
                    startActivity(MainGameIntent2);
                }
            }});
*/
        }

        private List<Map<String, Object>> getData () {
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", "Shape");
            map.put("img", R.drawable.triangle);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "Color");
            map.put("img", R.drawable.pink);
            list.add(map);

            map = new HashMap<String, Object>();
            map.put("title", "Animal");
            map.put("img", R.drawable.cat);
            list.add(map);

            return list;
        }
    }
