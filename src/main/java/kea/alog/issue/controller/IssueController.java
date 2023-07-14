package kea.alog.issue.controller;

import jakarta.servlet.http.HttpServletRequest;
import kea.alog.issue.controller.dto.IssueDto;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.service.IssueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @PostMapping("/create")
    public ResponseEntity<Issue> createPost(@RequestBody IssueDto.IssueCreateRequestDto issueCreateRequestDto){
        //String userId = feignService.getUserInfo(request).get().getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(issueCreateRequestDto));
    }

    @DeleteMapping("/delete/{issuePk}")
    public ResponseEntity<String> deleteIssue(@PathVariable("issuePk") Long issuePk){
        return ResponseEntity.ok("Deleted : "+ issueService.deleteIssue(issuePk));
    }

}
