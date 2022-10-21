package com.app.dgp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.app.dgp.controller.DGP;

@SpringBootApplication
public class DgpApplication implements CommandLineRunner{

	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
    public void run(String... args) throws Exception {
        String sql = "SELECT * FROM RHTM_DGP";
         
        List<DGP> dgps = jdbcTemplate.query(sql,
                BeanPropertyRowMapper.newInstance(DGP.class));
         
        dgps.forEach(System.out :: println);
    }

	public static void main(String[] args) {
		SpringApplication.run(DgpApplication.class, args);
	}

}
