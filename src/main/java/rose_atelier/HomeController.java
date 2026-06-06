package rose_atelier;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

    private final RoseRepository roseRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    private final List<CartItem> cart = new ArrayList<>();

    public HomeController(
            RoseRepository roseRepository,
            OrderHistoryRepository orderHistoryRepository
    ) {
        this.roseRepository = roseRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/admin")
    public String index(Model model) {
        List<Rose> roses = getSortedRoses();

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
        model.addAttribute("roses", getSortedRoses());
        return "stock";
    }

    @PostMapping("/stock/add")
    public String addStock(
            @RequestParam Long id,
            @RequestParam int quantity
    ) {
        Rose rose = roseRepository.findById(id).orElse(null);

        if (rose == null || quantity <= 0) {
            return "redirect:/stock";
        }

        rose.addQuantity(quantity);
        roseRepository.save(rose);

        return "redirect:/stock?success";
    }

    @GetMapping("/add-flower")
    public String addFlower() {
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
        try {
            String fileBaseName = "noimage";

            if (imageFile != null && !imageFile.isEmpty()) {
                String originalFileName = imageFile.getOriginalFilename();

                if (originalFileName != null && !originalFileName.isBlank()) {
                    Path uploadPath = Paths.get(
                            "src/main/resources/static/image/rose/" + folder
                    );

                    Files.createDirectories(uploadPath);

                    String savedFileName =
                            System.currentTimeMillis() + "_" + originalFileName;

                    Path filePath = uploadPath.resolve(savedFileName);

                    Files.copy(
                            imageFile.getInputStream(),
                            filePath,
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    fileBaseName = removeExtension(savedFileName);
                }
            }

            Rose rose = new Rose(
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
            );

            roseRepository.save(rose);

            return "redirect:/admin";

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/add-flower";
        }
    }

    @PostMapping("/flower/delete")
    public String deleteFlower(@RequestParam Long id) {
        roseRepository.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/order")
public String order(Model model) {
    model.addAttribute("roses", getSortedRoses());
    model.addAttribute("cartCount", cart.size());

    return "order";
}

    @PostMapping("/cart/add")
    public String addToCart(
            @RequestParam Long id,
            @RequestParam int quantity
    ) {
        Rose selectedRose = roseRepository.findById(id).orElse(null);

        if (selectedRose == null || quantity <= 0) {
            return "redirect:/order";
        }

        int currentCartQuantity = cart.stream()
                .filter(item -> item.getRose().getId().equals(id))
                .mapToInt(CartItem::getQuantity)
                .sum();

        if (selectedRose.getQuantity() < currentCartQuantity + quantity) {
            return "redirect:/order?error=stock";
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

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("total", calculateCartTotal());

        return "cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        int subtotal = calculateCartTotal();
        int shipping = subtotal >= 10000 ? 0 : 1000;

        model.addAttribute("cart", cart);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("grandTotal", subtotal + shipping);

        return "checkout";
    }

    @PostMapping("/checkout/complete")
    public String completeOrder() {
        for (CartItem item : cart) {
            Rose rose = roseRepository.findById(item.getRose().getId()).orElse(null);

            if (rose == null) {
                return "redirect:/cart";
            }

            if (rose.getQuantity() < item.getQuantity()) {
                return "redirect:/cart?error=stock";
            }
        }

        for (CartItem item : cart) {
            Rose rose = roseRepository.findById(item.getRose().getId()).orElseThrow();

            rose.addQuantity(-item.getQuantity());
            roseRepository.save(rose);

            OrderHistory history = new OrderHistory(
                    rose.getVariety(),
                    item.getQuantity(),
                    rose.getPrice(),
                    item.getSubtotal(),
                    LocalDateTime.now()
            );

            orderHistoryRepository.save(history);
        }

        cart.clear();

        return "redirect:/order";
    }

    @GetMapping("/waste")
    public String waste(Model model) {
        model.addAttribute("roses", getSortedRoses());
        return "waste";
    }

    @PostMapping("/waste/add")
    public String addWaste(
            @RequestParam Long id,
            @RequestParam int quantity
    ) {
        Rose rose = roseRepository.findById(id).orElse(null);

        if (rose == null || quantity <= 0) {
            return "redirect:/waste";
        }

        int wasteQuantity = Math.min(quantity, rose.getQuantity());

        rose.addQuantity(-wasteQuantity);
        roseRepository.save(rose);

        return "redirect:/admin";
    }

    @GetMapping("/analytics")
    public String analytics(Model model) {
        List<OrderHistory> histories = orderHistoryRepository.findAll();

        int totalSales = histories.stream()
                .mapToInt(OrderHistory::getSubtotal)
                .sum();

        int totalQuantity = histories.stream()
                .mapToInt(OrderHistory::getQuantity)
                .sum();

        Map<String, Integer> ranking = histories.stream()
                .collect(Collectors.groupingBy(
                        OrderHistory::getVariety,
                        Collectors.summingInt(OrderHistory::getQuantity)
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        model.addAttribute("histories", histories);
        model.addAttribute("totalOrders", histories.size());
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("ranking", ranking);

        return "analytics";
    }

    @GetMapping("/history")
    public String history(Model model) {
        List<OrderHistory> histories = orderHistoryRepository.findAll();

        histories.sort((a, b) ->
                b.getOrderedAt().compareTo(a.getOrderedAt())
        );

        model.addAttribute("histories", histories);

        return "history";
    }

    @GetMapping("/history/csv")
    public void downloadHistoryCsv(HttpServletResponse response) throws Exception {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader(
                "Content-Disposition",
                "attachment; filename=orders.csv"
        );

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

    @GetMapping("/order.html")
    public String oldOrderHtmlRedirect() {
        return "redirect:/order";
    }

    private List<Rose> getSortedRoses() {
        List<Rose> roses = roseRepository.findAll();

        roses.sort(Comparator.comparingInt(
                rose -> colorOrder(rose.getColor())
        ));

        return roses;
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

    private int calculateCartTotal() {
        int total = 0;

        for (CartItem item : cart) {
            total += item.getSubtotal();
        }

        return total;
    }

    private String removeExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex == -1) {
            return fileName;
        }

        return fileName.substring(0, dotIndex);
    }
}