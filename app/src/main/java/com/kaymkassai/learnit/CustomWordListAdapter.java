package com.kaymkassai.learnit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomWordListAdapter extends ArrayAdapter<HskModel> {

    private ArrayList<HskModel> dataSet;
    Context mContext;

    //View lookup cache

    private static class ViewHolder{
        TextView txtId;
        TextView txtCharacter;
        TextView txtPinyin;
        TextView txtMeaning;
    }

    public CustomWordListAdapter(ArrayList<HskModel> data, Context context){
        super(context, R.layout.hskrow_item, data);
        this.dataSet = data;
        this.mContext = context;

    }


    private int lastPosition = -1;


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the data item for this position
        HskModel hskModel = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.hskrow_item, parent, false);

            viewHolder.txtId = (TextView) convertView.findViewById(R.id.txtId);
            viewHolder.txtCharacter = (TextView) convertView.findViewById(R.id.txtCharacter);
            viewHolder.txtPinyin = (TextView) convertView.findViewById(R.id.txtPinyin);
            viewHolder.txtMeaning = (TextView) convertView.findViewById(R.id.txtMeaning);

            result = convertView;
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;


        viewHolder.txtId.setText(hskModel.getId().toString());
        viewHolder.txtCharacter.setText(hskModel.getCharacter().toString());
        viewHolder.txtPinyin.setText(hskModel.getPinyin().toString());
        viewHolder.txtMeaning.setText(hskModel.getMeaning().toString());
        viewHolder.txtCharacter.setTag(position);

        //Return the completed view to render on screen
        return convertView;
    }
}
