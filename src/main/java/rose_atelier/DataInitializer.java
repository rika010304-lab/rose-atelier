package rose_atelier;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoseRepository roseRepository;

    public DataInitializer(RoseRepository roseRepository) {
        this.roseRepository = roseRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("=== DataInitializer started ===");

        roseRepository.deleteAll();

        List<Rose> initialRoses = List.of(
                new Rose("ピンク", "rose-pink", "haruka", "はるか", "haruka", 680, 10, false, true, "在庫あり"),
                new Rose("ホワイト", "rose-white", "avalanche", "アバランチェ", "avalanche", 700, 10, true, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "supergreen", "スーパーグリーン", "supergreen", 520, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "Julia", "ジュリア", "Julia", 620, 10, false, false, "在庫あり")
        );

        roseRepository.saveAll(initialRoses);

        System.out.println("=== Rose count: " + roseRepository.count() + " ===");
    }
}