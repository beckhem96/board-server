package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.CommentMapper;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.TagMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void register(String id, PostDTO postDTO) {
        log.error("lwt check {}", id);
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDTO);
            Integer postId = postDTO.getId();
            List<TagDTO> tagDTOList = postDTO.getTagDTOList();
            if (tagDTOList != null) {
                for (int i=0;i<tagDTOList.size();i++) {
                    TagDTO tagDTO = tagDTOList.get(i);
                    tagMapper.register(tagDTO);
                    Integer tagId = tagDTO.getId();
                    tagMapper.createPostTag(tagId, postId);
                }
            }
        } else {
            log.error("register error, {}", postDTO);
            throw new RuntimeException("register error, " + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(accountId);
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if (postDTO != null && postDTO.getId() != 0) {
            postMapper.updatePosts(postDTO);
        } else {
            log.error("updatePosts error, {}", postDTO);
            throw new RuntimeException("updatePosts error, " + postDTO);
        }
    }

    @Override
    public void deletePosts(int userId, int postId) {
        if (userId != 0 && postId != 0) {
            postMapper.deletePosts(postId);
        } else {
            log.error("deletePosts error, {}", postId);
            throw new RuntimeException("deletePosts error, " + postId);
        }
    }

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if (commentDTO.getPostId() != 0) {
                commentMapper.register(commentDTO);
        } else {
            log.error("registerComment error, {}", commentDTO);
            throw new RuntimeException("registerComment error, " + commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if (commentDTO != null && commentDTO.getId() != 0) {
            commentMapper.updateComment(commentDTO);
        } else {
            log.error("updateComment error, {}", commentDTO);
            throw new RuntimeException("updateComment error, " + commentDTO);
        }
    }

    @Override
    public void deleteComment(int userId, int commentId) {
        if(userId != 0 && commentId != 0) {
            commentMapper.deletePostComment(commentId);
        } else {
            log.error("deleteComment error, {}", commentId);
            throw new RuntimeException("deleteComment error, " + commentId);
        }
    }

    @Override
    public void registerTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            tagMapper.register(tagDTO);
        } else {
            log.error("registerTag error, {}", tagDTO);
            throw new RuntimeException("registerTag error, " + tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            tagMapper.updateTags(tagDTO);
        } else {
            log.error("updateTag error, {}", tagDTO);
            throw new RuntimeException("updateTag error, " + tagDTO);
        }
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if (userId != 0 && tagId != 0) {
            tagMapper.deletePostTag(tagId);
        } else {
            log.error("deletePostTag error, {}", tagId);
            throw new RuntimeException("deletePostTag error, " + tagId);
        }
    }
}
