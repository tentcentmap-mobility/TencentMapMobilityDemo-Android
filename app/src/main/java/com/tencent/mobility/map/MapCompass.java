package com.tencent.mobility.map;

import android.view.LayoutInflater;
import android.view.View;

import com.tencent.mobility.IModel;
import com.tencent.mobility.IPasView;
import com.tencent.mobility.R;
import com.tencent.mobility.location.bean.MapLocation;
import com.tencent.mobility.map.sensor.ISensor;
import com.tencent.mobility.model.PasLocationModel;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;

/**
 * 罗盘by marker
 *
 * @author mjzuo
 */
public class MapCompass extends TMapBase implements IPasView {

    CompassManager mBlueCompassHelper;
    float mDirection = 0;// 罗盘角度
    Marker compassMarker;
    LatLng lastLatlng;

    private PasLocationModel loModel;// 定位

    View view;

    @Override
    public void onChange(int eventType, Object obj) {
        switch (eventType) {
            case IModel.LOCATION :
                if(obj instanceof MapLocation) {
                    lastLatlng = new LatLng(((MapLocation) obj).getLatitude(), ((MapLocation) obj).getLongitude());
                    setPoi(lastLatlng);
                    addCompassMarker();
                    if(loModel != null)
                        loModel.stopLocation();
                }
                break;
        }
    }

    @Override
    protected MapView getMap() {
        if(view == null)
            view = LayoutInflater.from(this).inflate(R.layout.map_compass_by_marker, null);
        return view.findViewById(R.id.map_compass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束陀螺仪
        if(mBlueCompassHelper != null)
            mBlueCompassHelper.stopSensor();
        if(loModel != null)
            loModel.unregister(this);
        loModel = null;
    }

    @Override
    View getLaout() {
        return view;
    }

    @Override
    void handle() {
        // 陀螺仪helper
        mBlueCompassHelper = new CompassManager(this, new MySensorListener());
        mBlueCompassHelper.startSensor();
        // 定位
        getLo();
    }

    /**
     * 获取定位
     */
    private void getLo() {
        loModel = new PasLocationModel(this);
        loModel.register(this);
        loModel.postChangeLocationEvent();
    }

    private void addCompassMarker( ) {
        if(compassMarker == null)
            compassMarker = addMarker(lastLatlng, R.mipmap.blue_compass, 0);
    }

    class MySensorListener implements ISensor.IDirectionListener {
        @Override
        public void onDirection(float direction) {
            // 更新罗盘marker的角度
            mDirection = direction;
            if(compassMarker != null){
                compassMarker.setRotation(direction);
            }
        }
    }
}
