package com.everybox.everybox.docs;

        import com.everybox.everybox.dto.ChatRoomRequestDto;
        import com.everybox.everybox.dto.ChatRoomResponseDto;
        import com.everybox.everybox.dto.MessageDto;
        import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.Parameter;
        import io.swagger.v3.oas.annotations.media.Content;
        import io.swagger.v3.oas.annotations.media.Schema;
        import io.swagger.v3.oas.annotations.responses.ApiResponse;
        import org.springframework.http.ResponseEntity;
        import org.springframework.security.core.Authentication;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestBody;

        import java.util.List;

public interface ChatDocs {

    @Operation(
            summary = "채팅방 생성",
            description = "특정 게시글과 수신자 ID를 기반으로 채팅방을 생성하거나 기존 채팅방을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 생성 또는 반환 성공", content = @Content(schema = @Schema(implementation = ChatRoomResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    ResponseEntity<ChatRoomResponseDto> createChatRoom(
            @RequestBody ChatRoomRequestDto req,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary = "채팅방 목록 조회",
            description = "현재 로그인한 사용자의 채팅방 목록을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "채팅방 목록 반환", content = @Content(schema = @Schema(implementation = ChatRoomResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "인증 실패")
            }
    )
    ResponseEntity<List<ChatRoomResponseDto>> getChatRooms(
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary = "채팅방 메시지 조회",
            description = "지정된 채팅방의 메시지 목록을 반환합니다. 사용자 인증이 필요합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메시지 목록 반환", content = @Content(schema = @Schema(implementation = MessageDto.class))),
                    @ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음"),
                    @ApiResponse(responseCode = "401", description = "인증 실패")
            }
    )
    ResponseEntity<List<MessageDto>> getMessages(
            @PathVariable Long chatRoomId,
            @Parameter(hidden = true) Authentication authentication
    );
}