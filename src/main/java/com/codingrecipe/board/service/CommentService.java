package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.CommentDTO;
import com.codingrecipe.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO) {

        commentRepository.save(commentDTO);
    }

    public List<CommentDTO> findAll(Long boardId) {

        List<CommentDTO> commentDTOList = commentRepository.findAll(boardId);

        return commentDTOList;
    }
}
