package com.mycompany.myapp.service.impl;

import java.util.List;

import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.domain.Proprietes;
import com.mycompany.myapp.service.Convert;

public class ConvertToPHP implements Convert {

	
	/*
	 * chainePHP : variable that stores the information returning by the method  
	 * php       : variable that stores the additional code
	 */
	
	String chainePHP = "";
	String php = "";

	
	/**
     *Get the PHP Code
     *
     * @return String 
     */
	@Override
	public String convertTo(BaseClass base, List<Langages> lang, List<Proprietes> properietes,
			List<Fonctions> fonctions, List<Argumts> arg) {
		
		chainePHP="";
		php="";
		
		//Block : Header of the PHP class
		
		
		chainePHP += "<?php \n class " + base.getNom() + "{ \n";
		

		//Block : Proprietes
		
		if(properietes!=null)
		{	
				for (int i = 0; i < properietes.size(); i++) {
					chainePHP += "\t public ";
		
					if (properietes.get(i).getType().contains("ArrayList")) {
						chainePHP += "$" + properietes.get(i).getNom() + "=array();\n";
		
					} else {
						
							chainePHP += "$" + properietes.get(i).getNom() + ";\n";
		
						
					}
				}
		}
		chainePHP += "\n";
		
		//Block : Fonctions 
		
		if(fonctions!=null)
		{
			fonctions.forEach(f -> {
				
				chainePHP += "\t public function " + f.getNom() + " (";
				if(f.getArguments()!=null)
				{

				for (int i = 0; i < f.getArguments().size(); i++) {
					Argumts arg2 = f.getArguments().get(i);
	
					chainePHP += "$" + arg2.getNom();
	
					if (i + 1 < f.getArguments().size()) {
						chainePHP += " , ";
	
					}
				}
				}
				chainePHP += ") \n {\n";
	
				chainePHP += "\t\t //saisir votre code ici \n";
				chainePHP += "\t} \n ";
				
	
			});
		}
		//Block : Additional Code PHP 
		
		if(lang!=null)
		{
		lang.forEach(l -> {
			if (l.getNom().equals("PHP")) {
				php = l.getCode();
			}

		});
			if (php != "") {
				String[] phpSplit = php.split(";");
				for (int j = 0; j < phpSplit.length; j++) {
	
					chainePHP += phpSplit[j] + ";\n";
				}
			}
		}
		chainePHP += "}\n ?>";

		return chainePHP;
	}

}
