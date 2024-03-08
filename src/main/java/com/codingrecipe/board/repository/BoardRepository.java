package com.codingrecipe.board.repository;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final SqlSessionTemplate sql;

    public BoardDTO save(BoardDTO boardDTO) {

        sql.insert("Board.save", boardDTO);
        return boardDTO; // board-mapper.xml의 save에 useGeneratedKeys="true" keyProperty="id" 를 추가하면
                         // id가 포함된 DTO 객체가 만들어져 리턴 해줌 -> 이렇게 써도 id 포하된다고 함
    }

    public List<BoardDTO> findAll() {

        return sql.selectList("Board.findAll");
    }

    public BoardDTO findById(Long id) {

        return sql.selectOne("Board.findById", id);
    }

    public void updateHits(Long id) {
        sql.update("Board.updateHits", id);
    }

    public void update(BoardDTO boardDTO) {
        sql.update("Board.update", boardDTO);
    }

    public void delete(Long id) {
        sql.delete("Board.delete", id);
    }

    public void saveFile(BoardFileDTO boardFileDTO) {
        sql.insert("Board.saveFile", boardFileDTO);
    }

    public List<BoardFileDTO> findFile(Long id) {

        return sql.selectList("Board.findFile", id);
    }

    public List<BoardDTO> pagingList(Map<String, Integer> pageParams) {

        return sql.selectList("Board.paging", pageParams);
    }

    public int boardCount() {

        return sql.selectOne("Board.boardCount");
    }
}
