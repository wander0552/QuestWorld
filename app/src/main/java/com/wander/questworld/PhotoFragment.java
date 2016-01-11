package com.wander.questworld;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.wander.Cache.SaveImage;
import com.wander.MyView.numberprogressbar.NumberProgressBar;
import com.wander.xutils.BtUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String URL = "param1";
    private static final String TEXT = "param2";

    // TODO: Rename and change types of parameters
    private String image_url;
    private String des;
    private ImageView photo;
    private NumberProgressBar progressBar;
    private View view;
    private Bitmap fullBitmap;
    private FrameLayout frameLayout;
    private PhotoViewAttacher mAttacher;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhotoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PhotoFragment newInstance(String param1, String param2) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putString(URL, param1);
        args.putString(TEXT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            image_url = getArguments().getString(URL);
            des = getArguments().getString(TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_photo, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.photo_frameLayout);
        frameLayout.setOnClickListener(this);
        TextView des_TextView = (TextView) view.findViewById(R.id.photo_text);
        photo = (ImageView) view.findViewById(R.id.photo_image);
        //设置图片的监听事件
        mAttacher = new PhotoViewAttacher(photo);

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });


        progressBar = (NumberProgressBar) view.findViewById(R.id.progress);
        ImageView download = (ImageView) view.findViewById(R.id.photo_download);
        downloadImage();
        if (!des.equals("null")) {
            des_TextView.setText(des);
        }
        download.setOnClickListener(this);
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause", "===onPause");
//        photo = null;
    }

    @Override
    public void onDestroyView() {
        Log.d("destroy", "-->onDestroyView");
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
            if (fullBitmap != null) {
//                fullBitmap.recycle();
                fullBitmap=null;
                System.gc();
            }
        }
    }

    void downloadImage() {
/*        try {
            image_url = URLEncoder.encode(image_url, "utf-8").replaceAll("\\+", "%20").replaceAll("%3A", ":").replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        BtUtils.getMasterPhoto(getActivity().getApplicationContext()).display(photo, image_url, new BitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
                Log.e("start", uri);
            }

            @Override
            public void onLoadCompleted(ImageView zoomImageView, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                fullBitmap = bitmap;
                Log.e("bitmap", fullBitmap.toString());
                if (fullBitmap != null) {
                    photo.setImageBitmap(fullBitmap);
                }
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
            }

            @Override
            public void onLoadFailed(ImageView zoomImageView, String s, Drawable drawable) {

            }

            @Override
            public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
                progressBar.setProgress((int) (100 * current / total));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_download:
                SaveImage.saveBitmap(fullBitmap, getActivity().getApplicationContext());
                break;
        }
    }

}
