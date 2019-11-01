package com.mycompany.myapp.service;

import java.util.List;

import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.domain.Proprietes;

public interface Convert {
	


	public String convertTo(BaseClass base, List<Langages> lang, List<Proprietes> properietes, List<Fonctions> fonctions ,List<Argumts> arg) ;
	
}
