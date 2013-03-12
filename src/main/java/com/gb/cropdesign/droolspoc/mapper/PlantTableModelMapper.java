package com.gb.cropdesign.droolspoc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gb.cropdesign.droolspoc.PlantTableModel;


public class PlantTableModelMapper implements RowMapper<PlantTableModel> {

	@Override
	public PlantTableModel mapRow(ResultSet resultSet, int rowCount) throws SQLException {
		PlantTableModel plantTableModel = new PlantTableModel(resultSet.getString("name")
				,resultSet.getString("rpd_nr") 
				, resultSet.getBoolean("conform"), resultSet.getBoolean("transgene"));
		
		return plantTableModel;
	}

}
