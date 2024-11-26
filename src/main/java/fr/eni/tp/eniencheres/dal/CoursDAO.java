package fr.eni.tp.eniencheres.dal;

import java.util.List;

import fr.eni.tp.eniencheres.bo.Cours;

public interface CoursDAO {

	Cours read(long id);

	List<Cours> findAll();

	List<Cours> findByFormateur(String emailFormateur);

	void insertCoursFormateur(long idCours, String emailFormateur);
	
	boolean validateListOfCourseIds(List<Cours> lstCours);
}
