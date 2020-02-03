package com.railway.booking.dao;

import com.railway.booking.entity.Station;

import java.util.List;

public interface StationDao extends CrudDao<Station> {
    List<Station> findAllStationsByTrainId(Integer id);
}
