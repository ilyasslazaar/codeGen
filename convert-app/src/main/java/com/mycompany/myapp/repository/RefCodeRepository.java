package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RefCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RefCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefCodeRepository extends JpaRepository<RefCode, Long> {

}
