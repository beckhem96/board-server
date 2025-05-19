package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper postSearchMapper;

    @Cacheable(value = "getPosts", key = "'getPosts' + #postSearchRequest.getName() + #postSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("search error! {}", postSearchRequest, e);
            throw e;
        }
        return postDTOList;
    }

    @Override
    public List<PostDTO> getPostsByTagName(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPostByTag(tagName);
        } catch (RuntimeException e) {
            log.error("searchByTagName error! {}", tagName, e);
            throw e;
        }
        return postDTOList;
    }
}
