/* ===================== GLOBAL THEME TOGGLE ===================== */
// This file is used by all pages to manage dark/light mode

function initTheme() {
  const savedTheme = localStorage.getItem("theme") || "dark"
  document.body.classList.toggle("light-mode", savedTheme === "light")
}

function toggleTheme() {
  const isLightMode = document.body.classList.toggle("light-mode")
  const theme = isLightMode ? "light" : "dark"
  localStorage.setItem("theme", theme)
}

// Initialize theme when DOM is loaded
document.addEventListener("DOMContentLoaded", () => {
  initTheme()
})
