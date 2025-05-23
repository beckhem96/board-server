package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public void register(CommentDTO commentDTO);
    public void updateComment(CommentDTO commentDTO);
    public void deletePostComment(int commentId);
}
