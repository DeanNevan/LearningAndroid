package com.example.learningandroid.combined.activity;

import static com.baidu.mapapi.map.LogoPosition.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.learningandroid.R;

public class FragmentSeller extends Fragment {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;

    private MarkerOptions markerOptionsIcon;
    private TextOptions textOptionsText;
    private Marker markerIcon;
    private Text markerText;

    LatLng centerPoint =  new LatLng(22.258758, 113.542017);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller, container);
        mMapView = view.findViewById(R.id.map_view);

        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //设置logo
        mMapView.setLogoPosition(logoPostionCenterTop);


        LatLng latLng = mBaiduMap.getMapStatus().target;
        //准备 marker 的图片
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.school_small);
        //准备 marker option 添加 marker 使用
        markerOptionsIcon = new MarkerOptions().icon(bitmap).position(centerPoint);
        textOptionsText = new TextOptions().text("暨南大学珠海校区").fontSize(32).bgColor(R.color.black).fontColor(R.color.purple_200).position(centerPoint);
        //获取添加的 marker 这样便于后续的操作
        markerIcon = (Marker) mBaiduMap.addOverlay(markerOptionsIcon);
        markerIcon.setPosition(centerPoint);

        markerText = (Text) mBaiduMap.addOverlay(textOptionsText);
        markerText.setPosition(centerPoint);

        //对 marker 添加点击相应事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(getContext(), "Marker被点击了！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //设定中心点坐标

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(centerPoint)
                //放大地图到20倍
                .zoom(20)
                .build();

        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

}
