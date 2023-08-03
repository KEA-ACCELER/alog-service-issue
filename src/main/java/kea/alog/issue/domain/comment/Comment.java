package kea.alog.issue.domain.comment;
import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.issue.domain.BaseTimeEntity;
import kea.alog.issue.domain.issue.Issue;
import lombok.*;

@Component
@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Comment extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_pk")
    private Long commentPk;

    @Column(name = "p_pk")
    public Long pjPk;

    @Column(name = "team_pk")
    public Long teamPk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_pk")
    private Issue issuePk;

    @Column(name = "comment_content", length=200)
    private String commentContent;

    @Column(name = "comment_author_pk")
    private Long commentAuthorPk;
    
    @Builder(toBuilder = true)
    public Comment(Long pjPk, Long teamPk, String commentContent, Long commentAuthorPk, Issue issuePk){
        this.issuePk = issuePk;
        this.pjPk = pjPk;
        this.teamPk = teamPk;
        this.commentContent = commentContent;
        this.commentAuthorPk = commentAuthorPk;
    }
}
