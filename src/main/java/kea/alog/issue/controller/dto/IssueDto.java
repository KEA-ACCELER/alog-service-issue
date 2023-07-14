package kea.alog.issue.controller.dto;

import jakarta.persistence.Column;
import kea.alog.issue.domain.issue.Issue;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class IssueDto {
    @Getter
    @RequiredArgsConstructor
    public static class IssueCreateRequestDto {
        private String issueTitle;
        private String issueDescription;
        private String issueAuthorNn;
        private Long issueAuthorPk;
        private String issueStatus;
        private String issueLabel;
        private Long topicPk;
        private boolean issueOpened;
        private Long issueAssigneePk;
        private String issueAssigneeNn;
        private String fileLink;

        public Issue toEntity() {
            return Issue.builder()
                    .issueTitle(issueTitle)
                    .issueDescription(issueDescription)
                    .issueAuthorNn(issueAuthorNn)
                    .issueAuthorPk(issueAuthorPk)
                    .issueStatus(issueStatus)
                    .issueLabel(issueLabel)
                    .topicPk(topicPk)
                    .issueOpened(issueOpened)
                    .issueAssigneePk(issueAssigneePk)
                    .issueAssigneeNn(issueAssigneeNn)
                    .fileLink(fileLink)
                    .build();

        }


    }
    @Getter
    @RequiredArgsConstructor
    public static class IssueResponseDto {
        private String issueTitle;
        private String issueDescription;
        private String issueAuthorNn;
        private Long issueAuthorPk;
        private String issueStatus;
        private String issueLabel;
        private Long topicPk;
        private boolean issueOpened;
        private Long issueAssigneePk;
        private String issueAssigneeNn;
        private String fileLink;

        @Builder
        public IssueResponseDto(Issue issue, String issueTitle, String issueDescription, String issueAuthorNn, Long issueAuthorPk, String issueStatus, String issueLabel, Long topicPk, boolean issueOpened, Long issueAssigneePk, String issueAssigneeNn, String fileLink) {
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
}

