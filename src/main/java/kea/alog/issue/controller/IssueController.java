package kea.alog.issue.controller;

import kea.alog.issue.config.Result;
import kea.alog.issue.controller.dto.IssueDto.*;
import kea.alog.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueController {
    final private IssueService issueService;

    @PostMapping("/create")
    public ResponseEntity<Result> createPost(@RequestBody IssueCreateRequestDto issueCreateRequestDto){
        Long issueId = issueService.createIssue(issueCreateRequestDto);
        if(issueId > 0L){
            Result result = Result.builder()
                            .isSuccess(true)
                            .message("Success Created")
                            .data(issueId)
                            .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(false)
                .message("Success Created")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

    @DeleteMapping("/close/{issuePk}")
    public ResponseEntity<Result> closedIssue(@PathVariable("issuePk") Long issuePk){
        Long delPk = issueService.closedIssue(issuePk);
        if(delPk > 0L) {
            Result result = Result.builder()
                            .isSuccess(true)
                            .message("closed : "+ delPk)
                            .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                            .isSuccess(false)
                            .message("Failed close : "+ delPk + "존재하지 않는 이슈")
                            .build();
            return ResponseEntity.badRequest().body(result);
        }
        
    }

    @GetMapping("/{issuePk}")
    public ResponseEntity<Result> readIssue(@PathVariable Long issuePk){
        IssueResponseDto rspDto = issueService.getOneIssue(issuePk);
        if(rspDto.chkData()){
            Result result = Result.builder()
                .isSuccess(true)
                .message("Success load data")
                .data(rspDto)
                .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(false)
                .message("Failed load data")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }
    @PatchMapping("/changeStatus")
    public ResponseEntity<Result> changeStatus(@RequestBody ChangeStatusOrLabel changeStatusOrLabel){
        boolean isSuccess = issueService.changeStatus(changeStatusOrLabel);
        if(isSuccess){
            Result result = Result.builder()
                                .data(changeStatusOrLabel)
                                .isSuccess(isSuccess)
                                .message(changeStatusOrLabel.getValue() + "로 Status 변경 완료")
                                .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(isSuccess)
                .message("Failed change status")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }
    @PatchMapping("/changeLabel")
    public ResponseEntity<Result> changeLabel(@RequestBody ChangeStatusOrLabel changeStatusOrLabel) {
        boolean isSuccess = issueService.changeLabel(changeStatusOrLabel);
        if(isSuccess){
            Result result = Result.builder()
                                .data(changeStatusOrLabel)
                                .isSuccess(isSuccess)
                                .message(changeStatusOrLabel.getValue() + "로 Label로 변경완료")
                                .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(isSuccess)
                .message("Failed change Label")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }
}
