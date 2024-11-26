package fr.eni.tp.eniencheres.bll;

import fr.eni.tp.eniencheres.bo.contexte.Utilisateur;
import fr.eni.tp.eniencheres.dal.CoursDAO;
import fr.eni.tp.eniencheres.dal.FormateurDAO;
import fr.eni.tp.eniencheres.dal.UtilisateursDAO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    private UtilisateursDAO utilisateursDAO;

    public UtilisateurServiceImpl(UtilisateursDAO utilisateursDAO) {
        this.utilisateursDAO = utilisateursDAO;
    }

    @Override
    public List<Utilisateur> getUtilisateurs() {
        return utilisateursDAO.findAll();
    }
}
