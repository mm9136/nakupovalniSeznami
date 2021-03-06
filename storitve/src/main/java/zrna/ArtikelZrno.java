package zrna;

import Entities.ArtikelEntity;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import interceptori.BeleziKlice;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
@BeleziKlice
public class ArtikelZrno {

    @PersistenceContext(unitName = "nakupovalniseznam-jpa")
    private EntityManager em;
    private final static Logger logger = Logger.getLogger(ArtikelZrno.class.getName());

    @PostConstruct
    private void init(){
        logger.info("Inicijalizacija zrna " + ArtikelZrno.class.getSimpleName());
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        logger.info("uuid = " + uuid);
    }

    @PreDestroy
    private void destroy(){
        logger.info("Deinicijalizacija zrna " + ArtikelZrno.class.getSimpleName());
    }

    public List<ArtikelEntity> getArtikli() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ArtikelEntity> c = cb.createQuery(ArtikelEntity.class);
        Root<ArtikelEntity> root = c.from(ArtikelEntity.class);
        c.select(root);
        List<ArtikelEntity> artikli = em.createQuery(c).getResultList();
        return artikli;
    }

    public ArtikelEntity getArtikelById(int idArtikel) {
        ArtikelEntity artikel = em.find(ArtikelEntity.class, idArtikel);
        if (artikel == null) {
            logger.info("Artikel ne obstaja");
        }
        return artikel;
    }

    public List<ArtikelEntity> getArtikelByNakupovalniSeznamId(int nakupovalniSeznamId){
        Query q = em.createNamedQuery(ArtikelEntity.GET_ARTIKEL_BY_NAKUPOVALNI_SEZNAM_ID).setParameter("nakupovalniSeznamId", nakupovalniSeznamId);
        List<ArtikelEntity> artikli =(List<ArtikelEntity>)(q.getResultList());
        if (artikli == null) {
            logger.info("Artikel z nakupovalni seznam id =" + nakupovalniSeznamId + " ne obstaja!");
            return null;
        }
        return artikli;

    }

    @Transactional
    public ArtikelEntity dodajArtikel(ArtikelEntity artikel) {
        em.persist(artikel);
        logger.info("Uspesno dodan artikel");
        return artikel;
    }

    @Transactional
    public ArtikelEntity posodobiArtikel(ArtikelEntity artikel) {
        ArtikelEntity a = em.find(ArtikelEntity.class, artikel.getId());
        if (a == null) {
            logger.info("Artikel ne obstaja");
            return null;
        }
        a.setNaziv(artikel.getNaziv());
        a.setZaloga(artikel.getZaloga());
        em.merge(a);
        logger.info("Uspesno posodobljen artikel");
        return a;
    }

    @Transactional
    public void izbrisiArtikel(int idArtikel) {
        ArtikelEntity artikel = em.find(ArtikelEntity.class, idArtikel);
        if (artikel == null) {
            logger.info("Artikel ne obstaja");
        }
        else {
            em.remove(artikel);
        }
    }

    @Default
    public List<ArtikelEntity> getArtikli2(QueryParameters query) {
        List<ArtikelEntity> artikli = JPAUtils.queryEntities(em, ArtikelEntity.class, query);
        return artikli;
    }

    public Long getArtikliCnt(QueryParameters query){
        Long cnt = JPAUtils.queryEntitiesCount(em, ArtikelEntity.class, query);
        return cnt;
    }

}
