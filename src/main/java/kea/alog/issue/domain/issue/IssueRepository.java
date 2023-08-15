package kea.alog.issue.domain.issue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    
    List<Issue> findAllByPjPk(Long pjPk);
    List<Issue> findAllByIssueAssigneePk(Long issueAssigneePk);
    List<Issue> findAllByPjPkAndIssueStatus(Long pjPk, IssueStatus issueStatus);
    List<Issue> findAllByPjPkAndIssueLabel(Long pjPk, IssueLabel issueLabel);
    List<Issue> findAllByIssueAuthorPk(Long issueAuthorPk);
    

    Page<Issue> findAllByIssueAssigneePkOrderByIssuePkDesc(Long issueAssigneePk, Pageable pageable);
    Page<Issue> findAllByPjPkAndIssueStatusOrderByIssuePkDesc(Long pjPk, IssueStatus issueStatus, Pageable pageable);
    Page<Issue> findAllByPjPkAndIssueLabelOrderByIssuePkDesc(Long pjPk, IssueLabel issueLabel, Pageable pageable);
    Page<Issue> findAllByIssueAuthorPkOrderByIssuePkDesc(Long issueAuthorPk, Pageable pageable);
    Page<Issue> findAllByPjPkOrderByIssuePkDesc(Long pjPk, Pageable pageable);
}
