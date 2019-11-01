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
				if(!(imp[i].isEmpty()))
				chaineJ += imp[i]+";\n" ;
				
							}
		}
		// Block : Name Class
	
			chaineJ += " public class " + base.getNom() + "{ \n";
		
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
					
						chaineJ += properietes.get(i).getType() + " ";
						chaineJ += properietes.get(i).getNom() + ";\n";

					
				}
			}
		}

		// Block : Fonctions
		
		if (fonctions != null) {
			fonctions.forEach(f -> {
				
				chaineJ+="\tprivate static ";
				if(f.getType().contains("ArrayList"))
				{
					String[] l=f.getType().split(",");
					if (l[0].equals("ArrayList")) {
						chaineJ += "List<" + l[1] + "> ";
						chaineJ += f.getNom()+ " (";

					}
				}
				else
				{
				chaineJ += f.getType() + " " + f.getNom() + " (";
				}
				
				if(f.getArguments()!=null)
				{
				for (int i = 0; i < f.getArguments().size(); i++) {
					Argumts arg2 = f.getArguments().get(i);
					if(arg2.getType().contains("ArrayList"))
					{
						String[] l=arg2.getType().split(",");
						if (l[0].equals("ArrayList")) {
							chaineJ += "List<" + l[1] + "> ";
							chaineJ += arg2.getNom();

						}

					}
					else
					chaineJ += (arg2.getType() + " " + arg2.getNom());

					if (i + 1 < f.getArguments().size()) {
						chaineJ += ", ";

					}
				}
				}
				chaineJ += ") \n {\n";
				
				chaineJ += "\t\t //saisir votre code ici \n";
				
				switch (f.getType()) {
				case "String":
				case "char":
				case "Long":
				case "Double":
					chaineJ+="return null; \n";
					break;
				case "boolean": 
					chaineJ+="return true; \n";
					break;
				case "int":
				case "double":
				case "long":
					chaineJ+="return 0; \n";
					break;
					
				default:
					if(f.getType().contains("ArrayList"))
					{
						chaineJ+="return null; \n";
					}
					break;
				}
				chaineJ += "\t} \n";
				
			});
		}
		// Block : Code Static ( Main )

		chaineJ += " public static void main(String[] args)\n{\n";
		chaineJ += "\t String n = args[0]; \n";
		if (fonctions != null) {
			fonctions.forEach(f -> {
				
				chaineJ += "\t System.out.println(" + f.getNom() + "(";
				if(f.getArguments()!=null)
				{
				for (int i = 0; i < f.getArguments().size(); i++) {
					Argumts arg2 = f.getArguments().get(i);
					switch (arg2.getType()) {
					case "String":
					case "char":
					case "Long":
					case "Double":
						chaineJ+="null";
						break;
					case "boolean": 
						chaineJ+="true";
						break;
					case "int":
					case "double":
					case "long":
						chaineJ+="0";
						break;
					default:
						if(arg2.getType().contains("ArrayList"))
						{
							chaineJ+="null";
						}
						break;
					}

					if (i + 1 < f.getArguments().size()) {
						chaineJ += ", ";

					}
				}
				}
				chaineJ+=")); \n	";
				
				
			});
			
		}

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
		chaineJ += "} \n } \n";


		return chaineJ;

	}

}
