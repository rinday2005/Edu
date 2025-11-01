/* ===== LESSON NEWBIE ===== */

// Toggle chapter expand/collapse
function toggleChapter(headerElement) {
  const chapter = headerElement.closest(".chapter")
  const content = chapter.querySelector(".chapter-content")
  const icon = headerElement.querySelector(".chapter-icon")

  const isExpanded = content.style.display !== "none"

  if (isExpanded) {
    content.style.display = "none"
    icon.textContent = "+"
  } else {
    content.style.display = "block"
    icon.textContent = "−"
  }
}

// Expand all chapters
document.addEventListener("DOMContentLoaded", () => {
  const expandAllLink = document.querySelector(".expand-all")
  if (expandAllLink) {
    expandAllLink.addEventListener("click", (e) => {
      e.preventDefault()
      const chapters = document.querySelectorAll(".chapter")
      const allExpanded = Array.from(chapters).every(
        (ch) => ch.querySelector(".chapter-content").style.display !== "none",
      )

      chapters.forEach((chapter) => {
        const content = chapter.querySelector(".chapter-content")
        const icon = chapter.querySelector(".chapter-icon")

        if (allExpanded) {
          content.style.display = "none"
          icon.textContent = "+"
        } else {
          content.style.display = "block"
          icon.textContent = "−"
        }
      })

      expandAllLink.textContent = allExpanded ? "Mở rộng tất cả" : "Thu gọn tất cả"
    })
  }
})

/* ===== ROOM ===== */

// Function to get random avatar
function getRandomAvatar() {
  const avatars = [
    "/PRJ_Assginment_EDUCATION/assets/images/avatar1.jpg",
    "/PRJ_Assginment_EDUCATION/assets/images/avatar2.jpg",
    "/PRJ_Assginment_EDUCATION/assets/images/avatar3.jpg",
  ]
  return avatars[Math.floor(Math.random() * avatars.length)]
}

// Function to get user avatar (if logged in)
function getUserAvatar() {
  console.log("Getting user avatar...")

  // Check hidden input first (most reliable)
  const hiddenAvatarInput = document.getElementById("userAvatarUrl")
  if (hiddenAvatarInput && hiddenAvatarInput.value) {
    console.log("Using avatar from hidden input:", hiddenAvatarInput.value)
    return hiddenAvatarInput.value
  }

  // Check if user is logged in and has avatar from session
  const userAvatarElement = document.querySelector(".user-avatar img")
  console.log("User avatar element:", userAvatarElement)

  if (userAvatarElement && userAvatarElement.src && !userAvatarElement.src.includes("default")) {
    console.log("Using user avatar from header:", userAvatarElement.src)
    return userAvatarElement.src
  }

  // Check for avatar in session data (if available)
  const sessionAvatar = document.querySelector('meta[name="user-avatar"]')
  console.log("Session avatar meta:", sessionAvatar)

  if (sessionAvatar && sessionAvatar.content) {
    console.log("Using session avatar:", sessionAvatar.content)
    return sessionAvatar.content
  }

  // Fallback to random avatar
  const randomAvatar = getRandomAvatar()
  console.log("Using random avatar:", randomAvatar)
  return randomAvatar
}

function openQAModal() {
  const modal = document.getElementById("qaModalOverlay")
  if (modal) {
    modal.style.display = "flex"
    modal.style.visibility = "visible"
    modal.style.opacity = "1"
    document.getElementById("qaInput").focus()

    // Debug log
    console.log("Q&A Modal opened")
  } else {
    console.error("Q&A Modal not found")
  }
}

function closeQAModal() {
  const modal = document.getElementById("qaModalOverlay")
  if (modal) {
    modal.style.display = "none"
    modal.style.visibility = "hidden"
    modal.style.opacity = "0"

    // Debug log
    console.log("Q&A Modal closed")
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const modal = document.getElementById("qaModalOverlay")
  if (modal) {
    modal.addEventListener("click", (e) => {
      if (e.target === modal) {
        closeQAModal()
      }
    })
  }
})

// Play video
function playVideo(event) {
  event.preventDefault()
}

// Handle enrollment
function handleEnroll() {
  const user = document.querySelector(".user-avatar")

  if (!user) {
    window.location.href = "/authen/loginAuthen.jsp"
  } else {
    const courseId = new URLSearchParams(window.location.search).get("id")

    fetch("/api/enrollment/enroll", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        courseId: courseId,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          alert("Đăng ký khóa học thành công!")
          location.reload()
        } else {
          alert("Đăng ký thất bại: " + data.message)
        }
      })
      .catch((error) => {
        alert("Có lỗi xảy ra, vui lòng thử lại")
      })
  }
}

// Initialize theme
function initTheme() {
  const savedTheme = localStorage.getItem("theme") || "dark"
  document.body.classList.toggle("light-mode", savedTheme === "light")
}

function toggleTheme() {
  const isLightMode = document.body.classList.toggle("light-mode")
  const theme = isLightMode ? "light" : "dark"
  localStorage.setItem("theme", theme)
}

function toggleLike(button) {
  button.classList.toggle("liked")
  const comment = button.closest(".qa-comment")
  const likeCount = comment.querySelector(".qa-like-count")

  if (button.classList.contains("liked")) {
    button.textContent = "Thích"
    if (!likeCount) {
      const newLikeCount = document.createElement("span")
      newLikeCount.className = "qa-like-count"
      newLikeCount.textContent = "👍 1"
      button.parentElement.appendChild(newLikeCount)
    } else {
      const count = Number.parseInt(likeCount.textContent.match(/\d+/)[0]) + 1
      likeCount.textContent = "👍 " + count
    }
  } else {
    if (likeCount) {
      const count = Number.parseInt(likeCount.textContent.match(/\d+/)[0]) - 1
      if (count > 0) {
        likeCount.textContent = "👍 " + count
      } else {
        likeCount.remove()
      }
    }
  }
}

function toggleReply(button) {
  const comment = button.closest(".qa-comment")
  let replyForm = comment.querySelector(".qa-reply-form")

  if (replyForm) {
    replyForm.remove()
  } else {
    replyForm = document.createElement("div")
    replyForm.className = "qa-reply-form"
    replyForm.innerHTML = `
      <div class="qa-reply-input-wrapper">
        <input type="text" class="qa-reply-input" placeholder="Nhập phản hồi của bạn..." />
        <button class="qa-reply-send-btn" onclick="submitReply(this)">
          <svg viewBox="0 0 24 24" width="16" height="16" fill="currentColor">
            <path d="M16.6915026,12.4744748 L3.50612381,13.2599618 C3.19218622,13.2599618 3.03521743,13.4170592 3.03521743,13.5741566 L1.15159189,20.0151496 C0.8376543,20.8006365 0.99,21.89 1.77946707,22.52 C2.41,22.99 3.50612381,23.1 4.13399899,22.8429026 L21.714504,14.0454487 C22.6563168,13.5741566 23.1272231,12.6315722 22.9702544,11.6889879 L4.13399899,1.16346272 C3.34915502,0.9 2.40734225,1.00636533 1.77946707,1.4776575 C0.994623095,2.10604706 0.837654326,3.0486314 1.15159189,3.99701575 L3.03521743,10.4380088 C3.03521743,10.5951061 3.19218622,10.7522035 3.50612381,10.7522035 L16.6915026,11.5376905 C16.6915026,11.5376905 17.1624089,11.5376905 17.1624089,12.0089827 C17.1624089,12.4744748 16.6915026,12.4744748 16.6915026,12.4744748 Z"/>
          </svg>
        </button>
      </div>
    `
    comment.appendChild(replyForm)
    comment.querySelector(".qa-reply-input").focus()
  }
}

function submitReply(button) {
  const replyInput = button.previousElementSibling
  const replyText = replyInput.value.trim()

  if (replyText) {
    const comment = button.closest(".qa-comment")
    const replyForm = button.closest(".qa-reply-form")

    // Tạo reply comment
    const replyComment = document.createElement("div")
    replyComment.className = "qa-comment qa-reply-comment"
    replyComment.innerHTML = `
      <div class="qa-comment-avatar" style="background-image: url('${getUserAvatar()}')"></div>
      <div class="qa-comment-content">
        <div class="qa-comment-header">
          <span class="qa-comment-author">Bạn</span>
          <span class="qa-comment-time">vừa xong</span>
        </div>
        <div class="qa-comment-text">${replyText}</div>
        <div class="qa-comment-actions">
          <button class="qa-action-btn qa-like-btn" onclick="toggleLike(this)">Thích</button>
          <button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>
          <button class="qa-action-menu">...</button>
        </div>
      </div>
    `

    // Thêm reply vào sau comment gốc
    comment.parentNode.insertBefore(replyComment, comment.nextSibling)

    // Xóa form reply
    replyForm.remove()

    // Cập nhật số lượng bình luận
    updateCommentCount()
  }
}

function updateCommentCount() {
  const commentsList = document.getElementById("qaCommentsList")
  const comments = commentsList.querySelectorAll(".qa-comment")
  const countElement = document.querySelector(".qa-count")
  const badgeElement = document.querySelector(".qa-count-badge")

  if (countElement) {
    countElement.textContent = `${comments.length} bình luận`
  }

  if (badgeElement) {
    badgeElement.textContent = comments.length
  }
}

function submitComment() {
  const qaInput = document.getElementById("qaInput")
  const commentText = qaInput.value.trim()

  if (commentText) {
    const newComment = document.createElement("div")
    newComment.className = "qa-comment"
    newComment.innerHTML = `
      <div class="qa-comment-avatar" style="background-image: url('${getUserAvatar()}')"></div>
      <div class="qa-comment-content">
        <div class="qa-comment-header">
          <span class="qa-comment-author">Bạn</span>
          <span class="qa-comment-time">vừa xong</span>
        </div>
        <div class="qa-comment-text">${commentText}</div>
        <div class="qa-comment-actions">
          <button class="qa-action-btn qa-like-btn" onclick="toggleLike(this)">Thích</button>
          <button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>
          <button class="qa-action-menu">...</button>
        </div>
      </div>
    `

    const commentsList = document.getElementById("qaCommentsList")
    commentsList.insertBefore(newComment, commentsList.firstChild)

    qaInput.value = ""

    // Cập nhật số lượng bình luận
    updateCommentCount()
  }
}

document.addEventListener("DOMContentLoaded", () => {
  initTheme()

  // Debug: Check if elements exist
  const qaModal = document.getElementById("qaModalOverlay")
  const qaSendBtn = document.getElementById("qaSendBtn")
  const qaInput = document.getElementById("qaInput")
  const qaHeader = document.querySelector(".qa-header")

  console.log("Q&A Modal:", qaModal)
  console.log("Q&A Send Button:", qaSendBtn)
  console.log("Q&A Input:", qaInput)
  console.log("Q&A Header:", qaHeader)

  if (qaModal) {
    qaModal.addEventListener("click", (e) => {
      if (e.target === qaModal) {
        closeQAModal()
      }
    })
  }

  if (qaSendBtn) {
    qaSendBtn.addEventListener("click", submitComment)
  }

  if (qaInput) {
    qaInput.addEventListener("keypress", (e) => {
      if (e.key === "Enter" && !e.shiftKey) {
        e.preventDefault()
        submitComment()
      }
    })
  }

  if (qaHeader) {
    qaHeader.addEventListener("click", openQAModal)
  }

  // Initialize comment count
  updateCommentCount()

  // Initialize avatars for existing comments
  initializeAvatars()
})

// Function to initialize avatars for existing comments
function initializeAvatars() {
  const existingAvatars = document.querySelectorAll('.qa-comment-avatar:not([style*="background-image"])')
  existingAvatars.forEach((avatar) => {
    avatar.style.backgroundImage = `url('${getRandomAvatar()}')`
  })
}

/* ===== LIST COURSE ===== */

// Added course filtering and sorting
function filterCourses(level) {
  const courses = document.querySelectorAll(".pro-course-card")
  console.log("[v0] Filtering courses by level:", level)

  courses.forEach((course) => {
    const courseLevel = course.querySelector(".stat-label:nth-of-type(3)")?.textContent || ""
    if (level === "all" || courseLevel.includes(level)) {
      course.style.display = "block"
    } else {
      course.style.display = "none"
    }
  })
}

function sortCourses(sortBy) {
  console.log("[v0] Sorting courses by:", sortBy)
  alert(`Sắp xếp khóa học theo: ${sortBy}`)
}

function enrollCourse(courseId) {
  console.log("[v0] Enrolling in course:", courseId)
  alert("Bạn đã đăng ký khóa học thành công!")
}

// Initialize page events
document.addEventListener("DOMContentLoaded", () => {
  console.log("[v0] Course page loaded")

  // Add course card click handlers
  const courseCards = document.querySelectorAll(".pro-course-card")
  courseCards.forEach((card) => {
    card.addEventListener("click", function () {
      const courseId = this.getAttribute("data-course-id") || "1"
      enrollCourse(courseId)
    })
  })
})
