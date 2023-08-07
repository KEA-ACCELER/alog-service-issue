package kea.alog.issue.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import kea.alog.issue.config.Result;
import kea.alog.issue.controller.dto.CommentDto.*;
import kea.alog.issue.service.CommentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue/comment")
public class CommentController {
    final private CommentService commentService;

    @PostMapping("/commentCreate")
    public ResponseEntity<Result> createComment(@RequestBody CommentCreateOrUpdateDto commentCreateDto){
        Long commentPk = commentService.createComment(commentCreateDto);
        if(commentPk > 0L){
            Result result = Result.builder()
                    .data(commentPk)
                    .isSuccess(true)
                    .message("Success Crated")
                    .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                    .isSuccess(false)
                    .message("Failed create")
                    .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PutMapping("/commentUpdate/{commentPk}")
    public ResponseEntity<Result> updateComment(@PathVariable Long commentPk, @RequestBody CommentCreateOrUpdateDto reqDto) {
        Long updateId = commentService.updateComment(commentPk, reqDto);
        if(updateId > 0L) {
            Result result = Result.builder()
                    .data(updateId)
                    .isSuccess(true)
                    .message("Success updated")
                    .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                    .isSuccess(false)
                    .message("Failed Update")
                    .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

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
    @DeleteMapping("/commentDelete/{commentPk}")
    public ResponseEntity<Result> commentDelete(@PathVariable Long commentPk){
        boolean isDelete = commentService.deleteComment(commentPk);
        if(isDelete){
            Result result = Result.builder()
                    .isSuccess(true)
                    .message("Success Deleted")
                    .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                    .isSuccess(false)
                    .message("Failed Delete")
                    .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

    
}