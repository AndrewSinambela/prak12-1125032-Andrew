import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatRoom {
    private String roomName;
    private List<User> members;
    private List<String> badwords;
    private User admin;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        this.members = new ArrayList<>();
        this.badwords = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public User getAdmin() {
        return admin;
    }

    public void addBadword(String word) {
        if (word != null && !word.isBlank()) {
            badwords.add(word.toLowerCase());
        }
    }

    public String filterMessage(String message) {
        String filtered = message;
        for (String badword : badwords) {
            filtered = filtered.replaceAll("(?i)\\b" + Pattern.quote(badword) + "\\b", "***");
        }
        return filtered;
    }

    public void registerUser(User user) {
        if (!members.contains(user)) {
            if (members.isEmpty()) {
                admin = user;
            }
            members.add(user);
            user.setChatRoom(this);
        }
    }

    public void removeUser(User user) {
        removeUser(admin, user);
    }

    public void removeUser(User requester, User targetUser) {
        if (requester == null || !requester.equals(admin)) {
            System.out.println("[ChatRoom " + roomName + "] Hanya admin yang dapat mengeluarkan anggota.");
            return;
        }
        if (targetUser.equals(admin)) {
            System.out.println("[ChatRoom " + roomName + "] Admin tidak dapat mengeluarkan dirinya sendiri.");
            return;
        }
        if (members.remove(targetUser)) {
            targetUser.leaveChatRoom(this);
            System.out.println("[ChatRoom " + roomName + "] " + targetUser.getName() + " telah dikeluarkan oleh admin " + admin.getName() + ".");
        }
    }

    public void sendBroadcast(User sender, String message) {
        if (!members.contains(sender)) {
            System.out.println("[ChatRoom " + roomName + "] " + sender.getName() + " bukan anggota room ini.");
            return;
        }
        String cleanMessage = filterMessage(message);
        System.out.println("[ChatRoom " + roomName + "] (Broadcast dari " + sender.getName() + "): " + cleanMessage);
        for (User member : members) {
            if (!member.equals(sender)) {
                member.receiveMessage(sender, cleanMessage);
            }
        }
    }

    public void sendPrivateMessage(User sender, String recipientName, String message) {
        if (!members.contains(sender)) {
            System.out.println("[ChatRoom " + roomName + "] " + sender.getName() + " bukan anggota room ini.");
            return;
        }
        String cleanMessage = filterMessage(message);
        for (User member : members) {
            if (member.getName().equalsIgnoreCase(recipientName)) {
                System.out.println("[ChatRoom " + roomName + "] (Private " + sender.getName() + " -> " + member.getName() + "): " + cleanMessage);
                member.receivePrivateMessage(sender, cleanMessage);
                return;
            }
        }
        System.out.println("[ChatRoom " + roomName + "] Pengguna '" + recipientName + "' tidak ditemukan.");
    }
}