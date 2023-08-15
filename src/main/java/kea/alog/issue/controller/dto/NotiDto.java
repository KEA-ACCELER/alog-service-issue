package kea.alog.issue.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotiDto {
    @Getter
    @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
    public static class Message {
        private Long UserPk;
        private String MsgContent;

        @Builder
        public Message(Long UserPk, String MsgContent) {
            this.UserPk = UserPk;
            this.MsgContent = MsgContent;
        }
        
    }
}
