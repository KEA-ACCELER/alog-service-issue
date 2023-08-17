package kea.alog.issue.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kea.alog.issue.controller.dto.NotiDto;

@FeignClient(
    name = "notice-service",
    url = "${notice-service-url}"
)
public interface NotiFeign {
    
    @PostMapping("/api/noti")
    public String createNoti(@RequestBody NotiDto.Message message);
}
