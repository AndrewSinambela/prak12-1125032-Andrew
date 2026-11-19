public class Main {
    public static void main(String[] args) {
        ChatRoom general = new ChatRoom("General");
        general.addBadword("bodoh");
        general.addBadword("kasar");

        User budi = new User("Budi");
        User andi = new User("Andi");
        User cici = new User("Cici");

        general.registerUser(budi);
        general.registerUser(andi);
        general.registerUser(cici);

        budi.send("Halo semuanya!");
        andi.send("Jangan ngomong bodoh ya");
        budi.sendPrivate("Cici", "Pesan rahasia yang agak kasar");
    }
}