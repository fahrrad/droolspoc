package com.gb.cropdesign.droolspoc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gb.cropdesign.droolspoc.Plant;

public class PlantMapper implements RowMapper<Plant> {

	@Override
	public Plant mapRow(ResultSet resultSet, int arg1) throws SQLException {
		Plant plant = new Plant(resultSet.getString("plant_name")
				, resultSet.getString("rpd_nr"));
		return plant;
	}

}
