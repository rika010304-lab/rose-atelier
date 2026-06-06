import pandas as pd
import matplotlib.pyplot as plt

plt.rcParams["font.family"] = "Meiryo"

df = pd.read_csv("orders.csv")

summary = (
    df.groupby("variety")["quantity"]
    .sum()
    .sort_values(ascending=False)
)

plt.figure(figsize=(10, 6))
summary.plot(kind="bar", color="#8a9f8f")

plt.title("Rose Sales Ranking")
plt.xlabel("Variety")
plt.ylabel("Total Quantity")
plt.xticks(rotation=45, ha="right")
plt.tight_layout()

plt.savefig("rose_sales_ranking.png")
plt.show()

print("売上ランキンググラフを出力しました: rose_sales_ranking.png")