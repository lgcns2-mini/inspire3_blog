package com.lgcns.inspire_blog.board.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgcns.inspire_blog.board.domain.entity.BoardHashtag;

public interface BoardHashtagRepository extends JpaRepository<BoardHashtag, UUID> {
    
}
