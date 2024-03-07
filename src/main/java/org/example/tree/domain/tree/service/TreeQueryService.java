package org.example.tree.domain.tree.service;

import lombok.RequiredArgsConstructor;
import org.example.tree.domain.member.entity.Member;
import org.example.tree.domain.tree.entity.Tree;
import org.example.tree.domain.tree.repository.TreeRepository;
import org.example.tree.global.exception.GeneralException;
import org.example.tree.global.exception.GlobalErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeQueryService {
    private final TreeRepository treeRepository;

    public Tree findById(Long id) {
        return treeRepository.findById(id)
                .orElseThrow(()->new GeneralException(GlobalErrorCode.TREE_NOT_FOUND));
    }

}
