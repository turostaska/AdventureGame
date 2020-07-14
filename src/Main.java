import dao.impl.CollectionPlayerDao;
import domain.Player;
import service.impl.PlayerService;

public class Main {
    public static void main(String[] args) {
        var dao = new CollectionPlayerDao();
        var playerService = new PlayerService(dao);

        dao.printList();

        Player laci = new Player("laci");

        playerService.addOrUpdate(laci);
        playerService.addOrUpdate(new Player("feri"));
        playerService.addOrUpdate(new Player("józsi"));
        playerService.delete(laci);
        System.out.println(playerService.getByName("feri").get().getName());

        dao.printList();

    }

}
