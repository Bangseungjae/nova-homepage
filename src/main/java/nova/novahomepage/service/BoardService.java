package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.domain.entity.Board;
import nova.novahomepage.repository.BoardRepository;
import nova.novahomepage.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;

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

    public void updateBoard(Board board) {
        Board updateBoard = boardRepository.findById(board.getId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 값입니다."));
        updateBoard.changeBoard(board.getTitle(), board.getContent());
    }

    public void deleteBoard(Board board) {
        if (board.getId() == null) {
            throw new IllegalArgumentException("지울 게시판 id가 없습니다.");
        }
        boardRepository.delete(board);
    }
}
