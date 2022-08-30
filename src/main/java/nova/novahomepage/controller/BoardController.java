package nova.novahomepage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.BoardDto;
import nova.novahomepage.controller.dto.BoardPageDto;
import nova.novahomepage.controller.dto.ChangeBoardDto;
import nova.novahomepage.domain.entity.Board;
import nova.novahomepage.domain.entity.Chatting;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.service.BoardService;
import nova.novahomepage.service.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "게시판 API를 제공하는 Controller")
public class BoardController {

    private final BoardService boardService;
    private final UsersService usersService;

    @ApiOperation(value = "게시글을 쓴다")
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

    @ApiOperation(value = "게시글을 하나 선택하여 본다")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable Long id) {
        Board board = boardService.getBoard(id);
        List<String> chat = new ArrayList<>();
        List<Chatting> chatting = board.getChatting();
        for (Chatting c : chatting) {
            chat.add(c.getChat());
        }
        BoardDto boardDto = BoardDto.builder()
                .chatting(chat)
                .content(board.getContent())
                .title(board.getTitle())
                .writer(board.getUsers().getName())
                .studentNumber(board.getUsers().getStudentNumber())
                .good(board.getGood())
                .id(board.getId())
                .build();
        return ResponseEntity.ok().body(boardDto);
    }

    @ApiOperation(value = "게시글을 하나 지운다")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/board/{id}")
    public ResponseEntity deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "게시글을 수정한다")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PutMapping("/board/{id}")
    public ResponseEntity updateBoard(@PathVariable(name = "id") Long id, @RequestBody ChangeBoardDto changeBoardDto) {
        log.info("ChangeBoardDto = {}", changeBoardDto);
        boardService.updateBoard(id, changeBoardDto);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAnyRole('ROLE_READ')")
    @ApiOperation(value = "게시판에 따라 페이징된 게시글을 제공 ex) http://localhost:8080/all-board?page=0&size=10&typeName=type")
    @GetMapping("/all-board")
    public ResponseEntity<Page<BoardPageDto>> allBoardByTypeName(Pageable pageable, String typeName) {
        Page<BoardPageDto> boardPageDtos = boardService.allBoardByType(pageable, typeName);
        return ResponseEntity.ok().body(boardPageDtos);
    }
}