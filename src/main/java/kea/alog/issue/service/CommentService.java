package kea.alog.issue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kea.alog.issue.controller.dto.CommentDto.CommentDataDto;
import kea.alog.issue.domain.comment.CommentRepository;
import kea.alog.issue.domain.issue.Issue;
import kea.alog.issue.domain.issue.IssueRepository;
import kea.alog.issue.domain.comment.Comment;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService{
    final private CommentRepository commentRepository;
    final private IssueRepository issueRepository;

    /**
     * 단일 comment
     * @param commentId
     * @return
     */
    @Transactional
    public CommentDataDto getComment(Long commentId) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        if(optComment.isPresent()){
            Comment comment = optComment.get();
            CommentDataDto commentGetDto = CommentDataDto.builder()
                    .commentPk(comment.getCommentPk())
                    .pjPk(comment.getPjPk())
                    .teamPk(comment.getTeamPk())
                    .issuePk(comment.getIssuePk().getIssuePk())
                    .commentContent(comment.getCommentContent())
                    .commentAuthorPk(comment.getCommentAuthorPk())
                    .build();
            return commentGetDto;
        } else return CommentDataDto.builder().commentPk(0L).build();
    }

    /**
     * Issue에 관련된 comment들을 전부 불러오기
     * @param issueId
     * @param currentSize
     * @return
     */
    @Transactional
    public List<CommentDataDto> getCommentList(Long issueId, Long currentSize){
        Optional<Issue> optIssue = issueRepository.findById(issueId);
        List<CommentDataDto> rspList = new ArrayList<>();
        if(optIssue.isPresent()){
            int pageSize = 10;
            Pageable pageable = PageRequest.of(currentSize.intValue()-1, pageSize, Sort.by("commentPk").ascending());

            Page<Comment> commentList = commentRepository.findAllByIssuePk(optIssue.get(), pageable);
            
            for(Comment idx : commentList.getContent()){
                CommentDataDto addComment = CommentDataDto.builder()
                        .commentPk(idx.getCommentPk())
                        .pjPk(idx.getPjPk())
                        .teamPk(idx.getTeamPk())
                        .issuePk(idx.getIssuePk().getIssuePk())
                        .commentContent(idx.getCommentContent())
                        .commentAuthorPk(idx.getCommentAuthorPk())
                        .build();
                rspList.add(addComment);
            }
            return rspList;
        } else return rspList;
    }
    /**
     * 지우는 역할
     * @param commentId
     * @return
     */
    @Transactional
    public boolean deleteComment(Long commentId){
        Optional<Comment> optComment = commentRepository.findById(commentId);
        if(optComment.isPresent()){
            commentRepository.delete(optComment.get());
            return true;
        } else return false;
    }
    /**
     * 업데이트 코멘트
     * @param reqData
     * @return
     */
    @Transactional
    public Long updateComment(CommentDataDto reqData){
        Optional<Comment> optComment =  commentRepository.findById(reqData.getCommentPk());
        if(optComment.isPresent()){
            Comment comment = optComment.get();
            if(chkData(comment, reqData)){
                Comment updateContent = comment.toBuilder()
                    .commentContent(reqData.getCommentContent())
                    .build();
                commentRepository.save(updateContent);
                return updateContent.getCommentPk();
            } else {
                return 0L;
            }
        } else return 0L;
    }

    public boolean chkData(Comment comment, CommentDataDto reqDto){
        return comment.getIssuePk().getIssuePk() == reqDto.getIssuePk() && comment.getPjPk() == reqDto.getPjPk() && comment.getTeamPk() == reqDto.getTeamPk() && comment.getCommentAuthorPk() == reqDto.getCommentAuthorPk();
    }
}