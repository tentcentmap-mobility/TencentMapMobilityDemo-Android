package com.tencent.mobility.location.bean;

import com.tencent.map.geolocation.TencentLocation;

public class TencentParseLocation extends ParseLocation<TencentLocation> {

    public MapLocation paseLocation(TencentLocation location){

        MapLocation mapLocation = new MapLocation();
        mapLocation.setLatitude(location.getLatitude());
        mapLocation.setLongitude(location.getLongitude());
        mapLocation.setCityCode(location.getCityCode());
        mapLocation.setProvider(location.getProvider());
        mapLocation.setBearing(location.getBearing());
        mapLocation.setCityCode(location.getCityCode());
        return mapLocation;
    }
}
