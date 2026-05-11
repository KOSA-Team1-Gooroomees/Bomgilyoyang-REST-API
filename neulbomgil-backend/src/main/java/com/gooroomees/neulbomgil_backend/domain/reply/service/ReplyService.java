package com.gooroomees.neulbomgil_backend.domain.reply.service;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import com.gooroomees.neulbomgil_backend.domain.board.entity.Board;
import com.gooroomees.neulbomgil_backend.domain.board.repository.BoardRepository;
import com.gooroomees.neulbomgil_backend.domain.board.service.BoardService;
import com.gooroomees.neulbomgil_backend.domain.reply.dto.ReplyRequestDTO;
import com.gooroomees.neulbomgil_backend.domain.reply.dto.ReplyResponseDTO;
import com.gooroomees.neulbomgil_backend.domain.reply.entity.Reply;
import com.gooroomees.neulbomgil_backend.domain.reply.repository.ReplyRepository;
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
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private static final int PAGE_SIZE = 15;

    //댓글 목록 조회
    public Page<ReplyResponseDTO> getReplies(Long boardid, int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("createdAt").descending());
        return replyRepository.findByBoard_Boardid(boardid, pageable).map(ReplyResponseDTO::new);
    }

    //댓글 작성
    @Transactional
    public void createReply(Long boardId, ReplyRequestDTO dto, UserAuth userAuth) {
        Board board = boardRepository.findById(boardId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        Reply reply = Reply.create(board, userAuth, dto.getContent());
        replyRepository.save(reply);
    }

    //댓글 수정
    @Transactional
    public void updateReply(Long replyId, ReplyRequestDTO dto, UserAuth userAuth) {
        Reply reply = replyRepository.findById(replyId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!reply.getUser().getUserId().equals(userAuth.getUserId())) {
            throw new IllegalArgumentException("본인 댓글만 수정할 수 있습니다.");
        }
        reply.update(dto.getContent());
    }
    //댓글 삭제
    @Transactional
    public void deleteReply(Long replyId, UserAuth userAuth){
        Reply reply = replyRepository.findById(replyId).orElseThrow(()
                -> new IllegalArgumentException("존재하지 않은 댓글입니다."));
        if(!reply.getUser().getUserId().equals(userAuth.getUserId())){
            throw new IllegalArgumentException("본인 댓글만 삭제할 수 있습니다.");
        }

    }
}