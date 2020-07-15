import dao.impl.CollectionPlayerDao;
import domain.Player;
import service.impl.PlayerService;

public class Main {
    public static void main(String[] args) {
        var dao = new CollectionPlayerDao();
        var playerService = new PlayerService(dao);

        dao.printList();

        Player laci = new Player("laci");
        Player joci = new Player("joci");

        playerService.addOrUpdate(laci);
        playerService.addOrUpdate(new Player("feri"));
        playerService.addOrUpdate(new Player("józsi"));
        playerService.delete(laci);
        playerService.delete(joci);
        System.out.println(playerService.getByName("feri").get().getName());

        dao.printList();

    }

}
