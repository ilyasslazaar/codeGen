package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BaseClass;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BaseClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BaseClassRepository extends JpaRepository<BaseClass, Long> {

}
