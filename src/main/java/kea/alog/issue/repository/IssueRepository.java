package kea.alog.issue.repository;

import kea.alog.issue.domain.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface IssueRepository extends JpaRepository<Issue, Long> {
    
}
