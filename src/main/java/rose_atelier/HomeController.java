package rose_atelier;

import java.time.LocalDateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import java.util.List;

@Controller
public class HomeController {

    private final RoseRepository roseRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    public HomeController(
            RoseRepository roseRepository,
            OrderHistoryRepository orderHistoryRepository
    ) {
        this.roseRepository = roseRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    private int colorOrder(String color) {
    return switch (color) {
        case "ピンク" -> 1;
        case "レッド" -> 2;
        case "イエロー" -> 3;
        case "ホワイト" -> 4;
        case "グリーン" -> 5;
        case "オレンジ" -> 6;
        case "その他" -> 7;
        default -> 99;
    };
}

  private List<CartItem> cart = new ArrayList<>();

private List<Rose> roses = new ArrayList<>(List.of(

            new Rose("ピンク", "rose-pink", "Aries", "アリエス", "Aries", 520, 0, true, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Avenir", "アヴニール", "Avenir", 470, 0, false, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Blossom Pink", "ブロッサムピンク", "Blossom Pink", 450, 0, false, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "haruka", "はるか", "haruka", 680, 0, false, true, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Jumilia", "ジュミリア", "Jumilia", 620, 0, true, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Remembrance", "リメンブランス", "Remembrance", 540, 0, true, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "riverdale", "リバーデール", "riverdale", 650, 0, false, true, "在庫あり"),
            new Rose("ピンク", "rose-pink", "SweetAvalanche", "スイートアバランチェ", "Sweet Avalanche", 700, 0, true, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Littlewoods", "リトルウッズ", "Littlewoods", 440, 0, false, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Lovelylydia", "ラブリーリディア", "Lovelylydia", 480, 0, false, false, "在庫あり"),
            new Rose("ピンク", "rose-pink", "Strawberrywaltz", "ストロベリーワルツ", "Strawberrywaltz", 610, 0, true, false, "在庫あり"),

            new Rose("レッド", "rose-red", "amada", "アマダ", "amada", 590, 0, true, false, "在庫あり"),
            new Rose("レッド", "rose-red", "Brillante", "ブリランテ", "Brillante", 640, 0, true, false, "在庫あり"),
            new Rose("レッド", "rose-red", "RedElegance", "レッドエレガンス", "RedElegance", 560, 0, false, false, "在庫あり"),
            new Rose("レッド", "rose-red", "RedRanunculus", "レッドラナンキュラス", "RedRanunculus", 500, 0, false, false, "在庫あり"),
            new Rose("レッド", "rose-red", "samurai", "サムライ", "samurai", 690, 0, true, false, "在庫あり"),
            new Rose("レッド", "rose-red", "fanfare", "ファンファーレ", "fanfare", 460, 0, false, false, "在庫あり"),
            new Rose("レッド", "rose-red", "Ladylove", "レディラブ", "Ladylove", 530, 0, false, false, "在庫あり"),
            new Rose("レッド", "rose-red", "littlemarvel", "リトルマーベル", "littlemarvel", 430, 0, false, false, "在庫あり"),
            new Rose("レッド", "rose-red", "Tamango", "タマンゴ", "Tamango", 510, 0, false, false, "在庫あり"),

            new Rose("イエロー", "rose-yellow", "catalina", "カタリナ", "catalina", 490, 0, false, false, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "Goldrush", "ゴールドラッシュ", "Goldrush", 630, 0, true, false, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "Ilios", "イリオス", "Ilios", 610, 0, true, false, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "JoanofArc", "ジャンヌダルク", "Joan of Arc", 570, 0, false, true, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "Lumiere", "ルミエール", "Lumiere", 520, 0, false, false, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "molineux", "モリニュー", "molineux", 660, 0, false, true, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "Honeylemon", "ハニーレモン", "Honeylemon", 440, 0, false, false, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "Pisscup", "ピスカップ", "Pisscup", 410, 0, false, false, "在庫あり"),
            new Rose("イエロー", "rose-yellow", "Shootingstar", "シューティングスター", "Shootingstar", 680, 0, true, false, "在庫あり"),

            new Rose("ホワイト", "rose-white", "All4CuteWhite", "オールフォーキュートホワイト", "All4CuteWhite", 450, 0, false, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "avalanche", "アバランチェ", "avalanche", 700, 0, true, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "emablewhite", "エマーブルホワイト", "emablewhite", 480, 0, false, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "Tineke", "ティネケ", "Tineke", 540, 0, false, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "FairyKissWhite", "フェアリーキッスホワイト", "FairyKissWhite", 510, 0, false, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "spraywit", "スプレーウィット", "spraywit", 430, 0, false, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "Sweetold", "スイートオールド", "Sweetold", 470, 0, false, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "Vivian", "ビビアン", "Vivian", 620, 0, true, false, "在庫あり"),
            new Rose("ホワイト", "rose-white", "whitebambina", "ホワイトバンビーナ", "whitebambina", 640, 0, true, false, "在庫あり"),

            new Rose("グリーン", "rose-green", "lime", "ライム", "lime", 610, 0, true, false, "在庫あり"),
            new Rose("グリーン", "rose-green", "Minttea", "ミントティー", "Minttea", 650, 0, true, false, "在庫あり"),
            new Rose("グリーン", "rose-green", "supergreen", "スーパーグリーン", "supergreen", 520, 0, false, false, "在庫あり"),
            new Rose("グリーン", "rose-green", "concusare", "コンクサーレ", "concusare", 560, 0, true, false, "在庫あり"),
            new Rose("グリーン", "rose-green", "Eclair", "エクレール", "Eclair", 680, 0, true, false, "在庫あり"),
            new Rose("グリーン", "rose-green", "OrientalEclair", "オリエンタルエクレール", "OrientalEclair", 700, 0, true, false, "在庫あり"),
            new Rose("グリーン", "rose-green", "Ryuiso", "リュイソ", "Ryuiso", 490, 0, false, false, "在庫あり"),

            new Rose("オレンジ", "rose-orange", "apricotfoundation", "アプリコットファンデーション", "apricotfoundation", 550, 0, false, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "BabyRomantica", "ベビーロマンティカ", "BabyRomantica", 690, 0, true, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "caramelantique", "キャラメルアンティーク", "caramelantique", 670, 0, false, true, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "Carpidom", "カルピデューム", "Carpidom", 620, 0, true, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "Fossett", "フォセット", "Fossett", 480, 0, false, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "HeliosRomantica", "ヘリオスロマンティカ", "HeliosRomantica", 590, 0, false, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "OrangeRomantica", "オレンジロマンティカ", "OrangeRomantica", 600, 0, false, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "PeachAvalanche", "ピーチアバランチェ", "PeachAvalanche", 640, 0, false, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "Cindy", "シンディ", "Cindy", 610, 0, true, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "Fellini", "フェリーニ", "Fellini", 660, 0, true, false, "在庫あり"),
            new Rose("オレンジ", "rose-orange", "sunnyday", "サニーデイ", "sunnyday", 630, 0, true, false, "在庫あり"),

            new Rose("その他", "rose-other", "blackbaccarat", "ブラックバカラ", "blackbaccarat", 680, 0, false, false, "在庫あり"),
            new Rose("その他", "rose-other", "blacktea", "ブラックティー", "blacktea", 700, 0, false, true, "在庫あり"),
            new Rose("その他", "rose-other", "Bluemillefeuille", "ブルーミルフィーユ", "Bluemillefeuille", 650, 0, false, false, "在庫あり"),
            new Rose("その他", "rose-other", "cafelatte", "カフェラテ", "cafelatte", 560, 0, false, false, "在庫あり"),
            new Rose("その他", "rose-other", "Julia", "ジュリア", "Julia", 620, 0, false, false, "在庫あり"),
            new Rose("その他", "rose-other", "teddybear", "テディベア", "teddybear", 540, 0, false, false, "在庫あり"),
            new Rose("その他", "rose-other", "schnabel", "シュナーベル", "schnabel", 500, 0, false, false, "在庫あり"),
            new Rose("その他", "rose-other", "TenatureLemon", "テナチュールレモン", "TenatureLemon", 610, 0, true, false, "在庫あり"),
            new Rose("その他", "rose-other", "Lilacclassic", "ライラッククラシック", "Lilacclassic", 640, 0, true, false, "在庫あり"),
            new Rose("その他", "rose-other", "crazytoo", "クレイジートゥー", "crazytoo", 690, 0, false, true, "在庫あり")
       ));

@GetMapping("/admin")
public String index(Model model) {

    if (roseRepository.count() == 0) {
        roseRepository.saveAll(roses);
    }

    List<Rose> roses = roseRepository.findAll();

    roses.sort(Comparator.comparingInt(
            rose -> colorOrder(rose.getColor())
    ));

    model.addAttribute("roses", roses);
    model.addAttribute("total", roses.size());

    long longLastingCount = roses.stream()
            .filter(Rose::isLongLasting)
            .count();

    long fragrantCount = roses.stream()
            .filter(Rose::isFragrant)
            .count();

    model.addAttribute("longLastingCount", longLastingCount);
    model.addAttribute("fragrantCount", fragrantCount);

    return "index";
}
@GetMapping("/stock")
public String stock(Model model) {
    List<Rose> roses = roseRepository.findAll();

    roses.sort(Comparator.comparingInt(
            rose -> colorOrder(rose.getColor())
    ));

    model.addAttribute("roses", roses);
    return "stock";
}
@PostMapping("/stock/add")
public String addStock(
        @RequestParam String variety,
        @RequestParam int quantity
) {

    System.out.println("在庫追加POST");
    System.out.println(variety);
    System.out.println(quantity);

    List<Rose> roses = roseRepository.findAll();

    for (Rose rose : roses) {
        if (rose.getVariety().equals(variety)) {
            rose.addQuantity(quantity);
            roseRepository.save(rose);
            break;
        }
    }

    return "redirect:/admin";
}
@GetMapping("/add-flower")
public String addFlower() {
    System.out.println("品種登録ページを開きました");
    return "add-flower";
}

@PostMapping("/add-flower/add")
public String addFlowerSubmit(
    
        @RequestParam String color,
        @RequestParam String folder,
        @RequestParam String variety,
        @RequestParam String englishName,
        @RequestParam int price,
        @RequestParam(required = false, defaultValue = "false") boolean longLasting,
        @RequestParam(required = false, defaultValue = "false") boolean fragrant,
        @RequestParam(required = false) MultipartFile imageFile
) {

     System.out.println("POST受け取りました");
   try {

    String fileBaseName = "noimage";

    if (imageFile != null && !imageFile.isEmpty()) {

    String fileName = imageFile.getOriginalFilename();

    Path uploadPath = Paths.get(
            "src/main/resources/static/image/rose/" + folder
    );

    Files.createDirectories(uploadPath);

    String savedFileName = System.currentTimeMillis() + "_" + fileName;

    Path filePath = uploadPath.resolve(savedFileName);

    Files.copy(
            imageFile.getInputStream(),
            filePath
    );

    fileBaseName = savedFileName
        .replace(".jpg", "")
        .replace(".JPG", "")
        .replace(".jpeg", "")
        .replace(".JPEG", "")
        .replace(".png", "")
        .replace(".PNG", "");
}
    System.out.println("登録します: " + variety);

    roseRepository.save(new Rose(
            color,
            folder,
            fileBaseName,
            variety,
            englishName,
            price,
            0,
            longLasting,
            fragrant,
            "売り切れ"
    ));

} catch (Exception e) {
    e.printStackTrace();
    return "redirect:/add-flower";
}

return "redirect:/admin";
}
@PostMapping("/flower/delete")
public String deleteFlower(
        @RequestParam Long id
) {

    roseRepository.deleteById(id);

    return "redirect:/";
}
@GetMapping("/order")
public String order(Model model) {
    List<Rose> roses = roseRepository.findAll();

    roses.sort(Comparator.comparingInt(
            rose -> colorOrder(rose.getColor())
    ));

    model.addAttribute("roses", roses);

    return "order";
}
@GetMapping("/welcome")
public String welcome() {
    return "welcome";
}

@GetMapping("/waste")
public String waste(Model model) {

    List<Rose> roses = roseRepository.findAll();

    roses.sort(Comparator.comparingInt(
            rose -> colorOrder(rose.getColor())
    ));

    model.addAttribute("roses", roses);

    return "waste";
}
@GetMapping("/cart")
public String cart(Model model) {

    model.addAttribute("cart", cart);

    int total = 0;

    for (CartItem item : cart) {
        total += item.getSubtotal();
    }

    model.addAttribute("total", total);

    return "cart";
}

@GetMapping("/checkout")
public String checkout(Model model) {

    model.addAttribute("cart", cart);

    int total = 0;

    for (CartItem item : cart) {
        total += item.getSubtotal();
    }

    int shipping = total >= 10000 ? 0 : 1000;

    model.addAttribute("subtotal", total);
    model.addAttribute("shipping", shipping);
    model.addAttribute("grandTotal", total + shipping);

    return "checkout";
}
@PostMapping("/cart/add")
public String addToCart(
        @RequestParam Long id,
        @RequestParam int quantity
) {

    Rose selectedRose = roseRepository.findById(id).orElse(null);

    if (selectedRose == null) {
        return "redirect:/order";
    }

    for (CartItem item : cart) {

        if (item.getRose().getId().equals(id)) {
            item.addQuantity(quantity);
            return "redirect:/cart";
        }
    }

    cart.add(new CartItem(selectedRose, quantity));

    return "redirect:/cart";
}
@PostMapping("/checkout/complete")
public String completeOrder() {

for (CartItem item : cart) {

    Rose rose = item.getRose();

    int newQuantity =
            rose.getQuantity() - item.getQuantity();

    if (newQuantity < 0) {
        newQuantity = 0;
    }

    rose.addQuantity(
            newQuantity - rose.getQuantity()
    );

    roseRepository.save(rose);

    orderHistoryRepository.save(

            new OrderHistory(

                    rose.getVariety(),

                    item.getQuantity(),

                    rose.getPrice(),

                    item.getSubtotal(),

                    LocalDateTime.now()
            )
    );
}

    cart.clear();

    return "redirect:/order";
}
@PostMapping("/waste/add")
public String addWaste(
        @RequestParam String variety,
        @RequestParam int quantity
) {

    List<Rose> roses = roseRepository.findAll();

    for (Rose rose : roses) {

        if (rose.getVariety().equals(variety)) {

            int newQuantity =
                    rose.getQuantity() - quantity;

            if (newQuantity < 0) {
                newQuantity = 0;
            }

            rose.addQuantity(
                    newQuantity - rose.getQuantity()
            );

            roseRepository.save(rose);

            break;
        }
    }

    return "redirect:/admin";
}
@GetMapping("/history")
public String history(Model model) {
    List<OrderHistory> histories =
            orderHistoryRepository.findAll();

    histories.sort((a, b) ->
            b.getOrderedAt().compareTo(a.getOrderedAt())
    );

    model.addAttribute("histories", histories);

    return "history";
}
@GetMapping("/history/csv")
public void downloadHistoryCsv(HttpServletResponse response) throws Exception {

    response.setContentType("text/csv; charset=UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=orders.csv");

    PrintWriter writer = response.getWriter();

    writer.println("variety,quantity,price,subtotal,orderedAt");

    List<OrderHistory> histories = orderHistoryRepository.findAll();

    for (OrderHistory history : histories) {
        writer.println(
                history.getVariety() + "," +
                history.getQuantity() + "," +
                history.getPrice() + "," +
                history.getSubtotal() + "," +
                history.getOrderedAt()
        );
    }

    writer.flush();
}

@GetMapping("/reset-stock")
public String resetStock() {

    List<Rose> roses = roseRepository.findAll();

    for (Rose rose : roses) {
        rose.setQuantity(10);
    }

    roseRepository.saveAll(roses);

    return "redirect:/admin";
}

}

