package com.credit.korea.KoreaCredit.mypage.consume;


import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.credit.korea.KoreaCredit.MainActivity;
import com.credit.korea.KoreaCredit.R;

import java.util.ArrayList;


public class ListSelect extends FrameLayout implements AdapterView.OnItemSelectedListener{

    private int selectedIdx;
    private Spinner spinner;
    public ISelectListener delegate;

    public ListSelect(Context context)
	{
        super(context);
        View.inflate(context, R.layout.list_select,this);
        spinner = (Spinner) findViewById(R.id._spinner);


        spinner.setOnItemSelectedListener(this);


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedIdx=position;
        if(delegate!=null){
            delegate.onSelectedChange(this,selectedIdx);
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {


    }
    public void setData(ArrayList<String> selects, int _selectedIdx)
    {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.getInstence(), android.R.layout.simple_spinner_item, selects);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        selectedIdx=_selectedIdx;
        spinner.setSelection(selectedIdx);



    }
    public int getSelected()
    {
        return selectedIdx;
    }






    /////////////////////////////////////////









}
