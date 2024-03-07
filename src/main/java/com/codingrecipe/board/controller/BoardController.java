package com.codingrecipe.board.controller;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm() {

        return "/save";
    }

    @PostMapping("/save")
    public String save(BoardDTO boardDTO){

        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);

        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String findAll(Model model) {

        List<BoardDTO> boardDTOList = boardService.findAll();
        System.out.println("boardDTOList = " + boardDTOList);
        model.addAttribute("boardList", boardDTOList);

        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {

        // 조회수 처리
        boardService.updateHits(id);
        // 상세내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        System.out.println("boardDTO = " + boardDTO);
        model.addAttribute("board", boardDTO);

        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {

        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, BoardDTO boardDTO, Model model) {

        boardService.update(boardDTO);

        BoardDTO board = boardService.findById(id);
        model.addAttribute("board", board);

        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        boardService.delete(id);

        return "redirect:/boarc/list";
    }
}
