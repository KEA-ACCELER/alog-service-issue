package kea.alog.issue.domain.issue;
import java.io.Serializable;

import lombok.*;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import kea.alog.issue.domain.BaseTimeEntity;

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

    //<yorkie>
    @Column(name = "issue_title", length=60)
    private String issueTitle;

    @Column(name = "issue_description", length=1000)
    private String issueDescription;

    @Column(name = "issue_author_nn", length=10)
    private String issueAuthorNn;

    @Column(name = "issue_author_pk")
    private Long issueAuthorPk;

    @Column(name = "issue_status", length=20)
    private String issueStatus;

    @Column(name = "issue_label", length=20)
    private String issueLabel;

    @Column(name="topic_pk")
    private Long topicPk;

    @Column(name="issue_opened")
    private boolean issueOpened;

    @Column(name="issue_assignee_pk")
    private Long issueAssigneePk;

    @Column(name="issue_assignee_nn", length=10)
    private String issueAssigneeNn;

    @Column(name="file_link")
    private String fileLink;

    @Builder
    public Issue(String issueTitle, String issueDescription, String issueAuthorNn, Long issueAuthorPk, String issueStatus, String issueLabel, Long topicPk, boolean issueOpened, Long issueAssigneePk, String issueAssigneeNn, String fileLink){
        this.issueTitle = issueTitle;
        this.issueDescription = issueDescription;
        this.issueAuthorNn = issueAuthorNn;
        this.issueAuthorPk = issueAuthorPk;
        this.issueStatus = issueStatus;
        this.issueLabel = issueLabel;
        this.topicPk = topicPk;
        this.issueOpened = issueOpened;
        this.issueAssigneePk = issueAssigneePk;
        this.issueAssigneeNn = issueAssigneeNn;
        this.fileLink = fileLink;
    }


}
