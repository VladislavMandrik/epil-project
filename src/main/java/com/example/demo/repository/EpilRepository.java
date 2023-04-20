package com.example.demo.repository;

import com.example.demo.model.Epil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpilRepository extends JpaRepository<Epil, Long> {
    List<Epil> findAllByOrderByNameAsc();
    List<Epil> findAllByMasterName(String s);
    @Query(value = "SELECT SUM(CAST(price AS INT)) FROM march WHERE master_name = 'Мандрик'", nativeQuery = true)
    Integer findSumInMarchByMandrik();
    @Query(value = "SELECT SUM(CAST(price AS INT)) FROM april WHERE master_name = 'Мандрик'", nativeQuery = true)
    Integer findSumInAprilByMandrik();
    @Query(value = "SELECT SUM(CAST(price AS INT)) FROM march WHERE master_name = 'Мазикова'", nativeQuery = true)
    Integer findSumInMarchByMazikova();
    @Query(value = "SELECT SUM(CAST(price AS INT)) FROM april WHERE master_name = 'Мазикова'", nativeQuery = true)
    Integer findSumInAprilByMazikova();
    @Query(value = "SELECT SUM(CAST(price AS INT)) FROM march WHERE master_name = 'Шамилова'", nativeQuery = true)
    Integer findSumInMarchByShamilova();
    @Query(value = "SELECT SUM(CAST(price AS INT)) FROM april WHERE master_name = 'Шамилова'", nativeQuery = true)
    Integer findSumInAprilByShamilova();
}
