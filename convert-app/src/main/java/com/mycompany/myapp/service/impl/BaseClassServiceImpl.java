package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.BaseClassService;
import com.mycompany.myapp.domain.Argumts;
import com.mycompany.myapp.domain.BaseClass;
import com.mycompany.myapp.domain.BuilderPlate;
import com.mycompany.myapp.domain.Fonctions;
import com.mycompany.myapp.domain.Langages;
import com.mycompany.myapp.domain.Proprietes;
import com.mycompany.myapp.domain.RefCode;
import com.mycompany.myapp.repository.ArgumtsRepository;
import com.mycompany.myapp.repository.BaseClassRepository;
import com.mycompany.myapp.repository.BuilderPlateRepository;
import com.mycompany.myapp.repository.FonctionsRepository;
import com.mycompany.myapp.repository.LangagesRepository;
import com.mycompany.myapp.repository.ProprietesRepository;
import com.mycompany.myapp.repository.RefCodeRepository;
import com.mycompany.myapp.service.dto.ArgumtsDTO;
import com.mycompany.myapp.service.dto.BaseClassDTO;
import com.mycompany.myapp.service.dto.BuilderPlateDTO;
import com.mycompany.myapp.service.dto.FonctionsDTO;
import com.mycompany.myapp.service.dto.LangagesDTO;
import com.mycompany.myapp.service.dto.ProprietesDTO;
import com.mycompany.myapp.service.dto.RefCodeDTO;
import com.mycompany.myapp.service.mapper.ArgumtsMapper;
import com.mycompany.myapp.service.mapper.BaseClassMapper;
import com.mycompany.myapp.service.mapper.BuilderPlateMapper;
import com.mycompany.myapp.service.mapper.FonctionsMapper;
import com.mycompany.myapp.service.mapper.LangagesMapper;
import com.mycompany.myapp.service.mapper.ProprietesMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link BaseClass}.
 */
@Service
@Transactional
public class BaseClassServiceImpl implements BaseClassService {

	private final Logger log = LoggerFactory.getLogger(BaseClassServiceImpl.class);

	private final BaseClassRepository baseClassRepository;

	private final BaseClassMapper baseClassMapper;

	private final RefCodeRepository refRepo;

	private final FonctionsMapper foncMapper;

	private final FonctionsRepository foncRepo;

	private final ArgumtsMapper argMapper;

	private final ArgumtsRepository argRepo;

	private final LangagesMapper langMapper;

	private final LangagesRepository langRepo;

	private final ProprietesMapper propMapper;

	private final ProprietesRepository propRepo;

	private final BuilderPlateMapper builderMapper;

	private final BuilderPlateRepository buildRepo;

	public BaseClassServiceImpl(BaseClassRepository baseClassRepository, BaseClassMapper baseClassMapper,
			RefCodeRepository refRepo, FonctionsMapper foncMapper, FonctionsRepository foncRepo,
			ArgumtsMapper argMapper, ArgumtsRepository argRepo, LangagesMapper langMapper, LangagesRepository langRepo,
			ProprietesMapper propMapper, ProprietesRepository propRepo, BuilderPlateMapper builderMapper,
			BuilderPlateRepository buildRepo) {
		super();
		this.baseClassRepository = baseClassRepository;
		this.baseClassMapper = baseClassMapper;
		this.refRepo = refRepo;
		this.foncMapper = foncMapper;
		this.foncRepo = foncRepo;
		this.argMapper = argMapper;
		this.argRepo = argRepo;
		this.langMapper = langMapper;
		this.langRepo = langRepo;
		this.propMapper = propMapper;
		this.propRepo = propRepo;
		this.builderMapper = builderMapper;
		this.buildRepo = buildRepo;
	}

	// DTO Class Declaration

	RefCodeDTO refDTO = new RefCodeDTO();
	FonctionsDTO fecDTO = new FonctionsDTO();
	ArgumtsDTO argumentDTO = new ArgumtsDTO();
	LangagesDTO langDTO = new LangagesDTO();
	ProprietesDTO propDTO = new ProprietesDTO();
	BuilderPlateDTO buildDTO = new BuilderPlateDTO();
	BaseClassDTO baseClassDTO = new BaseClassDTO();

	// Service Implementation of Class convert
	ConvertToJava cvJ = new ConvertToJava();
	ConvertToJs cvJS = new ConvertToJs();
	ConvertToPHP cvPHP = new ConvertToPHP();

	// Domain Class Declaration
	BaseClass base = null;
	Fonctions fonc = null;
	RefCode ref = new RefCode();
	Proprietes properietes = null;
	Langages langageSave;

	/**
	 * Save All Tables .
	 *
	 * @param String [JSON CONTENT] the entity to save
	 * @return the persisted entity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BaseClassDTO save(String reader) {

		JSONParser jsonParser = new JSONParser();

		try {

			// Read Data of JSON
			Object obj = jsonParser.parse(reader);
			JSONObject data = (JSONObject) obj;

			// Save Base
			if (data.get("name") != null) {
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
					functions.add(function);
				});

			}

			// Methods call
			String javaconvert = cvJ.convertTo(base, lange, propListe, functions, argListe);
			String jsConvert = cvJS.convertTo(null, lange, null, functions, null);
			String phpConvert = cvPHP.convertTo(base, lange, propListe, functions, argListe);

			// Use Reference

			if (data.get("Dev") != null) {
				String dev = data.get("Dev").toString();
				String[] devSplit = dev.split(";");
				for (int i = 0; i < devSplit.length; i++) {

					ref = refRepo.findAll().get(i);

					// Save BuilderPlate
					if (ref.getLangage().equals("JAVA")) {

						buildDTO.setDefaultCode(javaconvert);
						buildDTO.setBaseClassId(baseid);
						buildDTO.setRefCodeId(ref.getId());
						BuilderPlate builderPlateSave = builderMapper.toEntity(buildDTO);
						builderPlateSave = buildRepo.save(builderPlateSave);

					}
					if (ref.getLangage().equals("PHP")) {

						buildDTO.setDefaultCode(phpConvert);
						buildDTO.setBaseClassId(baseid);
						buildDTO.setRefCodeId(ref.getId());
						BuilderPlate builderPlateSave = builderMapper.toEntity(buildDTO);
						builderPlateSave = buildRepo.save(builderPlateSave);

					}
					if (ref.getLangage().equals("JS")) {

						buildDTO.setDefaultCode(jsConvert);
						buildDTO.setBaseClassId(baseid);
						buildDTO.setRefCodeId(ref.getId());
						BuilderPlate builderPlateSave = builderMapper.toEntity(buildDTO);
						builderPlateSave = buildRepo.save(builderPlateSave);
					}

				}

			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return baseClassMapper.toDto(base);

	}

	/**
	 * Get all the baseClasses.
	 *
	 * @return the list of entities.
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BaseClassDTO> findAll() {
		log.debug("Request to get all BaseClasses");
		return baseClassRepository.findAll().stream().map(baseClassMapper::toDto)
				.collect(Collectors.toCollection(LinkedList::new));
	}

	/**
	 * Get one baseClass by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<BaseClassDTO> findOne(Long id) {
		log.debug("Request to get BaseClass : {}", id);
		return baseClassRepository.findById(id).map(baseClassMapper::toDto);
	}

	/**
	 * Delete the baseClass by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete BaseClass : {}", id);
		baseClassRepository.deleteById(id);
	}

	@Override
	public BaseClassDTO save(BaseClassDTO baseClassDTO) {
		// TODO Auto-generated method stub
		return null;
	}
}
