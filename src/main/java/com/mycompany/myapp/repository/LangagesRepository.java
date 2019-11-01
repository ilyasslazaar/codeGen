package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Langages;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Langages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LangagesRepository extends JpaRepository<Langages, Long> {

}
