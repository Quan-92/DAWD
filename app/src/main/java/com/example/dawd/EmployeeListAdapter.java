package com.example.dawd;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EmployeeListAdapter extends BaseAdapter {
    private Activity context;
    List<Employee> employees;
    SQLiteDatabaseHandle db;

    public EmployeeListAdapter(Activity context, List employees, SQLiteDatabaseHandle db) {
        this.context = context;
        this.employees= employees;
        this.db=db;
    }

    public static class ViewHolder
    {
        TextView textViewName;
        TextView textViewDesignation;
        TextView textViewSalary;
    }
    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            row = inflater.inflate(R.layout.list_item, null, true);

            vh.textViewName = row.findViewById(R.id.textViewName);
            vh.textViewDesignation = row.findViewById(R.id.textViewDesignation);
            vh.textViewSalary = row.findViewById(R.id.textViewSalary);

            row.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Employee employee = employees.get(position);
        vh.textViewName.setText(employee.getName());
        vh.textViewDesignation.setText(employee.getDesignation());
        vh.textViewSalary.setText(String.valueOf(employee.getSalary()));
        Log.d("employee", employee.toString());
        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {
        return employees.size();
    }

    public void refreshData(List<Employee> employees){
        this.employees = employees;
        notifyDataSetChanged();
    }
}