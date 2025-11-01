/* ===== ARTICLES ===== */

// Articles Page JavaScript

function filterByTopic(element) {
  // Remove active class from all tabs
  const tabs = document.querySelectorAll(".topic-tab")
  tabs.forEach((tab) => tab.classList.remove("active"))

  // Add active class to clicked tab
  element.classList.add("active")

  // Get the topic name
  const topic = element.textContent.trim()
  console.log("[v0] Filtering articles by topic:", topic)

  // Here you would typically filter the articles based on the selected topic
  // For now, this is a placeholder for the filtering logic
}

// Initialize active tab on page load
document.addEventListener("DOMContentLoaded", () => {
  const firstTab = document.querySelector(".topic-tab")
  if (firstTab) {
    firstTab.classList.add("active")
  }
})

/* Added search and filter functions */
function searchArticles(query) {
  const articles = document.querySelectorAll(".article-item")
  console.log("[v0] Searching for:", query)

  articles.forEach((article) => {
    const title = article.querySelector(".article-title").textContent.toLowerCase()
    const description = article.querySelector(".article-description").textContent.toLowerCase()

    if (title.includes(query.toLowerCase()) || description.includes(query.toLowerCase())) {
      article.style.display = "block"
    } else {
      article.style.display = "none"
    }
  })
}

function toggleBookmark(button) {
  button.classList.toggle("bookmarked")
  const isBookmarked = button.classList.contains("bookmarked")
  console.log("[v0] Article bookmarked:", isBookmarked)
}

function shareArticle(platform) {
  const title = document.querySelector(".article-title")?.textContent || "Bài viết"
  console.log("[v0] Sharing article on", platform)
  alert(`Chia sẻ bài viết trên ${platform}!`)
}

/* Initialize page events */
document.addEventListener("DOMContentLoaded", () => {
  console.log("[v0] Articles page loaded")

  // Add bookmark functionality
  const bookmarkButtons = document.querySelectorAll(".bookmark-btn")
  bookmarkButtons.forEach((btn) => {
    btn.addEventListener("click", function () {
      toggleBookmark(this)
    })
  })
})

/* ===== ARTICLES DETAIL ===== */

function toggleLike(button) {
  const likeCount = button.querySelector(".like-count")
  const currentCount = Number.parseInt(likeCount.textContent)

  if (button.classList.contains("liked")) {
    button.classList.remove("liked")
    likeCount.textContent = currentCount - 1
  } else {
    button.classList.add("liked")
    likeCount.textContent = currentCount + 1
  }
}

function toggleComments() {
  const commentsSection = document.querySelector(".comments-section")
  if (commentsSection) {
    commentsSection.style.display = commentsSection.style.display === "none" ? "block" : "none"
  }
}

function filterRelatedArticles(button) {
  // Remove active class from all tabs
  const tabs = document.querySelectorAll(".topic-filter-tab")
  tabs.forEach((tab) => tab.classList.remove("active"))

  // Add active class to clicked tab
  button.classList.add("active")

  // Filter logic would go here
  console.log("[v0] Filtering articles by topic:", button.textContent)
}

// Initialize page
document.addEventListener("DOMContentLoaded", () => {
  console.log("[v0] Article detail page loaded")
})
