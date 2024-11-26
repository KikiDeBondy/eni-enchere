package fr.eni.tp.eniencheres.bll;
import java.util.List;

import fr.eni.tp.eniencheres.bo.Cours;

public interface CoursService {
	List<Cours> getCours();

	Cours findById(long id);
}
