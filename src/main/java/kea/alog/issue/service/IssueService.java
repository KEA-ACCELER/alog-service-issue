package kea.alog.issue.service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.IssueDto.*;
import kea.alog.issue.domain.issue.*;
import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {
    @Autowired
    IssueRepository issueRepository;

    // 이슈 생성
    @Transactional
    public Long createIssue(IssueCreateRequestDto reqData, String fileLink) {
        if (fileLink == null)
            fileLink = "";
        log.info("reqData : " + reqData.toString());
        log.info("fileLink : " + fileLink);

        Issue issue = reqData.toEntity(fileLink);
        
        return issueRepository.save(issue).getIssuePk();

    }

    @Transactional
    public Long closedIssue(Long issuePk) {
        Optional<Issue> optIssue = issueRepository.findById(issuePk);
        if (optIssue.isPresent() && optIssue.get().getIssueOpened()) {
            Issue issue = optIssue.get().toBuilder().issueOpened(false).build();
            issueRepository.save(issue);
            return issuePk;
        } else
            return 0L;
    }

    @Transactional
    public Issue getIssueByPk(Long issuePk) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (issue.isPresent()) {
            return issue.get();
        } 
        return null;

    }

    @Transactional
    public boolean changeStatus(Long issueId, ChangeStatusOrLabelDto reqData) {
        try {
            Optional<Issue> optIssue = issueRepository.findById(issueId);
            if (optIssue.isPresent()) {
                IssueStatus issueStatus = IssueStatus.valueOf(reqData.getValue());
                Issue issue = optIssue.get().toBuilder().issueStatus(issueStatus).build();
                issueRepository.save(issue);
                if (optIssue.get().getIssueStatus().equals(IssueStatus.DONE))
                    closedIssue(issueId);
                return true;
            } else
                return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean changeLabel(Long issuePk, ChangeStatusOrLabelDto reqData) {
        try {
            Optional<Issue> optIssue = issueRepository.findById(issuePk);
            if (optIssue.isPresent()) {
                IssueLabel issueLabel = IssueLabel.valueOf(reqData.getValue());
                Issue issue = optIssue.get().toBuilder().issueLabel(issueLabel).build();
                issueRepository.save(issue);
                return true;
            } else
                return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * @param issueId
     * @return
    //  */
    // @Transactional
    // public List<IssueResponseDto> getPjIssueList(Long pjPk) {
    //     List<Issue> optIssue = issueRepository.findAllByPjPk(pjPk);
    //     List<IssueResponseDto> rspDto = new ArrayList<>();

    //     for (Issue issue : optIssue) {
    //         IssueResponseDto addList = IssueResponseDto.builder()
    //                 .issuePk(issue.getIssuePk())
    //                 .pjPk(issue.getPjPk())
    //                 .teamPk(issue.getTeamPk())

    //                 .issueDescription(issue.getIssueDescription())
    //                 .issueAuthorPk(issue.getIssueAuthorPk())
    //                 .issueStatus(issue.getIssueStatus().toString())
    //                 .issueLabel(issue.getIssueLabel().toString())
    //                 .todoPk(issue.getTopicPk())
    //                 .issueOpened(issue.getIssueOpened())
    //                 .issueAssigneePk(issue.getIssueAssigneePk())
    //                 .fileLink(issue.getFileLink())
    //                 .issueId(issue.getIssueId())
    //                 .build();
    //         rspDto.add(addList);
    //     }
    //     return rspDto;
    // }

    // @Transactional
    // public List<IssueResponseDto> getAllUserIssueList(Long userPk) {
    //     List<Issue> allList = issueRepository.findAllByIssueAssigneePk(userPk);
    //     List<IssueResponseDto> rspDto = new ArrayList<>();
    //     for (Issue idx : allList) {
    //         IssueResponseDto addList = IssueResponseDto.builder()
    //                 .fileLink(idx.getFileLink())
    //                 .issueAssigneePk(idx.getIssueAssigneePk())
    //                 .issueAuthorPk(idx.getIssueAuthorPk())
    //                 .pjPk(idx.getPjPk())
    //                 .teamPk(idx.getTeamPk())
    //                 .issuePk(idx.getIssuePk())

    //                 .issueDescription(idx.getIssueDescription())
    //                 .issueStatus(idx.getIssueStatus().toString())
    //                 .issueLabel(idx.getIssueLabel().toString())
    //                 .todoPk(idx.getTopicPk())
    //                 .issueOpened(idx.getIssueOpened())
    //                 .issueId(idx.getIssueId())
    //                 .build();
    //         rspDto.add(addList);
    //     }
    //     return rspDto;
    // }

    /**
     * @Todo 페이징 추가할 것
     * @param userPk
     * @return
     */
    // @Transactional
    // public List<IssueResponseDto> getPageUserIssueList(Long userPk) {
    //     List<Issue> allList = issueRepository.findAllByIssueAssigneePk(userPk);
    //     List<IssueResponseDto> rspDto = new ArrayList<>();
    //     for (Issue idx : allList) {
    //         IssueResponseDto addList = IssueResponseDto.builder()
    //                 .fileLink(idx.getFileLink())
    //                 .issueAssigneePk(idx.getIssueAssigneePk())
    //                 .issueAuthorPk(idx.getIssueAuthorPk())
    //                 .pjPk(idx.getPjPk())
    //                 .teamPk(idx.getTeamPk())
    //                 .issuePk(idx.getIssuePk())

    //                 .issueDescription(idx.getIssueDescription())
    //                 .issueStatus(idx.getIssueStatus().toString())
    //                 .issueLabel(idx.getIssueLabel().toString())
    //                 .todoPk(idx.getTopicPk())
    //                 .issueOpened(idx.getIssueOpened())
    //                 .issueId(idx.getIssueId())
    //                 .build();
    //         rspDto.add(addList);
    //     }
    //     return rspDto;
    // }
    /**
     * Todo : 프로젝트별 리스트, 해결할 이슈 AssigneePk의 전체 리스트와 페이징을 한 리스트
     */
}
