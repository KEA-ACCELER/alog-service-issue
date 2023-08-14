package kea.alog.issue.controller.dto;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.enums.IssueLabel;
import kea.alog.issue.enums.IssueStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class IssueDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class IssueCreateRequestDto {
        private Long pjPk;
        private Long teamPk;
        private Long topicPk;
        private Long issueAuthorPk;
        private String issueContent;
        private String issueStatus;
        private String issueLabel;
        @Nullable
        private Long issueAssigneePk;
        private String issueId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public Issue toEntity(String fileLink){
            return Issue.builder()
                        .pjPk(pjPk)
                        .teamPk(teamPk)
                        .topicPk(topicPk)
                        .issueAuthorPk(issueAuthorPk)
                        .issueDescription(issueContent)
                        .issueStatus(Enum.valueOf(IssueStatus.class, issueStatus))
                        .issueLabel(Enum.valueOf(IssueLabel.class, issueLabel))
                        .issueAssigneePk(issueAssigneePk)
                        .fileLink(fileLink)
                        .issueId(issueId)
                        .issueOpened(true)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build();
        }

    }




}