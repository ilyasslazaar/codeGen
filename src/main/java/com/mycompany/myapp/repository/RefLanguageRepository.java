package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp.domain.RefLanguage;
@SuppressWarnings("unused")
@Repository
public interface RefLanguageRepository extends JpaRepository<RefLanguage, Long>{

}
