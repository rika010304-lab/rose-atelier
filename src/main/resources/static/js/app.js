document.addEventListener("DOMContentLoaded", () => {
  setupAdminSearch();
  setupOrderFilter();
  setupWasteCsvDownload();
});

function setupAdminSearch() {
  const searchBox = document.querySelector(".search-box");

  if (!searchBox) {
    return;
  }

  const rows = document.querySelectorAll("tbody tr");

  searchBox.addEventListener("input", () => {
    const keyword = searchBox.value.trim().toLowerCase();

    rows.forEach((row) => {
      const rowText = row.textContent.toLowerCase();
      row.style.display = rowText.includes(keyword) ? "" : "none";
    });
  });
}

function setupOrderFilter() {
  const filterButtons = document.querySelectorAll("#filterButtons .chip");
  const flowerCards = document.querySelectorAll(".flower-card");

  if (filterButtons.length === 0 || flowerCards.length === 0) {
    return;
  }

  filterButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const filter = button.dataset.filter;

      filterButtons.forEach((item) => {
        item.classList.remove("active");
      });

      button.classList.add("active");

      flowerCards.forEach((card) => {
        const color = card.dataset.color;
        const shouldShow = filter === "all" || color === filter;

        card.style.display = shouldShow ? "" : "none";
      });
    });
  });
}

function setupWasteCsvDownload() {
  const button = document.getElementById("downloadWasteCsvBtn");

  if (!button) {
    return;
  }

  button.addEventListener("click", () => {
    const varietySelect = document.querySelector('select[name="id"]');
    const quantityInput = document.querySelector('input[name="quantity"]');

    if (!varietySelect || !quantityInput) {
      return;
    }

    const selectedOption = varietySelect.selectedOptions[0];
    const variety = selectedOption ? selectedOption.textContent.trim() : "";
    const quantity = quantityInput.value.trim();

    if (!variety || !quantity) {
      alert("品種名と廃棄本数を入力してください。");
      return;
    }

    const csv = [
      ["variety", "quantity", "createdAt"],
      [variety, quantity, new Date().toLocaleString("ja-JP")]
    ]
      .map((row) => row.map(escapeCsvValue).join(","))
      .join("\n");

    const blob = new Blob(["\uFEFF" + csv], {
      type: "text/csv;charset=utf-8;"
    });

    const url = URL.createObjectURL(blob);
    const link = document.createElement("a");

    link.href = url;
    link.download = "waste.csv";
    link.click();

    URL.revokeObjectURL(url);
  });
}

function escapeCsvValue(value) {
  const text = String(value);

  if (text.includes(",") || text.includes("\"") || text.includes("\n")) {
    return `"${text.replaceAll("\"", "\"\"")}"`;
  }

  return text;
}