package kea.alog.issue.domain.issue;
import java.io.Serializable;
import java.time.LocalDateTime;

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

    @Column(name = "issue_description", length=1000)
    private String issueDescription;

    @Setter
    @Column(name = "issue_status", length=20)
    private IssueStatus issueStatus;

    @Setter
    @Column(name = "issue_label", length=20)
    private IssueLabel issueLabel;

    @Setter
    @Column(name = "topic_pk")
    private Long topicPk;

    @Setter
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Setter
    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Setter
    @Column(name="issue_opened")
    private Boolean issueOpened;

    @Setter
    @Column(name="issue_assignee_pk")
    private Long issueAssigneePk;

    @Setter
    @Column(name="file_link", length=500)
    private String fileLink;

    @Setter
    @Column(name = "issue_id")
    private String issueId;

    @Builder(toBuilder = true)
    public Issue(Long pjPk, LocalDateTime endDate, LocalDateTime startDate, Long teamPk, Long topicPk, String issueDescription, Long issueAuthorPk, IssueStatus issueStatus, IssueLabel issueLabel, Boolean issueOpened, Long issueAssigneePk, String fileLink, String issueId){
        this.pjPk = pjPk;
        this.teamPk = teamPk;
        this.startDate = startDate;
        this.endDate = endDate;
        this.issueAuthorPk = issueAuthorPk;
        this.issueDescription = issueDescription;
        this.issueStatus = issueStatus;
        this.issueLabel = issueLabel;
        this.topicPk = topicPk;
        this.issueOpened = issueOpened;
        this.issueAssigneePk = issueAssigneePk;
        this.fileLink = fileLink;
        this.issueId = issueId;
    }


}
