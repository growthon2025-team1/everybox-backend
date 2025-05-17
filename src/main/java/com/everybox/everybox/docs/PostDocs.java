package com.everybox.everybox.docs;

import com.everybox.everybox.dto.PostCreateRequestDto;
import com.everybox.everybox.dto.PostResponseDto;
import com.everybox.everybox.dto.PostUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
public interface PostDocs {

    @Operation(summary = "게시글 생성", description = "새 게시글을 등록합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 생성 성공",
            content = @Content(schema = @Schema(implementation = PostResponseDto.class)))
    @PostMapping
    ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostCreateRequestDto request,
            Authentication authentication
    );

    @Operation(summary = "게시글 전체 조회", description = "모든 게시글을 가져옵니다.")
    @GetMapping
    ResponseEntity<List<PostResponseDto>> getPosts();

    @Operation(summary = "게시글 상세 조회", description = "특정 게시글을 ID로 조회합니다.")
    @GetMapping("/{postId}")
    ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId);

    @Operation(summary = "게시글 삭제", description = "게시글을 ID 기준으로 삭제합니다.")
    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable Long postId, Authentication authentication);

    @Operation(summary = "게시글 수정", description = "기존 게시글을 수정합니다.")
    @PutMapping("/{postId}")
    ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request,
            Authentication authentication
    );
}