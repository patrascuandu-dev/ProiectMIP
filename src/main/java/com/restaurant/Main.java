import com.restaurant.service.MeniuService;
import com.restaurant.model.Produs;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MeniuService service = new MeniuService();
        List<Produs> produse = service.initializareMeniu();
        service.afiseazaMeniu(produse);
    }
}
