package com.mycompany.myapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.domain.DefaultCode;
import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.domain.Proprietes;
import com.mycompany.myapp.domain.RefLanguage;
import com.mycompany.myapp.repository.ArgumtsRepository;
import com.mycompany.myapp.repository.BaseClassRepository;
import com.mycompany.myapp.repository.DefaultCodeRepository;
import com.mycompany.myapp.repository.FonctionsRepository;
import com.mycompany.myapp.repository.LangagesRepository;
import com.mycompany.myapp.repository.ProprietesRepository;
import com.mycompany.myapp.repository.RefLanguageRepository;
import com.mycompany.myapp.service.DefaultCodeService;
import com.mycompany.myapp.service.dto.ArgumtsDTO;
import com.mycompany.myapp.service.dto.BaseClassDTO;
import com.mycompany.myapp.service.dto.DefaultCodeDTO;
import com.mycompany.myapp.service.dto.FonctionsDTO;
import com.mycompany.myapp.service.dto.LangagesDTO;
import com.mycompany.myapp.service.dto.ProprietesDTO;
import com.mycompany.myapp.service.dto.RefLanguageDTO;
import com.mycompany.myapp.service.mapper.ArgumtsMapper;
import com.mycompany.myapp.service.mapper.BaseClassMapper;
import com.mycompany.myapp.service.mapper.DefaultCodeMapper;
import com.mycompany.myapp.service.mapper.FonctionsMapper;
import com.mycompany.myapp.service.mapper.LangagesMapper;
import com.mycompany.myapp.service.mapper.ProprietesMapper;
import com.mycompany.myapp.service.mapper.RefLanguageMapper;

@Service
@Transactional
public class DefaultCodeServiceImpl implements DefaultCodeService {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(BaseClassServiceImpl.class);

	private final BaseClassRepository baseClassRepository;

	private final BaseClassMapper baseClassMapper;

	private final FonctionsMapper foncMapper;

	private final FonctionsRepository foncRepo;

	private final ArgumtsMapper argMapper;

	private final ArgumtsRepository argRepo;

	private final LangagesMapper langMapper;

	private final LangagesRepository langRepo;

	private final ProprietesMapper propMapper;

	private final ProprietesRepository propRepo;

	private final DefaultCodeMapper defaultMapper;

	private final DefaultCodeRepository defaultRep;
	
	public DefaultCodeServiceImpl(BaseClassRepository baseClassRepository, BaseClassMapper baseClassMapper,
			RefLanguageRepository refLangRepo, FonctionsMapper foncMapper, FonctionsRepository foncRepo,
			ArgumtsMapper argMapper, ArgumtsRepository argRepo, LangagesMapper langMapper, LangagesRepository langRepo,
			ProprietesMapper propMapper, ProprietesRepository propRepo, DefaultCodeMapper defaultMapper,
			DefaultCodeRepository defaultRep,RefLanguageMapper refMapper,RefLanguageRepository refRep) {
		super();
		this.baseClassRepository = baseClassRepository;
		this.baseClassMapper = baseClassMapper;
		this.foncMapper = foncMapper;
		this.foncRepo = foncRepo;
		this.argMapper = argMapper;
		this.argRepo = argRepo;
		this.langMapper = langMapper;
		this.langRepo = langRepo;
		this.propMapper = propMapper;
		this.propRepo = propRepo;
		this.defaultMapper = defaultMapper;
		this.defaultRep = defaultRep;
	}

	// DTO Class Declaration
	RefLanguageDTO refLangDTO = new RefLanguageDTO();
	FonctionsDTO fecDTO = new FonctionsDTO();
	ArgumtsDTO argumentDTO = new ArgumtsDTO();
	LangagesDTO langDTO = new LangagesDTO();
	ProprietesDTO propDTO = new ProprietesDTO();
	BaseClassDTO baseClassDTO = new BaseClassDTO();
	DefaultCodeDTO defaultDTO = new DefaultCodeDTO();

	// Service Implementation of Class convert
	ConvertToJava cvJ = new ConvertToJava();
	ConvertToJs cvJS = new ConvertToJs();
	ConvertToPHP cvPHP = new ConvertToPHP();

	// Domain Class Declaration
	BaseClass base = null;
	DefaultCode defaultCodeSave = null;

	Fonctions fonc = null;
	RefLanguage refLang = new RefLanguage();
	Proprietes properietes = null;
	Langages langageSave;

	/**
	 * Save All Tables .
	 *
	 * @param String [JSON CONTENT] the entity to save
	 * @return a list of "DefaultCode" 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultCode> save(String reader) {
		List<DefaultCode> test = new ArrayList<DefaultCode>();

		JSONParser jsonParser = new JSONParser();

		try {

			// Read Data of JSON
			Object obj = jsonParser.parse(reader);
			JSONObject data = (JSONObject) obj;

			// Save Base
			if (!data.get("name").equals("")) {
				String className = data.get("name").toString();
				baseClassDTO.setNom(className);
			} else {
				baseClassDTO.setNom("Test");
			}
			if (data.get("import") != null) {

				String imports = data.get("import").toString();

				baseClassDTO.setImports(imports);
			} else {
				baseClassDTO.setImports(null);
			}
			base = baseClassMapper.toEntity(baseClassDTO);
			base = baseClassRepository.save(base);

			long baseid = base.getId();

			// Save Langage

			List<String> langName = new ArrayList<>();
			List<String> codes = new ArrayList<>();
			List<Langages> lange = new ArrayList<>();

			JSONObject langages = (JSONObject) data.get("languages");
			if (langages != null) {
				langages.keySet().forEach(key -> langName.add(key.toString()));
				langages.values().forEach(value -> codes.add(((JSONObject) value).get("code").toString()));

				for (int i = 0; i < langName.size() || i < codes.size(); i++) {
					langDTO.setNom(langName.get(i));
					langDTO.setCode(codes.get(i));
					langDTO.setBaseClassId(baseid);

					langageSave = langMapper.toEntity(langDTO);
					langageSave = langRepo.save(langageSave);
					lange.add(langageSave);

				}
			}

			// Save properties

			List<String> propsName = new ArrayList<>();
			List<String> types = new ArrayList<>();
			List<Proprietes> propListe = new ArrayList<>();
			List<Fonctions> fonctionListe = new ArrayList<>();
			List<Argumts> argListe = new ArrayList<>();

			if (data.get("properties") != null) {
				JSONObject props = (JSONObject) data.get("properties");
				props.keySet().forEach(key -> propsName.add(key.toString()));
				props.values().forEach(value -> types.add(((JSONObject) value).get("type").toString()));

				for (int i = 0; i < propsName.size() || i < types.size(); i++) {
					propDTO.setNom(propsName.get(i));
					propDTO.setType(types.get(i));
					propDTO.setBaseClassId(baseid);

					properietes = propMapper.toEntity(propDTO);
					properietes = propRepo.save(properietes);

					propListe.add(properietes);

				}
			}

			// Save Fonction

			List<Fonctions> functions = new ArrayList<>();
			if (data.get("functions") != null) {
				JSONArray funct = (JSONArray) data.get("functions");

				funct.forEach(f -> {
					JSONObject fun = (JSONObject) f;
					Fonctions function = new Fonctions();
					List<Argumts> arguments = new ArrayList<>();

					function.setNom(fun.get("name").toString());
					function.setType(fun.get("returnType").toString());

					JSONArray ars = (JSONArray) fun.get("arguments");

					fecDTO.setNom(fun.get("name").toString());
					fecDTO.setType(fun.get("returnType").toString());

					fecDTO.setBaseClassId(baseid);
					fonc = foncMapper.toEntity(fecDTO);
					fonc = foncRepo.save(fonc);

					fonctionListe.add(fonc);

					// Save Arguments
					if(ars != null)
					{
					ars.forEach(a -> {
						Argumts argument = new Argumts(((JSONObject) a).get("name").toString(),
								((JSONObject) a).get("type").toString());
						argumentDTO.setNom(((JSONObject) a).get("name").toString());
						argumentDTO.setType(((JSONObject) a).get("type").toString());
						argumentDTO.setFonctionsId(fonc.getId());

						Argumts arg = argMapper.toEntity(argumentDTO);
						arg = argRepo.save(arg);
						argListe.add(arg);
						arguments.add(argument);
					});
					function.setArguments(arguments);

					}
					functions.add(function);
				});

			}

			// Methods call
			String javaconvert = cvJ.convertTo(base, lange, propListe, functions, argListe);
			String jsConvert = cvJS.convertTo(null, lange, null, functions, null);
			String phpConvert = cvPHP.convertTo(base, lange, propListe, functions, argListe);

			// Use Reference
			
			if (data.get("targetLanguage") != null) {
				String dev = data.get("targetLanguage").toString();
				String[] devSplit = dev.split(",");
				for (int i = 0; i < devSplit.length; i++) {

				 //refLang = refLangRepo.findAll().get(i);

					// Save BuilderPlate
					if (devSplit[i].equals("JAVA") || devSplit[i].equals("")) {
						
						// defaultCODE
						
						defaultDTO.setBoilerplate(javaconvert);
						defaultDTO.setRefLangId(1l);
						defaultCodeSave = defaultMapper.toEntity(defaultDTO);
						defaultCodeSave = defaultRep.save(defaultCodeSave);
						test.add(defaultCodeSave);
					
					
					

					}
				
					 if (devSplit[i].equals("PHP")) {

						// defaultCODE
						
						defaultDTO.setBoilerplate(phpConvert);
						defaultDTO.setRefLangId(2l);

						defaultCodeSave = defaultMapper.toEntity(defaultDTO);
						defaultCodeSave = defaultRep.save(defaultCodeSave);
						test.add(defaultCodeSave);

					}
					 if (devSplit[i].equals("JavaScript")) {

						// defaultCODE
						
						defaultDTO.setBoilerplate(jsConvert);
						defaultDTO.setRefLangId(3l);
						defaultCodeSave = defaultMapper.toEntity(defaultDTO);
						defaultCodeSave = defaultRep.save(defaultCodeSave);
						test.add(defaultCodeSave);

					}
				}

			}
			

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return test;
	}

}
