package kea.alog.issue.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.IssueDto.IssueCreateRequestDto;
import kea.alog.issue.controller.dto.NotiDto;
import kea.alog.issue.domain.comment.CommentRepository;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.domain.issue.IssueRepository;
import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueService {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    NotiFeign notiFeign;

    @Autowired
    CommentRepository commentRepository;



    // 이슈 생성
    @Transactional
    public Long createIssue(IssueCreateRequestDto reqData, String fileLink) {
        
        Issue issue =  issueRepository.save(reqData.toEntity(fileLink));
        issue.setIssueId("dragable-"+issue.getIssuePk());
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
        log.info("changed fileLink : ", issue.get().getFileLink());
        return "success";

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

    @Transactional
	public Issue readIssue(Long issuePk) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (!issue.isPresent()) {
            return null;
        }
        return issue.get();
	}

    @Transactional
	public String deleteIssue(Long issuePk) {

        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (!issue.isPresent()) {
            return null;
        }
		//관련 코멘트 전체 삭제
        commentRepository.deleteAllByIssuePk(issue.get());
        log.info("comment Deleted");
        // 이슈 삭제
        issueRepository.deleteById(issuePk);
        log.info("issue Deleted");

        return "success";
	}

    //topic pk의 done issue 개수 비율(100%) 반환
    @Transactional
    public Float getDoneIssueRatio(Long topicPk) {
        List<Issue> issues = issueRepository.findAllByTopicPk(topicPk);
        if (issues == null){
            return null;
        }
        int doneCnt = 0;
        for (Issue issue : issues){
            if (issue.getIssueStatus().equals(IssueStatus.DONE)){
                doneCnt++;
            }
        }
        return (float)doneCnt/issues.size();
    }

    @Transactional
    public String changeTopic(Long issuePk, Long topicPk) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (!issue.isPresent()) {
            return null;
        }
        issue.get().setTopicPk(topicPk);
        return issuePk+" isuue is move to " + topicPk;
    }


}