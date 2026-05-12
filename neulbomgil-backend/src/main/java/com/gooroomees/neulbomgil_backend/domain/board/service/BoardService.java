package com.gooroomees.neulbomgil_backend.domain.board.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.board.dto.BoardRequestDTO;
import com.gooroomees.neulbomgil_backend.domain.board.dto.BoardResponseDTO;
import com.gooroomees.neulbomgil_backend.domain.board.entity.Board;
import com.gooroomees.neulbomgil_backend.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private static final int PAGE_SIZE = 15;

    //게시글 없으면 예외처리
    private Board findBoard(Long boardId){
        return boardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    // 최신순 (디폴트)
    public Page<BoardResponseDTO> getAllBoards(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());
        return boardRepository.findAll(pageable)
                .map(BoardResponseDTO::new);
    }

    // 조회수 높은순
    public Page<BoardResponseDTO> getBoardsByViews(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("cnt").descending());
        return boardRepository.findAll(pageable)
                .map(BoardResponseDTO::new);
    }
    //댓글 많은순
    public Page<BoardResponseDTO> getBoardsReplyCount(int page){
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return boardRepository.findAllOrderByReplyCount(pageable)
                .map(BoardResponseDTO::new);
    }

    // 상세 글 클릭 + 조회수 증가
    @Transactional
    public BoardResponseDTO getOneBoard(Long boardId) {
        Board board = findBoard(boardId);
        board.increaseCnt();
        return new BoardResponseDTO(board);
    }

    //검색어 입력, 관련 글 가져오기
    public Page<BoardResponseDTO> searchBoard(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());
        return boardRepository.findByTitleContainingOrContentContaining(
                        keyword, keyword, pageable)
                .map(BoardResponseDTO::new);
    }

    // 글 작성
    @Transactional
    public void createBoard(BoardRequestDTO dto, UserAuth userAuth) {
        Board board = Board.create(userAuth, dto.getTitle(), dto.getContent());
        boardRepository.save(board);
    }

    // 글 수정
    @Transactional
    public void updateBoard(BoardRequestDTO dto,Long boardId, UserAuth userAuth) {
        Board board = findBoard(boardId);
        board.validateOwner(userAuth);
        board.update(dto.getTitle(), dto.getContent());
    }

    // 글 삭제
    @Transactional
    public void deleteBoard(Long boardId, UserAuth userAuth) {
        Board board = findBoard(boardId);
        board.validateOwner(userAuth);
        boardRepository.deleteById(boardId);
    }
}