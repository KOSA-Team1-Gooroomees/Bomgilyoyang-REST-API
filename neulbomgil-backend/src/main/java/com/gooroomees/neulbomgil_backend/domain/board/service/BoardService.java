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

    // 상세 글 클릭 + 조회수 증가
    @Transactional
    public BoardResponseDTO getOneBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        board.increaseCnt();
        return new BoardResponseDTO(board);
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
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        board.update(dto.getTitle(), dto.getContent());

        if(!board.getUserid().getUserId().equals(userAuth.getUserId())){
            throw new IllegalArgumentException("본인 글만 수정할 수 있습니다.");
        }
        board.update(dto.getTitle(), dto.getContent());
    }

    // 글 삭제
    @Transactional
    public void deleteBoard(Long boardId, UserAuth userAuth) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        // 본인 글인지 검증
        if (!board.getUserid().getUserId().equals(userAuth.getUserId())) {
            throw new IllegalArgumentException("본인 글만 삭제할 수 있습니다.");
        }

        boardRepository.deleteById(boardId);
    }
}