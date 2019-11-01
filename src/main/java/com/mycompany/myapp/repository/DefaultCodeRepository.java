package com.mycompany.myapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mycompany.myapp.domain.DefaultCode;

public interface DefaultCodeRepository extends JpaRepository<DefaultCode, Long> {

}
