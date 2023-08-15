package kea.alog.issue.service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.NotiDto;
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

    @Autowired
    NotiFeign notiFeign;

    // 이슈 생성
    @Transactional
    public Long createIssue(IssueCreateRequestDto reqData, String fileLink) {
        
        Issue issue =  issueRepository.save(reqData.toEntity(fileLink));

        if (issue.getIssueAssigneePk() != null){
            NotiDto.Message message = NotiDto.Message.builder()
                                .UserPk(issue.getIssueAssigneePk())
                                .MsgContent(issue.getIssueId()+" 에 지정되셨습니다")
                                .build();
            String notification = notiFeign.createNoti(message);
            log.info("notification : "+notification);
        }
        return issue.getIssuePk();

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
                String notification= notiFeign.createNoti(NotiDto.Message.builder()
                                        .UserPk(issueAssigneePk)
                                        .MsgContent(optIssue.get().getIssueId()+" 에 지정되셨습니다")
                                        .build());
                log.info("notification : "+notification);
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

    @Transactional
    public List<Issue> getAllIssues (Long pjPk, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Issue> issuePage = issueRepository.findAllByPjPkOrderByIssuePkDesc(pjPk, pageable);
        if (issuePage == null){
            return null;
        }
        return issuePage.stream().collect(Collectors.toList());
    }


}