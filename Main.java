public class Main {
    public static void main(String[] args) {
        ChatRoom general = new ChatRoom("General");
        general.addBadword("bodoh");

        User budi = new User("Budi");
        User andi = new User("Andi");
        User cici = new User("Cici");

        general.registerUser(budi);
        general.registerUser(andi);
        general.registerUser(cici);

        budi.sendToRoom("General", "Halo semuanya!");
        andi.sendToRoom("General", "Jangan ngomong bodoh ya");

        general.muteUser(andi);
        andi.sendToRoom("General", "Pesan ini tidak akan terkirim");
        andi.receiveMessage(budi, "Andi tetap bisa baca pesan saat di-mute");

        general.unmuteUser(andi);
        andi.sendToRoom("General", "Sudah di-unmute nih");
        general.showHistory();
    }
}