package Observer.AuctionSystem;

public class Main {
    public static void main(String[] args) {
        IPublisher vase = new AuctionItem("Antique Vase", 50000);
        IPublisher bat = new AuctionItem("M.S Dhoni 2011 WC Bat", 100000);
        IPublisher ball = new AuctionItem("Kumble 10 Wickets ball", 10000);

        ISubscriber sankalp = new Person("Sankalp");
        ISubscriber janvi = new Person("Janvi");
        ISubscriber udit = new Person("Udit");

        bat.subscribe(sankalp);
        bat.subscribe(udit);

        ball.subscribe(sankalp);
        ball.subscribe(janvi);

        bat.placeBid(sankalp, 200000);
        bat.placeBid(udit, 100000);
        ball.placeBid(janvi, 10000);

        vase.placeBid(janvi, 51000);
    }
}
