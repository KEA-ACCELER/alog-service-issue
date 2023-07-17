package kea.alog.issue.domain.issue;
import java.io.Serializable;

import lombok.*;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.issue.domain.BaseTimeEntity;
import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;

@Component
@Entity
@Table(name = "issue")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Issue extends BaseTimeEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_pk")
    private Long issuePk;

    @Column(name = "p_pk")
    public Long pjPk;

    @Column(name = "team_pk")
    public Long teamPk;

    @Column(name = "issue_author_pk")
    private Long issueAuthorPk;

    //<yorkie>
    @Column(name = "issue_title", length=60)
    private String issueTitle;

    @Column(name = "issue_description", length=1000)
    private String issueDescription;

    @Column(name = "issue_status", length=20)
    private IssueStatus issueStatus;

    @Column(name = "issue_label", length=20)
    private IssueLabel issueLabel;

    @Column(name = "todo_pk")
    private Long todoPk;

    @Column(name="issue_opened")
    private Boolean issueOpened;

    @Column(name="issue_assignee_pk")
    private Long issueAssigneePk;

    // @Column(name = "issue_author_nn", length=10)
    // private String issueAuthorNn;

    // @Column(name="topic_pk")
    // private Long topicPk;

    // @Column(name="issue_assignee_nn", length=10)
    // private String issueAssigneeNn;

    @Column(name="file_link")
    private String fileLink;

    @Column(name = "issue_id")
    private Long issueId;

    @Builder(toBuilder = true)
    public Issue(Long pjPk, Long teamPk, Long todoPk, String issueTitle, String issueDescription, Long issueAuthorPk, IssueStatus issueStatus, IssueLabel issueLabel, Boolean issueOpened, Long issueAssigneePk, String fileLink, Long issueId){
        this.pjPk = pjPk;
        this.teamPk = teamPk;
        this.issueAuthorPk = issueAuthorPk;
        this.issueTitle = issueTitle;
        this.issueDescription = issueDescription;
        this.issueStatus = issueStatus;
        this.issueLabel = issueLabel;
        this.todoPk = todoPk;
        this.issueOpened = issueOpened;
        this.issueAssigneePk = issueAssigneePk;
        this.fileLink = fileLink;
        this.issueId = issueId;
    }


}
