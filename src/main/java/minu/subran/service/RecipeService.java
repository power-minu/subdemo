package minu.subran.service;

import lombok.RequiredArgsConstructor;
import minu.subran.domain.Recipe;
import minu.subran.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public Long join(Recipe recipe) {
        recipeRepository.save(recipe);
        return recipe.getId();
    }

    public Recipe findOne(Long recipeId) {
        return recipeRepository.findOne(recipeId);
    }
}
