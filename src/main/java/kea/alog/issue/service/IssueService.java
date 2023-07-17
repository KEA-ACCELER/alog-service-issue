package kea.alog.issue.service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.IssueDto.*;
import kea.alog.issue.domain.issue.*;
import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {
    @Autowired
    IssueRepository issueRepository;

    @Transactional
    public Long createIssue(IssueCreateRequestDto issueCreateRequestDto ){
        Issue savIssue = Issue.builder()
                    .pjPk(issueCreateRequestDto.getPjPk())
                    .teamPk(issueCreateRequestDto.getTeamPk())
                    .issueAuthorPk(issueCreateRequestDto.getIssueAuthorPk())
                    .issueTitle(issueCreateRequestDto.getIssueTitle())
                    .issueDescription(issueCreateRequestDto.getIssueDescription())
                    .issueStatus(IssueStatus.valueOf(issueCreateRequestDto.getIssueStatus()))
                    .issueLabel(IssueLabel.valueOf(issueCreateRequestDto.getIssueLabel()))
                    .todoPk(issueCreateRequestDto.getTodoPk())
                    .issueOpened(issueCreateRequestDto.getIssueOpened())
                    .issueAssigneePk(issueCreateRequestDto.getIssueAssigneePk())
                    .fileLink(issueCreateRequestDto.getFileLink())
                    .issueId(issueCreateRequestDto.getIssueId())
                    .build();
        issueRepository.save(savIssue);
        return savIssue.getIssueId();
    }

    @Transactional
    public Long deleteIssue(Long issuePk){
        Optional<Issue> optIssue = issueRepository.findById(issuePk);
        if(optIssue.isPresent()){
            issueRepository.delete(optIssue.get());
            return issuePk;
        } else return 0L;
        
        
    }

    @Transactional
    public IssueResponseDto getOneIssue(Long issuePk){
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if(issue.isPresent()){
            Issue data = issue.get();
            IssueResponseDto rspDto = IssueResponseDto.builder()
                                        .issuePk(data.getIssuePk())
                                        .pjPk(data.getPjPk())
                                        .teamPk(data.getTeamPk())
                                        .issueTitle(data.getIssueTitle())
                                        .issueDescription(data.getIssueDescription())
                                        .issueAuthorPk(data.getIssueAuthorPk())
                                        .issueStatus(data.getIssueStatus().toString())
                                        .issueLabel(data.getIssueLabel().toString())
                                        .todoPk(data.getTodoPk())
                                        .issueOpened(data.getIssueOpened())
                                        .issueAssigneePk(data.getIssueAssigneePk())
                                        .fileLink(data.getFileLink())
                                        .issueId(data.getIssueId())
                                        .build();
            return rspDto;
        }
        else return IssueResponseDto.builder().build();
    }

}
