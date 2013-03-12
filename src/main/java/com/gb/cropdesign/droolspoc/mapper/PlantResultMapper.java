package com.gb.cropdesign.droolspoc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gb.cropdesign.droolspoc.PlantResult;

public class PlantResultMapper implements RowMapper<PlantResult> {

	@Override
	public PlantResult mapRow(ResultSet resultSet, int rowCount)
			throws SQLException {
		return new PlantResult(resultSet.getString("plant_name")
				, resultSet.getString("pcr_target")
				, resultSet.getBoolean("result"));
	}


}
