package fr.eni.tp.eniencheres.controller.tools;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.tp.eniencheres.bll.CoursService;
import fr.eni.tp.eniencheres.bo.Cours;

@Component
public class CoursConverter implements Converter<String, Cours> {

	private CoursService coursService;

	public CoursConverter(CoursService coursService) {
		super();
		this.coursService = coursService;
	}

	@Override
	public Cours convert(String idCours) {
		return coursService.findById(Long.parseLong(idCours));
	}

}
