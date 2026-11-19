import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatRoom {
    private String roomName;
    private List<User> members;
    private List<User> mutedUsers;
    private List<String> badwords;
    private List<String> chatHistory;
    private User admin;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        this.members = new ArrayList<>();
        this.mutedUsers = new ArrayList<>();
        this.badwords = new ArrayList<>();
        this.chatHistory = new ArrayList<>();
    }

    public String getRoomName() {
        return roomName;
    }

    public User getAdmin() {
        return admin;
    }

    public void muteUser(User user) {
        if (members.contains(user) && !mutedUsers.contains(user)) {
            mutedUsers.add(user);
            System.out.println("[ChatRoom " + roomName + "] " + user.getName() + " telah di-mute.");
        }
    }

    public void unmuteUser(User user) {
        if (mutedUsers.remove(user)) {
            System.out.println("[ChatRoom " + roomName + "] Mute untuk " + user.getName() + " telah dicabut.");
        }
    }

    public boolean isMuted(User user) {
        return mutedUsers.contains(user);
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
            mutedUsers.remove(targetUser);
            targetUser.leaveChatRoom(this);
            System.out.println("[ChatRoom " + roomName + "] " + targetUser.getName() + " telah dikeluarkan oleh admin " + admin.getName() + ".");
        }
    }

    public void sendBroadcast(User sender, String message) {
        if (!members.contains(sender)) {
            System.out.println("[ChatRoom " + roomName + "] " + sender.getName() + " bukan anggota room ini.");
            return;
        }
        if (isMuted(sender)) {
            System.out.println("[ChatRoom " + roomName + "] Pesan dari " + sender.getName() + " tidak disebarkan karena sedang di-mute.");
            return;
        }

        String cleanMessage = filterMessage(message);
        String logEntry = "[Broadcast dari " + sender.getName() + "]: " + cleanMessage;
        chatHistory.add(logEntry);

        System.out.println("[ChatRoom " + roomName + "] " + logEntry);
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
        if (isMuted(sender)) {
            System.out.println("[ChatRoom " + roomName + "] Pesan privat dari " + sender.getName() + " tidak dikirim karena sedang di-mute.");
            return;
        }

        String cleanMessage = filterMessage(message);
        for (User member : members) {
            if (member.getName().equalsIgnoreCase(recipientName)) {
                String logEntry = "[Private " + sender.getName() + " -> " + member.getName() + "]: " + cleanMessage;
                chatHistory.add(logEntry);

                System.out.println("[ChatRoom " + roomName + "] " + logEntry);
                member.receivePrivateMessage(sender, cleanMessage);
                return;
            }
        }
        System.out.println("[ChatRoom " + roomName + "] Pengguna '" + recipientName + "' tidak ditemukan.");
    }

    public void showHistory() {
        System.out.println("=== Riwayat Chat [" + roomName + "] ===");
        if (chatHistory.isEmpty()) {
            System.out.println("(Belum ada riwayat pesan)");
            return;
        }
        for (String entry : chatHistory) {
            System.out.println(entry);
        }
    }
}