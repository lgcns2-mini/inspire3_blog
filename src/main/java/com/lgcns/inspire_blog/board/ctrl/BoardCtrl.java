package com.lgcns.inspire_blog.board.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lgcns.inspire_blog.board.domain.dto.BoardRequestDTO;
import com.lgcns.inspire_blog.board.domain.dto.BoardResponseDTO;
import com.lgcns.inspire_blog.board.service.BoardService;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardCtrl {

    @Autowired
    private BoardService boardService;

    // 전체 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<BoardResponseDTO>> list() {
        List<BoardResponseDTO> list = boardService.select();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // 카테고리 별 게시글 목록 조회
    @GetMapping("/category")
    public ResponseEntity<List<BoardResponseDTO>> getBoardsByCategory(@RequestParam("category") String category) {
        return ResponseEntity.ok(boardService.findByCategory(category));
    }

    // 등록
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody BoardRequestDTO request) {
        BoardResponseDTO response = boardService.insert(request);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Integer boardId) {
        boolean deleted = boardService.delete(boardId);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 상세 조회
    @GetMapping("/read/{boardId}")
    public ResponseEntity<BoardResponseDTO> read(@PathVariable("boardId") Integer boardId) {
        BoardResponseDTO response = boardService.findBoard(boardId);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 좋아요 추가
    @PostMapping("/like/{boardId}")
    public ResponseEntity<Void> addLike(@PathVariable("boardId") Integer boardId, @RequestParam("userId") Long userId) {
        boolean result = boardService.addLike(boardId, userId);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 좋아요 취소
    @DeleteMapping("/like/{boardId}")
    public ResponseEntity<Void> cancelLike(@PathVariable("boardId") Integer boardId, @RequestParam("userId") Long userId) {
        boolean result = boardService.cancelLike(boardId, userId);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}