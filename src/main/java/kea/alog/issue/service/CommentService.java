package kea.alog.issue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.NotiDto;
import kea.alog.issue.controller.dto.CommentDto.*;
import kea.alog.issue.domain.comment.CommentRepository;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.domain.issue.IssueRepository;
import kea.alog.issue.domain.comment.Comment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    NotiFeign notiFeign;

    @Transactional
    public String deleteComment(Long commentId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        if (optComment.isPresent()) {
            commentRepository.delete(optComment.get());
            return commentId + " is deleted";
        }
        return "comment not found";
    }

    @Transactional
    public Long createComment(CommentCreateRequestDto reqDto) {
        Optional<Issue> optIssue = issueRepository.findById(reqDto.getIssuePk());
        if (!optIssue.isPresent()) {
            return null;
        }
        Comment commentData = Comment.builder()
                .issuePk(optIssue.get())
                .commentContent(reqDto.getCommentContent())
                .commentAuthorPk(reqDto.getCommentAuthorPk())
                .build();
        Comment comment = commentRepository.save(commentData);
        // 댓글 작성자가 이슈 담당자 본인인 경우에는 알림을 보내지 않는다/
        if (optIssue.get().getIssueAuthorPk() != reqDto.getCommentAuthorPk()) {
            // 알림 메시지가 8자가 넘으면, 8자까지만 보내고 뒤에 ... 을 붙인다, 3항 연산자
            notiFeign.createNoti(NotiDto.Message.builder()
                            .UserPk(optIssue.get().getIssueAuthorPk())
                            .MsgContent(optIssue.get().getIssueDescription().length() > 8 ? optIssue.get().getIssueDescription().substring(0, 8) + "... 에 댓글이 작성되었습니다." : optIssue.get().getIssueDescription() + " 에 댓글이 작성되었습니다.").build());
        }
        // 댓글 작성자가 이슈 assignee 본인인 경우에는 알림을 보내지 않는다/
        if (optIssue.get().getIssueAssigneePk() != reqDto.getCommentAuthorPk()) {
            // 알림 메시지가 8자가 넘으면, 8자까지만 보내고 뒤에 ... 을 붙인다
            notiFeign.createNoti(NotiDto.Message.builder()
                            .UserPk(optIssue.get().getIssueAssigneePk())
                            .MsgContent(optIssue.get().getIssueDescription().length() > 8 ? optIssue.get().getIssueDescription().substring(0, 8) + "... 에 댓글이 작성되었습니다." : optIssue.get().getIssueDescription() + " 에 댓글이 작성되었습니다.").build());
        }
        return comment.getCommentPk();
    }

    // 최신순으로 댓글 조회
    @Transactional
    public List<Comment> getCommentList(Long issuePk, int page, int size) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (!issue.isPresent()) {
            return null;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository.findAllByIssuePkOrderByCreatedDateDesc(issue.get(), pageable);
        if (!commentList.hasContent()) {
            return null;
        }
        return commentList.getContent();
    }

    @Transactional
    public List<Comment> getCommentListByAuthor(Long issuePk, Long authorPk, int page, int size) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if (!issue.isPresent()) {
            return null;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository
                .findAllByIssuePkAndCommentAuthorPkOrderByCreatedDateDesc(issue.get(), authorPk, pageable);
        if (!commentList.hasContent()) {
            return null;
        }
        return commentList.getContent();
    }

}