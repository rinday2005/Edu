/* ===== LEARNING PATH ===== */

/* Added learning path navigation */
function navigateTo(path) {
  window.location.href = path
}

function selectLearningPath(pathType) {
  console.log("[v0] Selected learning path:", pathType)
  alert(`Bạn đã chọn lộ trình: ${pathType}`)
}

function startLesson(lessonId) {
  console.log("[v0] Starting lesson:", lessonId)
  window.location.href = `lesson.jsp?id=${lessonId}`
}

function completeLesson(lessonId) {
  console.log("[v0] Completed lesson:", lessonId)
  alert("Chúc mừng! Bạn đã hoàn thành bài học này.")
}

/* Initialize page events */
document.addEventListener("DOMContentLoaded", () => {
  console.log("[v0] Learning page loaded")

  // Add learning path card click handlers
  const pathCards = document.querySelectorAll(".learning-path-card")
  pathCards.forEach((card) => {
    card.addEventListener("hover", function () {
      this.style.transform = "translateY(-5px)"
    })
  })
})

/* ===== LEARNING PATH FE ===== */

/* ===== LEARNING PATH BE ===== */
