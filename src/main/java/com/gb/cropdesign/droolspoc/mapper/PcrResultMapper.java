package com.gb.cropdesign.droolspoc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.gb.cropdesign.droolspoc.PcrResult;
import com.gb.cropdesign.droolspoc.PcrTarget;
import com.gb.cropdesign.droolspoc.Plant;

public class PcrResultMapper implements RowMapper<PcrResult> {

	@Override
	public PcrResult mapRow(ResultSet arg0, int arg1) throws SQLException {
		
		return new PcrResult(new Plant( arg0.getString("plant_name") , "" )
			, PcrTarget.valueOf(arg0.getString("pcr_target" ))
			, arg0.getBoolean("is_test_gene_present") );
	}

}
