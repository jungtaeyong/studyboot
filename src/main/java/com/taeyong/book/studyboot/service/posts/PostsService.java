package com.taeyong.book.studyboot.service.posts;

import com.taeyong.book.studyboot.domain.posts.Posts;
import com.taeyong.book.studyboot.domain.posts.PostsRepository;
import com.taeyong.book.studyboot.web.dto.PostsListResponseDto;
import com.taeyong.book.studyboot.web.dto.PostsResponseDto;
import com.taeyong.book.studyboot.web.dto.PostsSaveRequestDto;
import com.taeyong.book.studyboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다 id="+id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        /*
        * postsRepository 결과로 넘어온 Posts의 Stream을 map을 통해
        * PostListResponseDto로 변환하고 그 결과를 다시 List로 반환한다.
         */
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());

        // 람다식 활용한 것! .map(posts -> new PostsListResponseDto(posts)) 와 같다.
    }

    @Transactional
    public void delete (Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        postsRepository.delete(posts);
    }



}