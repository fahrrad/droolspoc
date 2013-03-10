package com.gb.cropdesign.droolspoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gb.cropdesign.droolspoc.PcrResult;
import com.gb.cropdesign.droolspoc.mapper.PcrResultMapper;

public class PcrResultServiceJdbcImpl implements PcrResultServiceI {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<PcrResult> getAllPcrResults() {
		String sql = "SELECT upper(replace(replace(replace(pcr_target, ' ', '_'), '/', '_'),'-', '_')) as pcr_target" +
				" , plant_name, is_test_gene_present, copy_nr, is_used FROM pcrresult";

		return jdbcTemplate.query(sql, new PcrResultMapper());
	}

	@Override
	public List<PcrResult> getPcrResultForPlantName(String plantName) {
		String sql = "SELECT upper(replace(replace(replace(pcr_target, ' ', '_'), '/', '_'),'-', '_')) as pcr_target" +
				", plant_name, is_test_gene_present, copy_nr, is_used " +
				" FROM pcrresult " +
				" WHERE plant_name = ?";

		return jdbcTemplate.query(sql, new PcrResultMapper(), plantName);
	}
}
