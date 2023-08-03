package kea.alog.issue.domain.issue;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findAllByPjPk(Long pjPk);
    List<Issue> findAllByIssueAssigneePk(Long issueAssigneePk);
    Page<Issue> findAllByIssueAssigneePkOrderByIssuePkDesc(Long issueAssigneePk, Pageable pageable);
}
