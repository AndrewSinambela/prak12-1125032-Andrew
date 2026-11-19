import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String roomName;
    private List<User> members;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        this.members = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public void registerUser(User user) {
        if (!members.contains(user)) {
            members.add(user);
            user.setChatRoom(this);
        }
    }

    public void sendBroadcast(User sender, String message) {
        System.out.println("[ChatRoom " + roomName + "] (Broadcast dari " + sender.getName() + "): " + message);
        for (User member : members) {
            if (!member.equals(sender)) {
                member.receiveMessage(sender, message);
            }
        }
    }

    public void sendPrivateMessage(User sender, String recipientName, String message) {
        for (User member : members) {
            if (member.getName().equalsIgnoreCase(recipientName)) {
                System.out.println("[ChatRoom " + roomName + "] (Private " + sender.getName() + " -> " + member.getName() + "): " + message);
                member.receivePrivateMessage(sender, message);
                return;
            }
        }
        System.out.println("[ChatRoom " + roomName + "] Pengguna '" + recipientName + "' tidak ditemukan.");
    }
}