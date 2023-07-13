package kea.alog.issue.service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.IssueDto;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {
    @Autowired
    IssueRepository issueRepository;

    @Transactional
    public Issue createIssue(IssueDto.IssueCreateRequestDto issueCreateRequestDto ){
        return issueRepository.save(issueCreateRequestDto.toEntity());
    }

    @Transactional
    public Long deleteIssue(Long issuePk){
        issueRepository.deleteByIssuePk(issuePk);
        return issuePk;
    }

    @Transactional
    public IssueDto.IssueResponseDto getOneIssue(Long issuePk){
        Issue issue = issueRepository.findByIssuePk(issuePk);
        return IssueDto.IssueResponseDto.builder().issue(issue)
                .build();
    }

}
