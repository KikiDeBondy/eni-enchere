package fr.eni.tp.eniencheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.tp.eniencheres.bo.Cours;
import fr.eni.tp.eniencheres.dal.CoursDAO;

@Service
public class CoursServiceImpl implements CoursService {
	private CoursDAO courseDAO;

	public CoursServiceImpl(CoursDAO courseDAO) {
		this.courseDAO = courseDAO;
	}

	@Override
	public List<Cours> getCours() {
		return courseDAO.findAll();
	}

	@Override
	public Cours findById(long id) {
		return courseDAO.read(id);
	}
}
