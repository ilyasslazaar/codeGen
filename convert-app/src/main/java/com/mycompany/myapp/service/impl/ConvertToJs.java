package com.mycompany.myapp.service.impl;

import java.util.List;

import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.domain.Proprietes;
import com.mycompany.myapp.service.Convert;

/**
 * Service Implementation for Convert.
 */
public class ConvertToJs implements Convert {
	
	/*
	 * chaineJS : variable that stores the information returning by the method  
	 * js    : variable that stores the additional code
	 */
	
	String chaineJS = "";
	String js = "";

	
	/**
     *Get the JavaScript Code
     *
     * @return String 
     */
	@Override
	public String convertTo(BaseClass base, List<Langages> lang, List<Proprietes> properietes,
			List<Fonctions> fonctions, List<Argumts> arg) {

		chaineJS = "";
		js = "";
		//Block : Fonctions 
		if (fonctions != null) {
			fonctions.forEach(f -> {
				chaineJS += "\t const " + f.getNom() + "= (";
				for (int i = 0; i < f.getArguments().size(); i++) {
					Argumts a = f.getArguments().get(i);
					chaineJS += a.getNom();
					if (i + 1 < f.getArguments().size())
						chaineJS += ", ";
				}
				chaineJS += ") => {\n";
				chaineJS += "\t let result; \n";
				chaineJS += "\t //Saisir votre code ici \n";
				chaineJS += "\t return result \n";
				chaineJS += "\t} \n";
			});

			chaineJS += "\t const {argv} = process; \n";
			fonctions.forEach(f -> {
				chaineJS += "\t console.log(" + f.getNom() + "(parseInt(argv[2]))); \n";
			});

		}
		
		// Block : Additional Code JavaScript 

		if (lang != null) {
			lang.forEach(l -> {
				if (l.getNom().equals("JS")) {
					js = l.getCode();
				}

			});
			if (js != "") {
				String[] jsSplit = js.split(";");
				for (int j = 0; j < jsSplit.length; j++) {

					chaineJS += jsSplit[j] + ";\n";
				}
			}
		}

		return chaineJS;
	}

}
