public class Main {
    public static void main(String[] args) {
        ChatRoom general = new ChatRoom("General");
        ChatRoom sports = new ChatRoom("Sports");

        User budi = new User("Budi");
        User andi = new User("Andi");
        User cici = new User("Cici");

        general.registerUser(budi);
        general.registerUser(andi);
        general.registerUser(cici);

        sports.registerUser(andi);
        sports.registerUser(budi);

        budi.sendToRoom("General", "Halo warga General!");
        andi.sendToRoom("Sports", "Siapa yang main futsal nanti sore?");

        general.removeUser(cici);
        budi.sendToRoom("General", "Cici sudah keluar ya.");
    }
}