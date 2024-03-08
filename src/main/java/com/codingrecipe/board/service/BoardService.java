package com.codingrecipe.board.service;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.dto.PageDTO;
import com.codingrecipe.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    int pageLimit = 3; // 한 페이지당 보여줄 글 갯수
    int blockLimit = 3; // 하단에 보여줄 페이지 번호 갯수

    public void save(BoardDTO boardDTO) throws IOException {

        if(boardDTO.getBoardFile().get(0).isEmpty()){
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

    public List<BoardDTO> pagingList(int page) {

        int pageLimit = 3;
        /*
            1 페이지당 보여지는 글 갯수 3
            1page => 0
            2page => 3
            3page => 6
         */
        int pagingStart = (page-1) * pageLimit;
        Map<String, Integer> pageParams = new HashMap<>();
        pageParams.put("start", pagingStart);
        pageParams.put("limit", pageLimit);
        List<BoardDTO> boardDTOList = boardRepository.pagingList(pageParams);

        return boardDTOList;
    }

    public PageDTO pagingParam(int page) {

        // 전체 글 갯수 조회
        int boardCount = boardRepository.boardCount();
        // 전체 페이지 갯수 계산 -> Math.ceil 올림
        int maxPage = (int)(Math.ceil((double) boardCount/pageLimit));
        // 시작 페이지 값 계산(1, 4, 7, 10, ~~~~)
        int startPage = (((int)(Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        // 끝 페이지 값 계산(3, 6, 9, 12, ~~~~)
        int endPage = startPage + blockLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }

        PageDTO pageDTO = new PageDTO();
        pageDTO.setPage(page);
        pageDTO.setMaxPage(maxPage);
        pageDTO.setStartPage(startPage);
        pageDTO.setEndPage(endPage);

        System.out.println("pageDTO = " + pageDTO);
        return pageDTO;
    }
}
