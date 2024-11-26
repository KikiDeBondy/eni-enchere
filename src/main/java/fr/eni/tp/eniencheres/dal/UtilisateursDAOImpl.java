package fr.eni.tp.eniencheres.dal;

import fr.eni.tp.eniencheres.bo.Formateur;
import fr.eni.tp.eniencheres.bo.contexte.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UtilisateursDAOImpl implements UtilisateursDAO{
    private final String FIND_ALL = "SELECT no_utilisateur, nom, prenom FROM UTILISATEURS";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Utilisateur> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Utilisateur.class));
    }
}
