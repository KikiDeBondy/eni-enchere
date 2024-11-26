package fr.eni.tp.eniencheres.controller.tools;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.tp.eniencheres.bll.CoursService;
import fr.eni.tp.eniencheres.bo.Cours;

@Component
public class CoursListConverter implements Converter<String[], List<Cours>> {
	private CoursService coursService;

	public CoursListConverter(CoursService coursService) {
		super();
		this.coursService = coursService;
	}

	@Override
	public List<Cours> convert(String[] source) {
		var cours = new ArrayList<Cours>();
		for (String id : source) {
			cours.add(coursService.findById(Long.parseLong(id)));
		}
		return cours;
	}

}
