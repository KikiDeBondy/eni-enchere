package fr.eni.tp.eniencheres.dal;

import fr.eni.tp.eniencheres.bo.contexte.Utilisateur;

import java.util.List;

public interface UtilisateursDAO {
    List<Utilisateur> findAll();

}
