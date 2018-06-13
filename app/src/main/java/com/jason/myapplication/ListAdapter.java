package com.jason.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jason.jasonimageutil.ImageLoadCallBack;
import com.jason.jasonimageutil.JasonImageUtil;
import com.jason.jasonimageutil.ReadImageResult;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    Context context;
    String[] address;

    public ListAdapter(Context context, String[] address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ImageView imageView = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list, null);
            imageView = convertView.findViewById(R.id.image);
            convertView.setTag(imageView);
        } else {
            imageView = (ImageView) convertView.getTag();
        }
        JasonImageUtil.with(context).setImageView(imageView).setPath(address[position % address.length]).setGif(true).setImageLoadCallBack(new ImageLoadCallBack() {
            @Override
            public void onStart(ImageView imageView) {

            }

            @Override
            public void onLoadCache(ImageView imageView, ReadImageResult result) {
                Log.e("RESULT", position + "==>" + result.getFrameList().size());
            }

            @Override
            public void onFinish(ImageView imageView, ReadImageResult result) {
                Log.e("RESULT", position + "==>" + result.getFrameList().size());
            }

            @Override
            public void onFailed(ImageView imageView, ReadImageResult result) {

            }
        }).load();

        return convertView;
    }
}
