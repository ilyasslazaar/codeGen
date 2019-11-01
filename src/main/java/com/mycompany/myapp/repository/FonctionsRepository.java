package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fonctions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fonctions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FonctionsRepository extends JpaRepository<Fonctions, Long> {

}
