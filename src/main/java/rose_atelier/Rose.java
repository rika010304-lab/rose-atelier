package rose_atelier;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private String folder;
    private String file;
    private String variety;
    private String englishName;
    private int price;
    private int quantity;
    private boolean longLasting;
    private boolean fragrant;
    private String status;

    public Rose() {
    }

    public Rose(
            String color,
            String folder,
            String file,
            String variety,
            String englishName,
            int price,
            int quantity,
            boolean longLasting,
            boolean fragrant,
            String status
    ) {
        this.color = color;
        this.folder = folder;
        this.file = file;
        this.variety = variety;
        this.englishName = englishName;
        this.price = price;
        this.quantity = quantity;
        this.longLasting = longLasting;
        this.fragrant = fragrant;
        this.status = status;
    }

    public String getColor() {
        return color;
    }

    public String getFolder() {
        return folder;
    }

    public String getFile() {
        return file;
    }

    public String getVariety() {
        return variety;
    }

    public String getEnglishName() {
        return englishName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isLongLasting() {
        return longLasting;
    }

    public boolean isFragrant() {
        return fragrant;
    }

    public String getStatus() {
        return status;
    }

    public void addQuantity(int amount) {

    this.quantity += amount;

    if (this.quantity <= 0) {

        this.quantity = 0;
        this.status = "売り切れ";

    } else if (this.quantity >= 10) {

        this.status = "在庫あり";

    } else {

        this.status = "残り少なめ";
    }
}

    public String getImagePath() {
        return "/image/rose/" + folder + "/" + file + ".jpg";
    }
    public Long getId() {
    return id;
}
public void setQuantity(int quantity) {
    this.quantity = quantity;
}
}
