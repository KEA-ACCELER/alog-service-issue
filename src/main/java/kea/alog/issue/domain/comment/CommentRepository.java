package kea.alog.issue.domain.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import kea.alog.issue.domain.issue.Issue;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByIssuePk(Issue issuePk, Pageable pageable);
}
