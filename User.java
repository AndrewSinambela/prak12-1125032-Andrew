import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<ChatRoom> chatRooms;

    public User(String name) {
        this.name = name;
        this.chatRooms = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        if (chatRoom != null && !chatRooms.contains(chatRoom)) {
            chatRooms.add(chatRoom);
        }
    }

    public void leaveChatRoom(ChatRoom chatRoom) {
        chatRooms.remove(chatRoom);
    }

    public void sendToRoom(String roomName, String message) {
        for (ChatRoom room : chatRooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                System.out.println("[" + this.name + " @ " + room.getRoomName() + "]: Mengirim pesan grup...");
                room.sendBroadcast(this, message);
                return;
            }
        }
        System.out.println("[" + this.name + "] Tidak terdaftar di room '" + roomName + "'.");
    }

    public void send(String message) {
        if (!chatRooms.isEmpty()) {
            sendToRoom(chatRooms.get(0).getRoomName(), message);
        }
    }

    public void sendPrivateToRoom(String roomName, String recipientName, String message) {
        for (ChatRoom room : chatRooms) {
            if (room.getRoomName().equalsIgnoreCase(roomName)) {
                System.out.println("[" + this.name + " @ " + room.getRoomName() + "]: Mengirim pesan privat ke " + recipientName + "...");
                room.sendPrivateMessage(this, recipientName, message);
                return;
            }
        }
        System.out.println("[" + this.name + "] Tidak terdaftar di room '" + roomName + "'.");
    }

    public void sendPrivate(String recipientName, String message) {
        if (!chatRooms.isEmpty()) {
            sendPrivateToRoom(chatRooms.get(0).getRoomName(), recipientName, message);
        }
    }

    public void receiveMessage(User sender, String message) {
        System.out.println("[" + this.name + "] Menerima pesan dari " + sender.getName() + ": " + message);
    }

    public void receivePrivateMessage(User sender, String message) {
        System.out.println("[" + this.name + "] Menerima pesan privat dari " + sender.getName() + ": " + message);
    }
}