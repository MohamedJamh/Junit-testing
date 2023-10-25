package ma.youcode.gathergrid.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import ma.youcode.gathergrid.config.UserDatabase;
import ma.youcode.gathergrid.domain.Category;
import ma.youcode.gathergrid.utils.Response;

import java.util.List;

@ApplicationScoped
public class CategoryRepositoryImp implements CategoryRepository{

    private EntityManager em;

    @Inject
    public CategoryRepositoryImp(@UserDatabase EntityManager em) {
        this.em = em;
    }

    @Override
    public Category findById(long id) {
        return em.find(Category.class,id);
    }

    @Override
    @Transactional
    public void save(Category category){
        em.persist(category);
    }

    @Override
    @Transactional
    public void update(Category category) {
        em.merge(category);
    }

    @Override
    public void delete(Category category) {
        em.remove(category);
    }

    @Override
    public List<Category> findAll() {
        return em.createQuery("select c from Category c", Category.class).getResultList();
    }
}
