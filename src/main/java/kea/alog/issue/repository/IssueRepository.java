package kea.alog.issue.repository;

import jakarta.persistence.EntityManager;
import kea.alog.issue.domain.issue.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface IssueRepository extends JpaRepository<Issue, Long> {
    public void deleteByIssuePk(Long issuePk);

    public Issue findByIssuePk(Long issuePk);

//    public Page<Issue> findAllByIssueAuthor(Long userPk, Pageable pageable);

//    public List<Issue> findAllByIssueAuthor(Long userPk);

}
