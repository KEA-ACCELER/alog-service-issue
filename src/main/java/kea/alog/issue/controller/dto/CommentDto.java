package kea.alog.issue.controller.dto;

import lombok.*;

public class CommentDto {
    @Getter
    @NoArgsConstructor
    public static class CommentCreateOrUpdateDto {
        private Long pjPk;
        private Long teamPk;
        private Long issuePk;
        private String commentContent;
        private Long commentAuthorPk;

        @Builder
        public CommentCreateOrUpdateDto(Long pjPk, Long teamPk, Long issuePk, String commentContent, Long commentAuthorPk){
            this.pjPk = pjPk;
            this.teamPk = teamPk;
            this.issuePk = issuePk;
            this.commentContent = commentContent;
            this.commentAuthorPk = commentAuthorPk;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class CommentDataDto {
        private Long commentPk;
        private Long pjPk;
        private Long teamPk;
        private Long issuePk;
        private String commentContent;
        private Long commentAuthorPk;

        @Builder
        public CommentDataDto(Long commentPk, Long pjPk, Long teamPk, Long issuePk, String commentContent, Long commentAuthorPk){
            this.commentPk = commentPk;
            this.pjPk = pjPk;
            this.teamPk = teamPk;
            this.issuePk = issuePk;
            this.commentContent = commentContent;
            this.commentAuthorPk = commentAuthorPk;
        }
    }
}
