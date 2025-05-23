package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CategoryDTO;
import com.fastcampus.boardserver.mapper.CategoryMapper;
import com.fastcampus.boardserver.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if (accountId != null) {
            categoryMapper.register(categoryDTO);
        } else {
            log.error("register error {}", categoryDTO);
            throw new RuntimeException("register error 게시글 카테고리 등록 메서드를 확인해라 " + categoryDTO);
        }
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {
            categoryMapper.updateCategory(categoryDTO);
        } else {
            log.error("update error {}", categoryDTO);
            throw new RuntimeException("update error " + categoryDTO);
        }
    }

    @Override
    public void delete(int categoryId) {
        if (categoryId != 0) {
            categoryMapper.deleteCategory(categoryId);
        } else {
            log.error("delete error {}", categoryId);
            throw new RuntimeException("delete error " + categoryId);
        }
    }
}
