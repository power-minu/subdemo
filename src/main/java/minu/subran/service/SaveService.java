package minu.subran.service;

import lombok.RequiredArgsConstructor;
import minu.subran.domain.Member;
import minu.subran.domain.Recipe;
import minu.subran.domain.Save;
import minu.subran.repository.MemberRepository_안씀;
import minu.subran.repository.RecipeRepository;
import minu.subran.repository.SaveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SaveService {

    private final SaveRepository saveRepository;
    private final MemberRepository_안씀 memberRepository;
    private final RecipeRepository recipeRepository;

    //저장하기
    @Transactional
    public Long join(Long memberId, Long recipeId) {
        Member member = memberRepository.findOne(memberId);
        Recipe recipe = recipeRepository.findOne(recipeId);

        Save save = Save.createSave(member, recipe);

        saveRepository.save(save);

        return save.getId();
    }

    //저장 취소하기
    @Transactional
    public void unSave(Long saveId) {
        Save save = saveRepository.findOne(saveId);
        saveRepository.drop(save);
    }

    //멤버로 검색
    public List<Save> findSaves(Member member) {
        return saveRepository.findByMember(member);
    }

    //저장 pk로 하나 찾기
    public Save findOne(Long saveId) {
        return saveRepository.findOne(saveId);
    }
}
