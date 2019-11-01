package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Proprietes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Proprietes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProprietesRepository extends JpaRepository<Proprietes, Long> {

}
