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
import kea.alog.issue.controller.dto.CommentDto.*;
import kea.alog.issue.domain.comment.CommentRepository;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.domain.issue.IssueRepository;
import kea.alog.issue.domain.comment.Comment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    IssueRepository issueRepository;

    @Transactional
    public String deleteComment(Long commentId){
        Optional<Comment> optComment = commentRepository.findById(commentId);
        if(optComment.isPresent()){
            commentRepository.delete(optComment.get());
            return commentId + " is deleted";
        }
        return "comment not found";
    }

    @Transactional
    public Long createComment(CommentCreateRequestDto reqDto){
        Optional<Issue> optIssue = issueRepository.findById(reqDto.getIssuePk());
        if(!optIssue.isPresent()){
            Comment commentData = Comment.builder()
                    .issuePk(optIssue.get())
                    .commentContent(reqDto.getCommentContent())
                    .commentAuthorPk(reqDto.getCommentAuthorPk())
                    .build();
            commentRepository.save(commentData);
            return commentData.getCommentPk();
        }
        return null;
    }

    //최신순으로 댓글 조회 
    @Transactional
    public List<Comment> getCommentList(Long issuePk, int page, int size){
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if(!issue.isPresent()){
            return null;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository.findAllByIssuePkOrderByCreatedDateDesc(issue.get(), pageable);
        if(!commentList.hasContent()){
            return null;
        }
        return commentList.getContent();
    }

    @Transactional
	public List<Comment> getCommentListByAuthor(Long issuePk, Long authorPk, int page, int size) {
        Optional<Issue> issue = issueRepository.findById(issuePk);
        if(!issue.isPresent()){
            return null;
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository.findAllByIssuePkAndCommentAuthorPkOrderByCreatedDateDesc(issue.get(), authorPk, pageable);
        if(!commentList.hasContent()){
            return null;
        }
        return commentList.getContent();
	}

}