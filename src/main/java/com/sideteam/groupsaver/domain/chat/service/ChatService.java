package com.sideteam.groupsaver.domain.chat.service;

import com.sideteam.groupsaver.domain.chat.domain.ClubChatRoom;
import com.sideteam.groupsaver.domain.chat.repository.ClubChatRoomRepository;
import com.sideteam.groupsaver.domain.club.domain.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private ClubChatRoomRepository roomRepository;

    public ClubChatRoom createClubChatRoom(Club club) {
        return ClubChatRoom.of(club.getName(), club.getMainImage(), club);
    }
}
