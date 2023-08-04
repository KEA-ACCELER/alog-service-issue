package kea.alog.issue.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class IssueDto {
    @Getter
    @NoArgsConstructor
    public static class IssueCreateRequestDto {
        private Long pjPk;
        private Long teamPk;
        private String issueTitle;
        private String issueDescription;
        private Long issueAuthorPk;
        private String issueStatus;
        private String issueLabel;
        private Long todoPk;
        private Boolean issueOpened;
        private Long issueAssigneePk;
        private String fileLink;
        private Long issueId;
        @Builder
        public IssueCreateRequestDto(Long pjPk, Long teamPk, Long todoPk, String issueTitle, String issueDescription, Long issueAuthorPk, String issueStatus, String issueLabel, Boolean issueOpened, Long issueAssigneePk, String fileLink, Long issueId) {
            this.pjPk = pjPk;
            this.teamPk = teamPk;
            this.issueTitle = issueTitle;
            this.issueDescription = issueDescription;
            this.issueAuthorPk = issueAuthorPk;
            this.issueStatus = issueStatus;
            this.issueLabel = issueLabel;
            this.todoPk = todoPk;
            this.issueOpened = issueOpened;
            this.issueAssigneePk = issueAssigneePk;
            this.fileLink = fileLink;
            this.issueId = issueId;
        }

    }

    @Getter
    @NoArgsConstructor
    public static class IssueResponseDto {
        private Long issuePk;
        private Long pjPk;
        private Long teamPk;
        private String issueTitle;
        private String issueDescription;
        private Long issueAuthorPk;
        private String issueStatus;
        private String issueLabel;
        private Long todoPk;
        private Boolean issueOpened;
        private Long issueAssigneePk;
        private String fileLink;
        private Long issueId;

        @Builder
        public IssueResponseDto(Long issuePk, Long pjPk, Long teamPk, Long todoPk, String issueTitle, String issueDescription, Long issueAuthorPk, String issueStatus, String issueLabel, Boolean issueOpened, Long issueAssigneePk, String fileLink, Long issueId) {
            this.issuePk = issuePk;
            this.pjPk = pjPk;
            this.teamPk = teamPk;
            this.issueTitle = issueTitle;
            this.issueDescription = issueDescription;
            this.issueAuthorPk = issueAuthorPk;
            this.issueStatus = issueStatus;
            this.issueLabel = issueLabel;
            this.todoPk = todoPk;
            this.issueOpened = issueOpened;
            this.issueAssigneePk = issueAssigneePk;
            this.fileLink = fileLink;
            this.issueId = issueId;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class ChangeStatusOrLabelDto{
        private String value;

        @Builder
        public ChangeStatusOrLabelDto(String value){
            this.value = value;
        }
    }
    @Getter
    @NoArgsConstructor
    public static class IssueKeyDto{
        Long issuePk;
        Long pjPk;
        Long teamPk;

        @Builder
        public IssueKeyDto(Long issuePk, Long pjPk, Long teamPk){
            this.issuePk = issuePk;
            this.pjPk = pjPk;
            this.teamPk = teamPk;
        }
    }
}

