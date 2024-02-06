package minu.subran.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import minu.subran.domain.Recipe;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecipeRepository {

    private final EntityManager em;

    public void save(Recipe recipe) {
        em.persist(recipe);
    }

    public Recipe findOne(Long id) {
        return em.find(Recipe.class, id);
    }
}
