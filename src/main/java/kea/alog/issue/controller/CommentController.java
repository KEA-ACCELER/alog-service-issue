package kea.alog.issue.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kea.alog.issue.config.Result;
import kea.alog.issue.controller.dto.CommentDto.CommentDataDto;
import kea.alog.issue.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    final private CommentService commentService;

    @GetMapping("/{commentPk}")
    public ResponseEntity<Result> getOneComment(@PathVariable Long commentPk){
        CommentDataDto rspDto = commentService.getComment(commentPk);
        if(rspDto.getCommentPk() != 0L){
            Result result = Result.builder()
                    .isSuccess(true)
                    .data(rspDto)
                    .message("불러오기 완료")
                    .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                    .isSuccess(false)
                    .message("불러오기 실패")
                    .build();
            return ResponseEntity.badRequest().body(result);
        }
    }
    @GetMapping("/{issuePk}/{currentPage}")
    public ResponseEntity<Result> getListComment(@PathVariable Long issuePk, @PathVariable Long curentPage){
        List<CommentDataDto> comments = commentService.getCommentList(issuePk, curentPage);
        if(comments.size() > 0){
            Result result = Result.builder()
                    .isSuccess(true)
                    .data(comments)
                    .message("불러오기 완료")
                    .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                    .isSuccess(false)
                    .message("없거나 실패함")
                    .build();
            return ResponseEntity.badRequest().body(result);
        }
    }
}