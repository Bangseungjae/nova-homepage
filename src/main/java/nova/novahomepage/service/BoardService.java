package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.BoardPageDto;
import nova.novahomepage.controller.dto.ChangeBoardDto;
import nova.novahomepage.domain.entity.Board;
import nova.novahomepage.repository.BoardRepository;
import nova.novahomepage.repository.UsersRepository;
import nova.novahomepage.repository.dsl.BoardQueryDsl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardQueryDsl boardQueryDsl;

    public void makeBoard(Board board) {
        log.info("board Content: {}", board.getContent());

        if (!StringUtils.hasText(board.getContent())) {
            throw new IllegalArgumentException("게시판 내용이 없습니다.");
        }
        if (!StringUtils.hasText(board.getTitle())) {
            throw new IllegalArgumentException("제목이 없습니다.");
        }

        boardRepository.save(board);
    }

    public void updateBoard(Long id, ChangeBoardDto changeBoardDto) {
        Board updateBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다."));
        updateBoard.changeBoard(changeBoardDto.getTitle(), changeBoardDto.getContent(), changeBoardDto.getTypeName());
    }

    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다."));
        if (board.getId() == null) {
            throw new IllegalArgumentException("지울 게시판 id가 없습니다.");
        }
        boardRepository.delete(board);
    }

    public Board getBoard(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다."));
    }

    public Page<BoardPageDto> allBoardByType(Pageable pageable, String typeName) {
        return boardQueryDsl.findAllBoardByType(typeName, pageable);
    }

}
