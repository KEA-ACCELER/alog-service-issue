package kea.alog.issue.controller;

import jakarta.servlet.http.HttpServletRequest;
import kea.alog.issue.config.Result;
import kea.alog.issue.controller.dto.IssueDto.*;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueController {
    @Autowired
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

    @DeleteMapping("/delete/{issuePk}")
    public ResponseEntity<Result> deleteIssue(@PathVariable("issuePk") Long issuePk){
        Long delPk = issueService.deleteIssue(issuePk);
        if(delPk > 0L) {
            Result result = Result.builder()
                            .isSuccess(true)
                            .message("Deleted : "+ delPk)
                            .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                            .isSuccess(false)
                            .message("Failed Delete : "+ delPk)
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
}
