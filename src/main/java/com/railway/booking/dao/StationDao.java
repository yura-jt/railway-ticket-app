package com.railway.booking.dao;

import com.railway.booking.entity.Station;
import com.railway.booking.entity.StationType;

import java.util.List;
import java.util.Optional;

public interface StationDao extends CrudDao<Station> {
    List<Station> findAllStationsByTrainId(Integer id);

    Optional<Station> getStationByTrainAndType(Integer trainId, StationType type, String name);

    Integer getStationDistance(Integer trainId, StationType type, String name);
}