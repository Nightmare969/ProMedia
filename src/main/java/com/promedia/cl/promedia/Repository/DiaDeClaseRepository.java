package com.promedia.cl.promedia.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.promedia.cl.promedia.Model.DiaDeClase;

@Repository
public interface DiaDeClaseRepository extends JpaRepository<DiaDeClase, Long>{

}
