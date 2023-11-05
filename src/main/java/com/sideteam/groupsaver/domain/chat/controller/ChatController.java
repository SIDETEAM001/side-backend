package com.sideteam.groupsaver.domain.chat.controller;

import com.sideteam.groupsaver.domain.chat.domain.ChatRoom;
import com.sideteam.groupsaver.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chatList")
    public String chatList(Model model){
        List<ChatRoom> roomList = chatService.findAllRoom();
        model.addAttribute("roomList",roomList);
        return "chatList";
    }

    @PostMapping("/createRoom")
    public String createRoom(Model model, @RequestParam String name, String username) {
        ChatRoom room = chatService.createRoom(name);
        model.addAttribute("room", room);
        model.addAttribute("username", username);
        return "chatRoom";
    }

    @GetMapping("/chatRoom")
    public String chatRoom(Model model, @RequestParam String roomId){
        ChatRoom room = chatService.findRoomById(roomId);
        model.addAttribute("room", room);
        return "chatRoom";
    }
}