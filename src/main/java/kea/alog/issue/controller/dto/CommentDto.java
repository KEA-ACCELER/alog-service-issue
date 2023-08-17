package kea.alog.issue.controller.dto;

import lombok.*;

public class CommentDto {
    @Getter
    @NoArgsConstructor
    public static class CommentCreateRequestDto {
        private Long issuePk;
        private String commentContent;
        private Long commentAuthorPk;

        @Builder
        public CommentCreateRequestDto(Long issuePk, String commentContent, Long commentAuthorPk){
            this.issuePk = issuePk;
            this.commentContent = commentContent;
            this.commentAuthorPk = commentAuthorPk;
        }
    }

}
