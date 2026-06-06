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
    roseRepository.deleteAll();

    List<Rose> initialRoses = List.of(

                new Rose("ピンク", "rose-pink", "Aries", "アリエス", "Aries", 520, 10, true, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Avenir", "アヴニール", "Avenir", 470, 10, false, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Blossom Pink", "ブロッサムピンク", "Blossom Pink", 450, 10, false, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "haruka", "はるか", "haruka", 680, 10, false, true, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Jumilia", "ジュミリア", "Jumilia", 620, 10, true, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Remembrance", "リメンブランス", "Remembrance", 540, 10, true, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "riverdale", "リバーデール", "riverdale", 650, 10, false, true, "在庫あり"),
                new Rose("ピンク", "rose-pink", "SweetAvalanche", "スイートアバランチェ", "Sweet Avalanche", 700, 10, true, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Littlewoods", "リトルウッズ", "Littlewoods", 440, 10, false, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Lovelylydia", "ラブリーリディア", "Lovely Lydia", 480, 10, false, false, "在庫あり"),
                new Rose("ピンク", "rose-pink", "Strawberrywaltz", "ストロベリーワルツ", "Strawberry Waltz", 610, 10, true, false, "在庫あり"),

                new Rose("レッド", "rose-red", "amada", "アマダ", "amada", 590, 10, true, false, "在庫あり"),
                new Rose("レッド", "rose-red", "Brillante", "ブリランテ", "Brillante", 640, 10, true, false, "在庫あり"),
                new Rose("レッド", "rose-red", "RedElegance", "レッドエレガンス", "Red Elegance", 560, 10, false, false, "在庫あり"),
                new Rose("レッド", "rose-red", "RedRanunculus", "レッドラナンキュラス", "Red Ranunculus", 500, 10, false, false, "在庫あり"),
                new Rose("レッド", "rose-red", "samurai", "サムライ", "samurai", 690, 10, true, false, "在庫あり"),
                new Rose("レッド", "rose-red", "fanfare", "ファンファーレ", "fanfare", 460, 10, false, false, "在庫あり"),
                new Rose("レッド", "rose-red", "Ladylove", "レディラブ", "Lady Love", 530, 10, false, false, "在庫あり"),
                new Rose("レッド", "rose-red", "littlemarvel", "リトルマーベル", "little marvel", 430, 10, false, false, "在庫あり"),
                new Rose("レッド", "rose-red", "Tamango", "タマンゴ", "Tamango", 510, 10, false, false, "在庫あり"),

                new Rose("イエロー", "rose-yellow", "catalina", "カタリナ", "catalina", 490, 10, false, false, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "Goldrush", "ゴールドラッシュ", "Goldrush", 630, 10, true, false, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "Ilios", "イリオス", "Ilios", 610, 10, true, false, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "JoanofArc", "ジャンヌダルク", "Joan of Arc", 570, 10, false, true, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "Lumiere", "ルミエール", "Lumiere", 520, 10, false, false, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "molineux", "モリニュー", "molineux", 660, 10, false, true, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "Honeylemon", "ハニーレモン", "Honey Lemon", 440, 10, false, false, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "Pisscup", "ピスカップ", "Pisscup", 410, 10, false, false, "在庫あり"),
                new Rose("イエロー", "rose-yellow", "Shootingstar", "シューティングスター", "Shooting Star", 680, 10, true, false, "在庫あり"),

                new Rose("ホワイト", "rose-white", "All4CuteWhite", "オールフォーキュートホワイト", "All4CuteWhite", 450, 10, false, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "avalanche", "アバランチェ", "avalanche", 700, 10, true, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "emablewhite", "エマーブルホワイト", "emable white", 480, 10, false, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "Tineke", "ティネケ", "Tineke", 540, 10, false, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "FairyKissWhite", "フェアリーキッスホワイト", "Fairy Kiss White", 510, 10, false, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "spraywit", "スプレーウィット", "spray wit", 430, 10, false, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "Sweetold", "スイートオールド", "Sweet old", 470, 10, false, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "Vivian", "ビビアン", "Vivian", 620, 10, true, false, "在庫あり"),
                new Rose("ホワイト", "rose-white", "whitebambina", "ホワイトバンビーナ", "white bambina", 640, 10, true, false, "在庫あり"),

                new Rose("グリーン", "rose-green", "lime", "ライム", "lime", 610, 10, true, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "Minttea", "ミントティー", "Mint tea", 650, 10, true, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "supergreen", "スーパーグリーン", "supergreen", 520, 10, false, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "concusare", "コンクサーレ", "concusare", 560, 10, true, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "Eclair", "エクレール", "Eclair", 680, 10, true, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "OrientalEclair", "オリエンタルエクレール", "Oriental Eclair", 700, 10, true, false, "在庫あり"),
                new Rose("グリーン", "rose-green", "Ryuiso", "リュイソ", "Ryuiso", 490, 10, false, false, "在庫あり"),

                new Rose("オレンジ", "rose-orange", "apricotfoundation", "アプリコットファンデーション", "apricot foundation", 550, 10, false, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "BabyRomantica", "ベビーロマンティカ", "Baby Romantica", 690, 10, true, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "caramelantique", "キャラメルアンティーク", "caramel antique", 670, 10, false, true, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "Carpidom", "カルピディーム", "Carpidom", 620, 10, true, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "Fossett", "フォセット", "Fossett", 480, 10, false, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "HeliosRomantica", "ヘリオスロマンティカ", "Helios Romantica", 590, 10, false, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "OrangeRomantica", "オレンジロマンティカ", "Orange Romantica", 600, 10, false, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "PeachAvalanche", "ピーチアバランチェ", "Peach Avalanche", 640, 10, false, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "Cindy", "シンディ", "Cindy", 610, 10, true, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "Fellini", "フェリーニ", "Fellini", 660, 10, true, false, "在庫あり"),
                new Rose("オレンジ", "rose-orange", "sunnyday", "サニーデイ", "sunny day", 630, 10, true, false, "在庫あり"),

                new Rose("その他", "rose-other", "blackbaccarat", "ブラックバカラ", "black baccarat", 680, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "blacktea", "ブラックティー", "black tea", 700, 10, false, true, "在庫あり"),
                new Rose("その他", "rose-other", "Bluemillefeuille", "ブルーミルフィーユ", "Blue millefeuille", 650, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "cafelatte", "カフェラテ", "cafe latte", 560, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "Julia", "ジュリア", "Julia", 620, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "teddybear", "テディベア", "teddy bear", 540, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "schnabel", "シュナーベル", "schnabel", 500, 10, false, false, "在庫あり"),
                new Rose("その他", "rose-other", "TenatureLemon", "テナチュールレモン", "Tenature Lemon", 610, 10, true, false, "在庫あり"),
                new Rose("その他", "rose-other", "Lilacclassic", "ライラッククラシック", "Lilac classic", 640, 10, true, false, "在庫あり"),
                new Rose("その他", "rose-other", "crazytoo", "クレイジートゥー", "crazy too", 690, 10, false, true, "在庫あり")
        );
    

        roseRepository.saveAll(initialRoses);
    }
}