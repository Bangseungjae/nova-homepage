package nova.novahomepage.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.BoardDto;
import nova.novahomepage.domain.entity.Board;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.service.BoardService;
import nova.novahomepage.service.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "게시판 API를 제공하는 Controller")
public class BoardController {

    private final BoardService boardService;
    private final UsersService usersService;

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("/board")
    public void makeBoard(@RequestBody BoardDto boardDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String studentNumber = authentication.getName();
        Users user = usersService.findUser(studentNumber);
        log.info("board Dto : {}", boardDto);
        Board board = Board.builder()
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .typeName(boardDto.getTypeName())
                .users(user)
                .good(0)
                .build();
        boardService.makeBoard(board);
    }
}