package com.taeyong.book.studyboot.web;


import com.taeyong.book.studyboot.config.auth.LoginUser;
import com.taeyong.book.studyboot.config.auth.dto.SessionUser;
import com.taeyong.book.studyboot.service.posts.PostsService;
import com.taeyong.book.studyboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

//        CustomOAuth2UserService 에서 로그인 성공 시에 세션에 SessionUser를 저장하도록 구성했다.
//        @LoginUser 어노테이션을 이용해 처리했기 때문에 해줄필요가 없다.
//        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user!=null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
    
}
