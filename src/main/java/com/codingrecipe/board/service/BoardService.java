package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {

        if(boardDTO.getBoardFile().isEmpty()){
            // 파일이 없음
            boardDTO.setFileAttached(0);
            boardRepository.save(boardDTO);
        } else {
            // 파일이 있음
            boardDTO.setFileAttached(1);
            // 게시글 저장 후 id 값을 활용을 위해 리턴 받음
            BoardDTO savedBoard = boardRepository.save(boardDTO);

            for(MultipartFile boardFile : boardDTO.getBoardFile()) {
                // 파일만 따로 가져오기
                //MultipartFile boardFile = boardDTO.getBoardFile();
                // 파일 이름 가져오기
                String originalFileName = boardFile.getOriginalFilename();
                System.out.println("originalFilename = " + originalFileName);
                // 저장용 이름 만들기
                System.out.println(System.currentTimeMillis());
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                System.out.println("storedFileName = " + storedFileName);
                // BoardFileDTO 세팅
                BoardFileDTO boardFileDTO = new BoardFileDTO();
                boardFileDTO.setOriginalFileName(originalFileName);
                boardFileDTO.setStoredFileName(storedFileName);
                boardFileDTO.setBoardId(savedBoard.getId());
                // 파일 저장용 폴더에 파일 저장 처리
                String savePath = "/Users/eunsikkim/ESKIM/02. Project/03. CodingRecipe/06. SpringBoot_Board_Mybatis/workspace/upload_files/" + storedFileName;
                boardFile.transferTo(new File(savePath));
                // board_file_table 저장 처리
                boardRepository.saveFile(boardFileDTO);
            }
        }
    }

    public List<BoardDTO> findAll() {

        List<BoardDTO> boardDTOList = boardRepository.findAll();

        return boardDTOList;
    }

    public BoardDTO findById(Long id) {

        return boardRepository.findById(id);
    }

    public void updateHits(Long id) {

        boardRepository.updateHits(id);
    }

    public void update(BoardDTO boardDTO) {

        boardRepository.update(boardDTO);
    }

    public void delete(Long id) {

        boardRepository.delete(id);
    }

    public List<BoardFileDTO> findFile(Long id) {

        return boardRepository.findFile(id);
    }
}
