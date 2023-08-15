package kea.alog.issue.service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.IssueDto.*;
import kea.alog.issue.domain.issue.*;
import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Issue getIssueByPk(Long issuePk) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (issue.isPresent()) {
            return issue.get();
        } 
        return null;

    }

    @Transactional
    public boolean changeStatus(Long issueId, String status) {
        try {
            Optional<Issue> optIssue = issueRepository.findById(issueId);
            if (optIssue.isPresent()) {
                IssueStatus issueStatus = Enum.valueOf(IssueStatus.class,status);
                optIssue.get().setIssueStatus(issueStatus);
                optIssue.get().setIssueOpened(true);
                if (optIssue.get().getIssueStatus().equals(IssueStatus.DONE)){
                    optIssue.get().setIssueOpened(false);
                }
                return true;
            } 
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        
        }
        return false;
    }

    @Transactional
    public boolean changeLabel(Long issueId, String label) {
        try {
            Optional<Issue> optIssue = issueRepository.findById(issueId);
            if (optIssue.isPresent()) {
                IssueLabel issueLabel = Enum.valueOf(IssueLabel.class,label);
                optIssue.get().setIssueLabel(issueLabel);
                return true;
            } 
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        
        }
        return false;
    }

    @Transactional
	public boolean changeDate(Long issuePk, LocalDateTime startDate, LocalDateTime endDate) {
        try {
            Optional<Issue> optIssue = issueRepository.findById(issuePk);
            if (optIssue.isPresent()) {
                optIssue.get().setStartDate(startDate);
                optIssue.get().setEndDate(endDate);
                return true;
            } 
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        
        }
        return false;
	}

    @Transactional
	public boolean changeAssignee(Long issuePk, Long issueAssigneePk) {
        try {
            Optional<Issue> optIssue = issueRepository.findById(issuePk);
            if (optIssue.isPresent()) {
                optIssue.get().setIssueAssigneePk(issueAssigneePk);
                return true;
            } 
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        
        }
        return false;
	}

    @Transactional
	public List<Issue> getIssueByStatus(Long pjPk, String issueStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Issue> issuePage = issueRepository.findAllByPjPkAndIssueStatusOrderByIssuePkDesc(pjPk, Enum.valueOf(IssueStatus.class, issueStatus), pageable);

        if (issuePage == null){
            return null;
        }
        return issuePage.stream()
                    .collect(Collectors.toList());
	}

    @Transactional
    public List<Issue> getIssueByLabel(Long pjPk, String issueLabel, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Issue> issuePage = issueRepository.findAllByPjPkAndIssueLabelOrderByIssuePkDesc(pjPk, Enum.valueOf(IssueLabel.class, issueLabel), pageable);
        if (issuePage == null){
            return null;
        }


        return issuePage.stream()
                    .collect(Collectors.toList());
    }

    @Transactional
    public List<Issue> getIssueByAssignee(Long issueAssigneePk, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Issue> issuePage = issueRepository.findAllByIssueAssigneePkOrderByIssuePkDesc(issueAssigneePk, pageable);
        if (issuePage == null){
            return null;
        }


        return issuePage.stream()
                    .collect(Collectors.toList());
    }

    @Transactional
	public List<Issue> getIssueByAuthor(Long issueAuthorPk, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Issue> issuePage = issueRepository.findAllByIssueAuthorPkOrderByIssuePkDesc(issueAuthorPk, pageable);
        if (issuePage == null){
            return null;
        }


        return issuePage.stream()
                    .collect(Collectors.toList());
		
	}

    @Transactional
    public Integer getIssueCntByAssignee(Long issueAssigneePk) {
        List<Issue> issues= issueRepository.findAllByIssueAssigneePk(issueAssigneePk);
        return (issues==null ? null : issues.size());
    }

    @Transactional
	public Integer getIssueCntByAuthor(Long issueAuthorPk) {
		List<Issue> issues= issueRepository.findAllByIssueAuthorPk(issueAuthorPk);
        return (issues==null ? null : issues.size());
	}

    @Transactional
    public Integer getIssueCntByStatus(Long pjPk, String issueStatus) {
        List<Issue> issues= issueRepository.findAllByPjPkAndIssueStatus(pjPk, Enum.valueOf(IssueStatus.class, issueStatus));
        return (issues==null ? null : issues.size());
    }

    @Transactional
    public Integer getIssueCntByLabel(Long pjPk, String issueLabel) {
        List<Issue> issues= issueRepository.findAllByPjPkAndIssueLabel(pjPk, Enum.valueOf(IssueLabel.class, issueLabel));
        return (issues==null ? null : issues.size());
    }


    @Transactional
    public String changeImage(Long issuePk, String fileLink) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (!issue.isPresent()) {
            return null;
        }
        issue.get().setFileLink(fileLink);
        return fileLink;

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



}