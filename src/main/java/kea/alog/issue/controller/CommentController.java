package kea.alog.issue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kea.alog.issue.config.Result;
import kea.alog.issue.controller.dto.CommentDto.*;
import kea.alog.issue.domain.comment.Comment;
import kea.alog.issue.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Operation(summary = "댓글 생성 ")
    @ApiResponse(responseCode = "200", description = "댓글 생성 성공, 실패시 null 반환")
    @PostMapping("/")
    public ResponseEntity<Long> createComment(@RequestBody CommentCreateRequestDto commentCreateRequestDto) {
        Long commentPk = commentService.createComment(commentCreateRequestDto);
        return ResponseEntity.ok(commentPk);
    }

    @Operation(summary = "댓글 삭제")
    @DeleteMapping("")
    public ResponseEntity<String> commentDelete(@RequestParam Long commentPk){
        String result = commentService.deleteComment(commentPk);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "최신순으로 댓글 조회")
    @GetMapping("/by/date")
    public ResponseEntity<List<Comment>> commentList(@RequestParam Long issuePk,
    @Parameter(description = "페이지 넘버, 디폴트 0") @RequestParam(defaultValue = "0") int page,
    @Parameter(description = "페이지 사이즈, 디폴트 10") @RequestParam(defaultValue = "10") int size){
        List<Comment> commentList = commentService.getCommentList(issuePk, page, size);
        return ResponseEntity.ok(commentList);
    }

    @Operation(summary = "특정인이 쓴 댓글 조회")
    @GetMapping("/by/author")
    public ResponseEntity<List<Comment>> commentListByAuthor(@RequestParam Long issuePk, @RequestParam Long authorPk,
    @Parameter(description = "페이지 넘버, 디폴트 0") @RequestParam(defaultValue = "0") int page,
    @Parameter(description = "페이지 사이즈, 디폴트 10") @RequestParam(defaultValue = "10") int size){
        List<Comment> commentList = commentService.getCommentListByAuthor(issuePk, authorPk, page, size);
        return ResponseEntity.ok(commentList);
    }



}