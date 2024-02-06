package minu.subran.service;

import jakarta.persistence.EntityManager;
import minu.subran.domain.Member;
import minu.subran.domain.Recipe;
import minu.subran.domain.Save;
import minu.subran.repository.MemberRepository_안씀;
import minu.subran.repository.RecipeRepository;
import minu.subran.repository.SaveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
//@Rollback(value = false)
class SaveServiceTest {

    @Autowired
    SaveRepository saveRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository_안씀 memberRepository;
    @Autowired
    RecipeService recipeService;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    SaveService saveService;
    @Autowired
    EntityManager em;

    @Test
    public void 레시피_저장하기() throws Exception {
        //given
        Member member = new Member();
        member.setMemberName("kim");
        member.setMemberEmail("kim@a.b");
        member.setPassword("asdfasdf");
        memberService.join(member);

        Recipe recipe = new Recipe();
        recipe.setBread("f");
        recipe.setMainStuff("e");
        recipeService.join(recipe);

        //when
        Long savedId = saveService.join(member.getId(), recipe.getId());

        //then
        em.flush(); //이거 하면 진짜 디비에 들어가는 거임.
        assertInstanceOf(Save.class, saveRepository.findOne(savedId));
        assertEquals(saveRepository.findOne(savedId).getMember(),
                memberRepository.findOne(member.getId()));
        assertEquals(saveRepository.findOne(savedId).getRecipe(),
                recipeRepository.findOne(recipe.getId()));
    }

    @Test
    public void 레시피_삭제하기() throws Exception {
        //given
        Member member = new Member();
        member.setMemberName("kim");
        member.setMemberEmail("kim@a.b");
        member.setPassword("asdfasdf");
        memberService.join(member);

        Recipe recipe = new Recipe();
        recipe.setBread("f");
        recipe.setMainStuff("e");
        recipeService.join(recipe);

        //when
        Long savedId = saveService.join(member.getId(), recipe.getId());
        saveService.unSave(savedId);
//        em.flush();

        //then
        assertNull(saveService.findOne(savedId));
    }

    @Test
    public void 멤버아이디로_저장정보_리스트() throws Exception {
        //given
        Member member = new Member();
        member.setMemberName("kim");
        member.setMemberEmail("kim@a.b");
        member.setPassword("asdfasdf");
        memberService.join(member);

        Recipe recipe = new Recipe();
        recipe.setBread("f");
        recipe.setMainStuff("e");
        recipeService.join(recipe);
        Recipe recipe2 = new Recipe();
        recipe2.setBread("h");
        recipe2.setMainStuff("p");
        recipeService.join(recipe2);

        //when
        saveService.join(member.getId(), recipe.getId());
        saveService.join(member.getId(), recipe2.getId());
        List<Save> saves = saveService.findSaves(member);
        System.out.println("saves[0] member id = " + saves.get(0).getMember().getId());
        System.out.println("saves[0] member name = " + saves.get(0).getMember().getMemberName());
        System.out.println("saves[0] recipe id = " + saves.get(0).getRecipe().getId());

        System.out.println("saves[1] member id = " + saves.get(1).getMember().getId());
        System.out.println("saves[1] member name = " + saves.get(1).getMember().getMemberName());
        System.out.println("saves[1] recipe id = " + saves.get(1).getRecipe().getId());

        //then
        assertEquals(saves.size(), 2);
    }
}