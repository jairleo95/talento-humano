package com.app.dgp.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface DGPRepository extends JpaRepository<DGP, String>{
//    @Query(value = "")
//    Collection<String> findDgpProcessDetails(@Param("name") String iddgp, @Param("idrp") String idrp, @Param("iddep") String iddep);
    
}
