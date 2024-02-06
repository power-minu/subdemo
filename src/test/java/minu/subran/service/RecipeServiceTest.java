package minu.subran.service;

import jakarta.persistence.EntityManager;
import minu.subran.domain.Recipe;
import minu.subran.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class RecipeServiceTest {

    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 레시피_생성() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setBread("f");
        recipe.setMainStuff("s");

        //when
        Long savedId = recipeService.join(recipe);

        //then
        assertEquals(recipe, recipeService.findOne(savedId));
    }
}