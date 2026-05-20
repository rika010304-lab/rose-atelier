import pandas as pd
import matplotlib.pyplot as plt
import matplotlib.pyplot as plt
plt.rcParams["font.family"] = "Meiryo"

df = pd.read_csv("orders.csv")

summary = df.groupby("variety")["quantity"].sum()
summary = summary.sort_values(ascending=False)

summary.plot(kind="bar")

plt.title("Rose Sales Ranking")
plt.xlabel("Variety")
plt.ylabel("Total Quantity")
plt.tight_layout()

plt.savefig("rose_sales_ranking.png")
plt.show()