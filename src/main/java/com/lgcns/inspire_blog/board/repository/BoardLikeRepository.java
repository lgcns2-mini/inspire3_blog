package com.lgcns.inspire_blog.board.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire_blog.board.domain.entity.BoardEntity;
import com.lgcns.inspire_blog.board.domain.entity.BoardLike;
import com.lgcns.inspire_blog.user.domain.entity.UserEntity;

public interface BoardLikeRepository extends JpaRepository<BoardLike, UUID> {
    boolean existsByBoardAndUser(BoardEntity board, UserEntity user);
    Optional<BoardLike> findByBoardAndUser(BoardEntity board, UserEntity user);
    List<BoardLike> findAllByUser(UserEntity user);
}
