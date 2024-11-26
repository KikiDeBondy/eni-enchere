package fr.eni.tp.eniencheres.dal;

import java.util.List;

import fr.eni.tp.eniencheres.bo.Formateur;

public interface FormateurDAO {
	void create(Formateur formateur);

	Formateur read(String emailFormateur);

	void update(Formateur formateur);

	List<Formateur> findAll();

	int uniqueEmail(String email) ;
}
