package com.lgcns.inspire_blog.user.ctrl;

import com.lgcns.inspire_blog.user.domain.dto.LoginRequestDTO;
import com.lgcns.inspire_blog.user.domain.dto.LoginResponseDTO;
import com.lgcns.inspire_blog.user.domain.dto.UserRequestDTO;
import com.lgcns.inspire_blog.user.domain.dto.UserResponseDTO;
import com.lgcns.inspire_blog.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.signup(request));
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signin(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(userService.signin(request));
    }

    // 회원 정보 수정
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserResponseDTO> updateUserInfo(@PathVariable Long userId, @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.updateUserInfo(userId, request));
    }
}