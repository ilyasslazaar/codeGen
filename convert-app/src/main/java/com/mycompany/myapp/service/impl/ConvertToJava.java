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
public class ConvertToJava implements Convert {

	
	/*
	 * ChaineJ : variable that stores the information returning by the method  
	 * java    : variable that stores the additional code
	 */
	
	String chaineJ = "";
	String java = "";

	/**
     *Get the java Code
     *
     * @return String 
     */
	@Override
	public String convertTo(BaseClass base, List<Langages> lang, List<Proprietes> properietes,
			List<Fonctions> fonctions, List<Argumts> arg) {
		chaineJ = "";
		java = "";
		// Block : Imports

		if (base.getImports() != null) {
			String[] imp = base.getImports().split(";");

			for (int i = 0; i < imp.length; i++) {
				chaineJ += imp[i] + ";\n";
			}
		}
		// Block : Name Class
		
		if (base.getNom() != null) {
			chaineJ += " public class " + base.getNom() + "{ \n";
		} else {
			chaineJ += " public class TEST{ \n";

		}
		// Block : Proprietes
		
		if (properietes != null) {
			for (int i = 0; i < properietes.size(); i++) {
				chaineJ += "\n public ";

				if (properietes.get(i).getType().contains("ArrayList")) {
					String[] l = properietes.get(i).getType().split(",");
					if (l[0].equals("ArrayList")) {
						chaineJ += "List<" + l[1] + "> ";
						chaineJ += properietes.get(i).getNom() + ";\n";

					}
				} else {
					if (properietes.get(i).getType().equals("integer")) {
						chaineJ += "int ";
						chaineJ += properietes.get(i).getNom() + ";\n";

					} else if (properietes.get(i).getType().equals("character")) {
						chaineJ += "char ";
						chaineJ += properietes.get(i).getNom() + ";\n";

					}

					else {
						chaineJ += properietes.get(i).getType() + " ";
						chaineJ += properietes.get(i).getNom() + ";\n";

					}
				}
			}
		}

		// Block : Fonctions
		
		if (fonctions != null) {
			fonctions.forEach(f -> {
				chaineJ += "\tprivate static " + f.getType() + " " + f.getNom() + " (";

				for (int i = 0; i < f.getArguments().size(); i++) {
					Argumts arg2 = f.getArguments().get(i);
					chaineJ += (arg2.getType() + " " + arg2.getNom());

					if (i + 1 < f.getArguments().size()) {
						chaineJ += ", ";

					}
				}
				chaineJ += ") \n {\n";
				chaineJ += "\t\t //saisir votre code ici \n";
				chaineJ += "\t} \n";

			});
		}
		// Block : Code Static ( Main )

		chaineJ += " public static void main(String[] args)\n{\n";
		chaineJ += "\t String n = args[0]; \n";
		if (fonctions != null) {
			fonctions.forEach(f -> {

				chaineJ += "\t System.out.println(" + f.getNom() + "(n)); \n ";

			});
		}
		chaineJ += "} \n";

		// Block : Additional Code JAVA 
		if (lang != null) {
			lang.forEach(l -> {
				if (l.getNom().equals("Java")) {
					java = l.getCode();
				}

			});

			if (java != "") {
				String[] javaSplit = java.split(";");
				for (int j = 0; j < javaSplit.length; j++) {

					chaineJ += javaSplit[j] + ";\n";
				}
			}

		}
		chaineJ += "} \n";

		return chaineJ;

	}

}
