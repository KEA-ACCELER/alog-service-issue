package kea.alog.issue.controller;

import kea.alog.issue.config.Result;
import kea.alog.issue.controller.dto.IssueDto.*;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issue")
public class IssueController {
    final private IssueService issueService;

    @Operation(summary = "이슈 생성 (Aggr)")
    @PostMapping("")
    public ResponseEntity<Long> createPost(@RequestBody IssueCreateRequestDto issueCreateRequestDto, 
                                                @RequestParam(value = "fileLink") String fileLink){
        Long issueId = issueService.createIssue(issueCreateRequestDto, fileLink);
        return ResponseEntity.ok(issueId);
                                                
    }

    @Operation(summary = "이슈 하나 조회")
    @GetMapping("")
    public ResponseEntity<Issue> readIssue(@RequestParam Long issuePk){
        return ResponseEntity.ok(issueService.readIssue(issuePk));
    }
    
    @Operation(summary = "이슈 상태 변경")
    @PatchMapping("/status")
    public ResponseEntity<Result> changeStatus(@RequestParam("issuePk") Long issuePk, 
    @Parameter(description = "이 단어중 하나 [TODO, INPROGRESS, DONE, EMERGENCY]") @RequestParam("issueStatus") String status){
    
        boolean isSuccess = issueService.changeStatus(issuePk, status);
        if(isSuccess){
            Result result = Result.builder()
                                .isSuccess(isSuccess)
                                .message("change to " + status)
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
 
    @Operation(summary = "이슈 라벨 변경")
    @PatchMapping("/label")
    public ResponseEntity<Result> changeLabel(@RequestParam("issuePk") Long issuePk,
    @Parameter(description = "이 단어중 하나 [NONE, BUG, DOCUMENTATION, DUPLICATE_ETC]") @RequestParam("issueLabel") String label){
        boolean isSuccess = issueService.changeLabel(issuePk, label);
        if(isSuccess){
            Result result = Result.builder()
                                .isSuccess(isSuccess)
                                .message("change to " + label)
                                .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(isSuccess)
                .message("Failed change label")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "이슈 시작/끝 일자 변경")
    @PatchMapping("/date")
    public ResponseEntity<Result> changeDate(@RequestParam("issuePk") Long issuePk,
    @Parameter(description = "시작일자") @RequestParam("startDate") LocalDateTime startDate,
    @Parameter(description = "종료일자") @RequestParam("endDate") LocalDateTime endDate){
        boolean isSuccess = issueService.changeDate(issuePk, startDate, endDate);
        if(isSuccess){
            Result result = Result.builder()
                                .isSuccess(isSuccess)
                                .message("change to " + startDate + " ~ " + endDate)
                                .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(isSuccess)
                .message("Failed change date")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "이슈 assignee 변경")
    @PatchMapping("/assignee")
    public ResponseEntity<Result> changeAssignee(@RequestParam("issuePk") Long issuePk,
    @Parameter(description = "이슈 담당자 pk") @RequestParam("issueAssigneePk") Long issueAssigneePk){
        boolean isSuccess = issueService.changeAssignee(issuePk, issueAssigneePk);
        if(isSuccess){
            Result result = Result.builder()
                                .isSuccess(isSuccess)
                                .message("change to " + issueAssigneePk)
                                .build();
            return ResponseEntity.ok().body(result);
        } else {
            Result result = Result.builder()
                .isSuccess(isSuccess)
                .message("Failed change assignee")
                .build();
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Operation(summary = "이슈 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<List<Issue>> getAllIssue(@RequestParam("pkPk") Long pjPk,
    @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(issueService.getAllIssues(pjPk, page, size));
    }

    @Operation(summary = "issueStatus 별로 특정 개수만큼을 반환")
    @GetMapping("/by/status")
    public ResponseEntity<List<Issue>> getIssueByStatus(@RequestParam("pkPk") Long pjPk,
    @Parameter(description = "이 단어중 하나 [TODO, INPROGRESS, DONE, EMERGENCY]" ) @RequestParam("issueStatus") String issueStatus,
    @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(issueService.getIssueByStatus(pjPk, issueStatus, page, size));
    }

    @Operation(summary = "IssueLabel 별로 특정 개수 만큼을 반환")
    @GetMapping("/by/label")
    public ResponseEntity<List<Issue>> getIssueByLabel(@RequestParam("pkPk") Long pjPk,
    @Parameter(description = "이 단어중 하나 [NONE, BUG, DOCUMENTATION, DUPLICATE_ETC]") @RequestParam("issueLabel") String issueLabel,
    @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(issueService.getIssueByLabel(pjPk, issueLabel, page, size));
    }

    @Operation(summary = "IssueAssignee 에 할당된 이슈 반환")
    @GetMapping("/by/assignee")
    public ResponseEntity<List<Issue>> getIssueByAssignee(@RequestParam("issueAssigneePk") Long issueAssigneePk,
    @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(issueService.getIssueByAssignee(issueAssigneePk, page, size));
    }

    @Operation(summary = "IssueAuthor가 작성한 이슈 반환")
    @GetMapping("/by/author")
    public ResponseEntity<List<Issue>> getIssueByAuthor(@RequestParam("issueAuthorPk") Long issueAuthorPk,
    @RequestParam("page") int page, @RequestParam("size") int size){
        return ResponseEntity.ok(issueService.getIssueByAuthor(issueAuthorPk, page, size));
    }

    @Operation(summary = "IssueStatus 별로 개수 반환")
    @GetMapping("/by/status/cnt")
    public ResponseEntity<Integer> getIssueCntByStatus(@RequestParam("pkPk") Long pjPk,
    @Parameter(description = "이 단어중 하나 [TODO, INPROGRESS, DONE, EMERGENCY]") @RequestParam("issueStatus") String issueStatus){
        return ResponseEntity.ok(issueService.getIssueCntByStatus(pjPk, issueStatus));
    }

    @Operation(summary = "IssueLabel 별로 개수 반환")
    @GetMapping("/by/label/cnt")
    public ResponseEntity<Integer> getIssueCntByLabel(@RequestParam("pkPk") Long pjPk,
    @Parameter(description = "이 단어중 하나 [NONE, BUG, DOCUMENTATION, DUPLICATE_ETC]") @RequestParam("issueLabel") String issueLabel){
        return ResponseEntity.ok(issueService.getIssueCntByLabel(pjPk, issueLabel));
    }

    @Operation(summary = "IssueAssignee 에 할당된 개수 반환")
    @GetMapping("/by/assignee/cnt")
    public ResponseEntity<Integer> getIssueCntByAssignee(@RequestParam("issueAssigneePk") Long issueAssigneePk){
        return ResponseEntity.ok(issueService.getIssueCntByAssignee(issueAssigneePk));
    }

    @Operation(summary = "IssueAuthor가 작성한 개수 반환")
    @GetMapping("/by/author/cnt")
    public ResponseEntity<Integer> getIssueCntByAuthor(@RequestParam("issueAuthorPk") Long issueAuthorPk){
        return ResponseEntity.ok(issueService.getIssueCntByAuthor(issueAuthorPk));
    }


    @Operation(summary = "이미지 수정 (Aggr)")
    @PatchMapping("/image")
    public ResponseEntity<String> changeImage(@RequestParam("issuePk") Long issuePk,
    @RequestParam("fileLink") String fileLink){
        return ResponseEntity.ok().body(issueService.changeImage(issuePk, fileLink));
    }


}
