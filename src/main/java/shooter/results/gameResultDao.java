
package shooter.results;

import com.google.inject.persist.Transactional;
import util.jpa.GenericJpaDao;

import java.util.List;


public class gameResultDao extends GenericJpaDao<gameResult> {

    //protected EntityManager entityManager;

    public gameResultDao() {
        super(gameResult.class);
    }

    @Transactional
    public List<gameResult> findBest(int n){
        return entityManager.createQuery("SELECT r FROM gameResult r ORDER BY r.score DESC", gameResult.class)
                .setMaxResults(n)
                .getResultList();
    }
}
