package kea.alog.issue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import kea.alog.issue.controller.dto.IssueDto.IssueCreateRequestDto;
import kea.alog.issue.service.IssueService;


@ExtendWith(MockitoExtension.class)
public class IssueTest {

    @Mock
    private IssueService issueService;

    @InjectMocks
    private IssueCreateRequestDto issueCreateRequestDto;

    @BeforeEach
    public void setUp() {
        // IssueService에서 사용하는 NotiFeign 필드에 Mock 주입
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void createIssueTest() {
        // given
        IssueCreateRequestDto issueCreateRequestDto = IssueCreateRequestDto.builder().pjPk(1L).teamPk(1L).topicPk(1L)
                .issueAuthorPk(1L).issueContent("test")
                .issueStatus("TODO").issueLabel("NONE").issueAssigneePk(1L)
                .issueId("testId").startDate(null).endDate(null).build();
        when(issueService.createIssue(issueCreateRequestDto, "testLink")).thenReturn(1L);

        // when
        issueService.createIssue(issueCreateRequestDto, "testLink");

        // then
        verify(issueService, times(1)).createIssue(any(), any());


    }
}

