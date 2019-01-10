package com.example.xavierdecazenove1.ass2.PackListViewClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xavierdecazenove1.ass2.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context m_context;
    private List<ModelItems> modelItemsList;
    private LayoutInflater inflater;

    public MyAdapter(Context m_context, List<ModelItems> modelItemsList) {
        this.m_context = m_context;
        this.modelItemsList = modelItemsList;
        this.inflater = LayoutInflater.from(m_context);
    }

    @Override
    public int getCount() {
        return modelItemsList.size();
    }

    @Override
    public ModelItems getItem(int position) {
        return modelItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.items, null);

        // Retourne les info à propos des items
        ModelItems currentItem = getItem(position);
        String itemsName = currentItem.getM_name();

        // Recupération des id
        TextView name = (TextView) view.findViewById(R.id.name);

        name.setText(itemsName);

        return view;
    }
}
