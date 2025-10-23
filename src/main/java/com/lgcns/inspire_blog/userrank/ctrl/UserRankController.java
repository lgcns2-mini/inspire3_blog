package com.lgcns.inspire_blog.userrank.ctrl;

import com.lgcns.inspire_blog.userrank.domain.dto.UserRankDTO;
import com.lgcns.inspire_blog.userrank.service.UserRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rank")
@RequiredArgsConstructor
public class UserRankController {

    private final UserRankService rankService;

    //  글 작성 시 점수 증가
    @PostMapping("/{userId}/post")
    public void addPost(@PathVariable long userId) {
        rankService.increasePostCount(userId);
    }

    // 댓글 작성 시 점수 증가
    @PostMapping("/{userId}/comment")
    public void addComment(@PathVariable long userId) {
        rankService.increaseCommentCount(userId);
    }

    @GetMapping("/leaderboard")
    public List<UserRankDTO> leaderboard(@RequestParam(defaultValue = "3") int size) {
        return rankService.getTopN(size);
    }
}