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

    // Fetch comments by courseID (preferred) or lessonID
    const courseID = window.currentCourseID || null
    if (courseID) {
      fetchCommentsByCourse(courseID)
    } else if (currentLessonId) {
      fetchComments(currentLessonId)
    }

    // Debug log
    console.log("Q&A Modal opened, courseID:", courseID, "lessonID:", currentLessonId)
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
  const qaModal = document.getElementById("qaModalOverlay")
  if (qaModal) {
    qaModal.addEventListener("click", (e) => {
      if (e.target === qaModal) {
        closeQAModal()
      }
    })
  }
  
  const testModal = document.getElementById("testModalOverlay")
  if (testModal) {
    testModal.addEventListener("click", (e) => {
      if (e.target === testModal) {
        closeTestModal()
      }
    })
  }
})

// Play video
function playVideo(event) {
  event.preventDefault()
}

// Load selected lesson video into player
let currentLessonId = null
let currentSectionId = null
function loadLessonVideo(element) {
  const url = element.getAttribute("data-video-url")
  const name = element.getAttribute("data-lesson-name")
  currentLessonId = element.getAttribute("data-lesson-id")
  currentSectionId = element.getAttribute('data-section-id')
  const container = document.querySelector(".video-container")
  if (!container) return

  if (isYouTubeUrl(url)) {
    const videoId = extractYouTubeId(url)
    if (!videoId) return
    container.innerHTML = `
      <iframe
        id="ytPlayer"
        width="100%"
        height="480"
        src="https://www.youtube.com/embed/${videoId}?rel=0&modestbranding=1&autoplay=1"
        title="YouTube video player"
        frameborder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        referrerpolicy="strict-origin-when-cross-origin"
        allowfullscreen
      ></iframe>`
  } else {
    // Ensure a <video> element exists
    let video = container.querySelector("#courseVideo")
    if (!video) {
      container.innerHTML = ""
      video = document.createElement("video")
      video.id = "courseVideo"
      video.className = "video-player"
      video.controls = true
      video.setAttribute("controlsList", "nodownload")
      const source = document.createElement("source")
      source.type = "video/mp4"
      video.appendChild(source)
      container.appendChild(video)
    }

    const source = video.querySelector("source")
    source.src = url || ""
    video.load()
    video.play().catch(() => {})
  }

  // update title
  const title = document.querySelector(".video-title")
  if (title && name) title.textContent = name

  // active state
  document.querySelectorAll(".lesson-item.active").forEach((el) => el.classList.remove("active"))
  element.classList.add("active")

  // Load comments for this lesson or course
  const courseID = window.currentCourseID || null
  if (courseID) {
    // Prefer course-level comments
    fetchCommentsByCourse(courseID)
  } else if (currentLessonId) {
    fetchComments(currentLessonId)
  }
  
  // Load tests for this lesson
  if (currentLessonId) {
    fetchTestsForLesson(currentLessonId)
  }
  if (currentSectionId) {
    fetchAssignments(currentSectionId)
  }
}

function isYouTubeUrl(url) {
  if (!url) return false
  return /^(https?:\/\/)?(www\.)?(youtube\.com|youtu\.be)\//i.test(url)
}

function extractYouTubeId(url) {
  if (!url) return null
  try {
    const u = new URL(url, window.location.origin)
    // youtu.be/<id>
    if (u.hostname.includes("youtu.be")) {
      return u.pathname.replace("/", "").split("/")[0]
    }
    // youtube.com/watch?v=<id>
    if (u.searchParams.get("v")) return u.searchParams.get("v")
    // youtube.com/embed/<id>
    if (u.pathname.includes("/embed/")) return u.pathname.split("/embed/")[1]?.split("/")[0]
  } catch (e) {
    // fallback regex
    const m = url.match(/(?:v=|youtu\.be\/|embed\/)([A-Za-z0-9_-]{11})/)
    return m ? m[1] : null
  }
  return null
}

/* ===== COMMENTS: EDIT / DELETE / REPORT / MODERATE ===== */
function getUserRole() {
  // Try meta tag first
  const m = document.querySelector('meta[name="user-role"]')
  if (m?.content) return m.content
  // Try body attribute
  const bodyRole = document.body.getAttribute('data-user-role')
  if (bodyRole) return bodyRole
  return "Learner"
}

function getUserId() {
  // Try meta tag first
  const m = document.querySelector('meta[name="user-id"]')
  if (m?.content) return m.content
  // Try body attribute
  const bodyId = document.body.getAttribute('data-user-id')
  if (bodyId) return bodyId
  return ""
}

function renderActionsForComment(authorId) {
  const role = getUserRole()
  const currentUserId = getUserId()
  const isOwner = currentUserId && authorId && currentUserId.toUpperCase() === authorId.toUpperCase()
  const isInstructor = role === "Instructor"
  const isAdmin = role === "Admin"

  // Debug logging
  console.log('[renderActionsForComment]', {
    currentUserId,
    authorId,
    isOwner,
    role
  })

  const actions = []
  if (isOwner) {
    actions.push('<button class="qa-action-btn qa-edit-btn" onclick="startEditComment(this)">Chỉnh sửa</button>')
    actions.push('<button class="qa-action-btn qa-delete-btn" onclick="deleteComment(this)">Xóa</button>')
  }
  actions.push('<button class="qa-action-btn qa-reply-btn" onclick="toggleReply(this)">Phản hồi</button>')
  // three-dots menu for report and moderation
  actions.push('<div class="qa-more"><button class="qa-action-btn qa-more-btn" onclick="toggleMoreMenu(this)">...</button><div class="qa-more-menu">'
    + (!isOwner ? '<button class="qa-more-item" onclick="reportCommentFromMenu(this)">Report</button>' : '')
    + (isInstructor ? '<button class="qa-more-item" onclick="restrictCommentFromMenu(this)">Hạn chế</button>' : '')
    + (isAdmin ? '<button class="qa-more-item" onclick="adminDeleteCommentFromMenu(this)">Xóa (Admin)</button>' : '')
    + '</div></div>')
  if (isInstructor) {
    actions.push('<button class="qa-action-btn" onclick="restrictComment(this)">Hạn chế</button>')
  }
  if (isAdmin) {
    actions.push('<button class="qa-action-btn" onclick="adminDeleteComment(this)">Xóa (Admin)</button>')
  }
  return actions.join("")
}

function toggleMoreMenu(btn) {
  const menu = btn.nextElementSibling
  if (!menu) return
  // close others
  document.querySelectorAll('.qa-more-menu.show').forEach(m => { if (m !== menu) m.classList.remove('show') })
  menu.classList.toggle('show')
  // click outside to close
  const close = (e) => {
    if (!menu.contains(e.target) && e.target !== btn) {
      menu.classList.remove('show')
      document.removeEventListener('click', close)
    }
  }
  setTimeout(() => document.addEventListener('click', close), 0)
}

function reportCommentFromMenu(el) {
  const comment = el.closest('.qa-comment')
  const dummyBtn = comment.querySelector('.qa-action-btn')
  reportComment(dummyBtn)
}

function restrictCommentFromMenu(el) {
  const comment = el.closest('.qa-comment')
  const dummyBtn = comment.querySelector('.qa-action-btn')
  restrictComment(dummyBtn)
}

function adminDeleteCommentFromMenu(el) {
  const comment = el.closest('.qa-comment')
  const dummyBtn = comment.querySelector('.qa-action-btn')
  adminDeleteComment(dummyBtn)
}

function updateRepliesToggle(comment) {
  const repliesContainer = comment.querySelector('.qa-replies')
  const repliesCount = repliesContainer ? repliesContainer.querySelectorAll('.qa-reply-comment').length : 0
  let toggle = comment.querySelector('.qa-replies-toggle')

  if (repliesCount > 0) {
    if (!toggle) {
      toggle = document.createElement('button')
      toggle.type = 'button'
      toggle.className = 'qa-replies-toggle'
      const actions = comment.querySelector('.qa-comment-actions')
      if (actions && actions.nextSibling) {
        actions.parentNode.insertBefore(toggle, actions.nextSibling)
      } else if (actions) {
        actions.after(toggle)
      } else {
        comment.appendChild(toggle)
      }

      toggle.addEventListener('click', () => {
        const container = comment.querySelector('.qa-replies')
        if (!container) return
        const visible = container.style.display !== 'none'
        container.style.display = visible ? 'none' : 'block'
        toggle.textContent = (visible ? 'Xem ' : 'Ẩn ') + (container.querySelectorAll('.qa-reply-comment').length) + ' trả lời'
      })
    }

    if (repliesContainer) {
      repliesContainer.style.display = 'none'
      repliesContainer.style.marginTop = '8px'
    }
    toggle.textContent = 'Xem ' + repliesCount + ' trả lời'
  } else if (toggle) {
    toggle.remove()
  }
}

function updateAllRepliesToggle() {
  document.querySelectorAll('.qa-comment').forEach(updateRepliesToggle)
}

function startEditComment(btn) {
  const comment = btn.closest('.qa-comment')
  const textEl = comment.querySelector('.qa-comment-text')
  if (!textEl) return
  const current = textEl.innerText
  // Use separate edit form class to avoid conflict with reply form
  const editor = document.createElement('div')
  editor.className = 'qa-edit-form'
  editor.innerHTML = `
    <div class="qa-reply-input-wrapper">
      <input type="text" class="qa-reply-input" value="${current.replace(/"/g, '&quot;')}">
      <button class="qa-reply-send-btn" onclick="commitEditComment(this)">Lưu</button>
      <button class="qa-reply-send-btn" onclick="cancelEditComment(this)">Hủy</button>
    </div>`
  textEl.style.display = 'none'
  // Insert correctly under the same parent node as text, display after text
  const parent = textEl.parentNode
  parent.insertBefore(editor, textEl.nextSibling)
  editor.querySelector('.qa-reply-input').focus()
}

function commitEditComment(btn) {
  const wrap = btn.closest('.qa-edit-form')
  const comment = btn.closest('.qa-comment')
  const textEl = comment.querySelector('.qa-comment-text')
  const input = wrap.querySelector('.qa-reply-input')
  const newText = input.value.trim()
  if (!newText) { return }

  const commentId = comment.getAttribute('data-comment-id') || ''
  if (!commentId) { alert('Không có mã bình luận để cập nhật. Vui lòng thử lại sau.'); return }
  const base = document.body.getAttribute('data-context') || ''

  // Disable buttons to prevent duplicate submission
  const btns = wrap.querySelectorAll('button')
  btns.forEach(b => b.setAttribute('disabled','disabled'))

  // Persist to backend first, then update UI on success
  const form = new URLSearchParams()
  form.append('commentID', commentId)
  form.append('content', newText)

  // Use POST + _method=PUT to fix container parsing issues with PUT forms
  form.append('_method', 'PUT')
  fetch(`${base}/api/comments`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: form.toString(),
  }).then(res => {
    if (!res.ok) {
      if (res.status === 401) throw new Error('Bạn cần đăng nhập để chỉnh sửa.')
      if (res.status === 403) throw new Error('Bạn chỉ có thể chỉnh sửa bình luận của mình.')
      throw new Error('Update failed')
    }
    // Update UI
    textEl.textContent = newText
    const edited = comment.querySelector('.qa-edited') || document.createElement('span')
    edited.className = 'qa-edited'
    edited.textContent = 'Đã chỉnh sửa'
    const actions = comment.querySelector('.qa-comment-actions')
    if (actions && !comment.querySelector('.qa-edited')) actions.appendChild(edited)
    textEl.style.display = ''
    wrap.remove()
  }).catch((e) => {
    alert('Cập nhật bình luận thất bại. ' + (e && e.message ? e.message : 'Vui lòng thử lại!'))
    btns.forEach(b => b.removeAttribute('disabled'))
  })
}

function cancelEditComment(btn) {
  const wrap = btn.closest('.qa-edit-form')
  const comment = btn.closest('.qa-comment')
  const textEl = comment.querySelector('.qa-comment-text')
  textEl.style.display = ''
  wrap.remove()
}

function deleteComment(btn) {
  const comment = btn.closest('.qa-comment')
  const parentReplies = comment.closest('.qa-replies')
  const parentComment = parentReplies ? parentReplies.closest('.qa-comment') : null
  const commentId = comment.getAttribute('data-comment-id') || ''
  const base = document.body.getAttribute('data-context') || ''
  if (!commentId) {
    comment.remove()
    updateCommentCount()
    if (parentComment) updateRepliesToggle(parentComment)
    return
  }
  const form = new URLSearchParams()
  form.append('commentID', commentId)
  form.append('_method', 'DELETE')
  fetch(`${base}/api/comments`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: form.toString(),
  }).then(res => {
    if (!res.ok) {
      if (res.status === 401) throw new Error('Bạn cần đăng nhập để xóa.')
      if (res.status === 403) throw new Error('Bạn chỉ có thể xóa bình luận của mình.')
      throw new Error('Delete failed')
    }
    comment.remove()
    updateCommentCount()
    if (parentComment) updateRepliesToggle(parentComment)
  }).catch(e => {
    alert('Xóa bình luận thất bại. ' + (e && e.message ? e.message : 'Vui lòng thử lại!'))
  })
}

function adminDeleteComment(btn) {
  deleteComment(btn)
}

function reportComment(btn) {
  const comment = btn.closest('.qa-comment')
  const commentId = comment.getAttribute('data-comment-id')
  
  if (!commentId) {
    alert('Không tìm thấy ID bình luận để báo cáo.')
    return
  }
  
  // Check if already reported
  if (comment.classList.contains('reported')) {
    alert('Bạn đã báo cáo bình luận này rồi.')
    return
  }
  
  // Confirm report
  if (!confirm('Bạn có chắc muốn báo cáo bình luận này là spam/nội dung không phù hợp?')) {
    return
  }
  
  const base = document.body.getAttribute('data-context') || ''
  const form = new URLSearchParams()
  form.append('action', 'report')
  form.append('commentID', commentId)
  form.append('reason', 'spam')
  
  fetch(`${base}/api/comments`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: form.toString(),
  })
  .then(r => {
    if (!r.ok) {
      return r.json().then(j => Promise.reject(new Error(j.error || 'Failed to report')))
    }
    return r.json()
  })
  .then(j => {
    if (j.success) {
      comment.classList.add('reported')
      // Disable report button
      const reportBtn = comment.querySelector('.qa-action-btn[onclick*="reportComment"]')
      if (reportBtn) {
        reportBtn.setAttribute('disabled', 'disabled')
        reportBtn.textContent = 'Đã báo cáo'
        reportBtn.style.opacity = '0.6'
        reportBtn.style.cursor = 'not-allowed'
      }
      alert('✓ ' + (j.message || 'Đã gửi báo cáo tới admin thành công!'))
    } else {
      alert('✗ ' + (j.error || 'Không thể gửi báo cáo. Vui lòng thử lại.'))
    }
  })
  .catch(err => {
    console.error('Error reporting comment:', err)
    alert('✗ Có lỗi xảy ra: ' + (err.message || 'Vui lòng thử lại.'))
  })
}

function restrictComment(btn) {
  const comment = btn.closest('.qa-comment')
  comment.classList.toggle('restricted')
  // Disable reply after restriction
  if (comment.classList.contains('restricted')) {
    const replyBtn = comment.querySelector('.qa-reply-btn')
    if (replyBtn) replyBtn.setAttribute('disabled', 'disabled')
  } else {
    const replyBtn = comment.querySelector('.qa-reply-btn')
    if (replyBtn) replyBtn.removeAttribute('disabled')
  }
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
  // Ensure replies container lives inside content so it stacks vertically
  const content = comment.querySelector('.qa-comment-content') || comment
  let replies = content.querySelector('.qa-replies')
  if (!replies) {
    replies = document.createElement('div')
    replies.className = 'qa-replies'
    content.appendChild(replies)
  }
  let replyForm = replies.querySelector(".qa-reply-form")
  if (replyForm) { replyForm.remove(); return }
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
    </div>`
  replies.appendChild(replyForm)
  replies.style.display = 'block'
  const input = replyForm.querySelector(".qa-reply-input")
  input.focus()
  // Support Enter to send directly (Shift+Enter for new line)
  input.addEventListener('keydown', (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault()
      replyForm.querySelector('.qa-reply-send-btn').click()
    }
  })
}

function submitReply(button) {
  const replyInput = button.previousElementSibling
  const replyText = replyInput.value.trim()

  if (!replyText) return

  const comment = button.closest(".qa-comment")
  const replyForm = button.closest(".qa-reply-form")

  // Get lessonID for reply (same as parent comment's lesson)
  const courseID = window.currentCourseID || null
  let targetLessonId = currentLessonId
  
  // If no lesson selected but we have courseID, try to get first lesson
  if (!targetLessonId && courseID) {
    const firstLesson = document.querySelector('.lesson-item[data-lesson-id]')
    if (firstLesson) {
      targetLessonId = firstLesson.getAttribute('data-lesson-id')
    }
  }

  if (!targetLessonId) {
    alert('Vui lòng chọn một bài học để phản hồi.')
    return
  }

  // Create reply comment (embedded in parent comment's replies area)
  const replyComment = document.createElement("div")
  replyComment.className = "qa-comment qa-reply-comment"
  const authorId = getUserId()
  replyComment.setAttribute('data-user-id', authorId)
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
        ${renderActionsForComment(authorId)}
      </div>
    </div>
  `
  // Append to reply container inside parent comment content area, ensuring vertical expansion
  const content = comment.querySelector('.qa-comment-content') || comment
  let replies = content.querySelector('.qa-replies')
  if (!replies) {
    replies = document.createElement('div')
    replies.className = 'qa-replies'
    content.appendChild(replies)
  }
  replies.appendChild(replyComment)
  replies.style.display = 'block'

  // Xóa form reply
  replyForm.remove()

  // Cập nhật số lượng bình luận
  updateCommentCount()
  updateRepliesToggle(comment)

  // persist to backend
  const parentCommentId = comment.getAttribute('data-comment-id') || ''
  const form = new URLSearchParams()
  form.append('lessionID', targetLessonId)
  form.append('content', replyText)
  if (parentCommentId) form.append('parentID', parentCommentId)
  if (courseID) form.append('courseID', courseID)
  
  fetch(`${document.body.getAttribute('data-context') || ''}/api/comments`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: form.toString(),
  })
  .then(r => {
    if (!r.ok) {
      throw new Error('Failed to submit reply')
    }
    return r.json()
  })
  .then(j => {
    if (j && j.commentID) {
      replyComment.setAttribute('data-comment-id', j.commentID)
      // Reload comments to get updated list with proper author info
      if (courseID) {
        fetchCommentsByCourse(courseID)
      } else if (targetLessonId) {
        fetchComments(targetLessonId)
      }
    }
  })
  .catch(err => {
    console.error('Error submitting reply:', err)
    alert('Có lỗi xảy ra khi phản hồi. Vui lòng thử lại.')
  })
}

function updateCommentCount() {
  const commentsList = document.getElementById("qaCommentsList")
  if (!commentsList) return
  
  // Count only top-level comments (not replies)
  const topLevelComments = commentsList.querySelectorAll(".qa-comment:not(.qa-reply-comment)")
  const totalComments = commentsList.querySelectorAll(".qa-comment")
  const count = topLevelComments.length
  const totalCount = totalComments.length
  
  const countElement = document.querySelector(".qa-count")
  const badgeElement = document.getElementById("qaCountBadge") || document.querySelector(".qa-count-badge")

  if (countElement) {
    countElement.textContent = `${count} bình luận`
  }

  if (badgeElement) {
    badgeElement.textContent = count
  }
  
  console.log('[updateCommentCount] Top-level comments:', count, 'Total comments:', totalCount)
}

function submitComment() {
  const qaInput = document.getElementById("qaInput")
  const commentText = qaInput.value.trim()

  if (!commentText) return

  // Get courseID or lessonID for comment submission
  const courseID = window.currentCourseID || null
  const lessonId = currentLessonId || null
  
  // For course-level comments, we need a lessonID to store the comment
  // Use the first lesson of the course if available, or current lesson
  let targetLessonId = lessonId
  
  // If no lesson selected but we have courseID, try to get first lesson
  if (!targetLessonId && courseID) {
    const firstLesson = document.querySelector('.lesson-item[data-lesson-id]')
    if (firstLesson) {
      targetLessonId = firstLesson.getAttribute('data-lesson-id')
    }
  }

  if (!targetLessonId) {
    alert('Vui lòng chọn một bài học để đăng bình luận.')
    return
  }

  const newComment = document.createElement("div")
  newComment.className = "qa-comment"
  const authorId = getUserId()
  newComment.setAttribute('data-user-id', authorId)
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
        ${renderActionsForComment(authorId)}
      </div>
    </div>
  `

  const commentsList = document.getElementById("qaCommentsList")
  commentsList.insertBefore(newComment, commentsList.firstChild)

  qaInput.value = ""

  // Cập nhật số lượng bình luận
  updateCommentCount()

  // persist to backend
  const form = new URLSearchParams()
  form.append('lessionID', targetLessonId)
  form.append('content', commentText)
  if (courseID) {
    // Store courseID in a hidden field or use it for filtering
    form.append('courseID', courseID)
  }
  
  fetch(`${document.body.getAttribute('data-context') || ''}/api/comments`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: form.toString(),
  })
  .then(r => {
    if (!r.ok) {
      throw new Error('Failed to submit comment')
    }
    return r.json()
  })
  .then(j => {
    if (j && j.commentID) {
      newComment.setAttribute('data-comment-id', j.commentID)
      // Reload comments to get updated list with proper author info
      if (courseID) {
        fetchCommentsByCourse(courseID)
      } else if (targetLessonId) {
        fetchComments(targetLessonId)
      }
    }
  })
  .catch(err => {
    console.error('Error submitting comment:', err)
    alert('Có lỗi xảy ra khi đăng bình luận. Vui lòng thử lại.')
  })
}

// Fetch comments by courseID (for course-level comments)
function fetchCommentsByCourse(courseID) {
  const base = document.body.getAttribute('data-context') || ''
  console.log('[fetchCommentsByCourse] Fetching comments for courseID:', courseID)
  fetch(`${base}/api/comments?courseID=${encodeURIComponent(courseID)}`)
    .then(r => {
      if (!r.ok) {
        console.error('[fetchCommentsByCourse] Error response:', r.status)
        return []
      }
      return r.json()
    })
    .then(list => {
      console.log('[fetchCommentsByCourse] Received comments:', list ? list.length : 0)
      renderComments(list || [])
      updateCommentCount()
    })
    .catch(err => {
      console.error('[fetchCommentsByCourse] Error:', err)
      renderComments([])
      updateCommentCount()
    })
}

// Fetch comments by lessonID (for lesson-level comments)
function fetchComments(lessonId) {
  const base = document.body.getAttribute('data-context') || ''
  console.log('[fetchComments] Fetching comments for lessonID:', lessonId)
  fetch(`${base}/api/comments?lessionID=${encodeURIComponent(lessonId)}`)
    .then(r => {
      if (!r.ok) {
        console.error('[fetchComments] Error response:', r.status)
        return []
      }
      return r.json()
    })
    .then(list => {
      console.log('[fetchComments] Received comments:', list ? list.length : 0)
      renderComments(list || [])
      updateCommentCount()
    })
    .catch(err => {
      console.error('[fetchComments] Error:', err)
      renderComments([])
      updateCommentCount()
    })
}

function fetchAssignments(sectionId) {
  const base = document.body.getAttribute('data-context') || ''
  fetch(`${base}/api/assignments?sectionID=${encodeURIComponent(sectionId)}`)
    .then(r=>r.json())
    .then(list => renderAssignments(list))
    .catch(()=>{})
}

function fetchTestsForLesson(lessonId) {
  const base = document.body.getAttribute('data-context') || ''
  const testContainer = document.querySelector(`.test-list-container[data-lesson-id="${lessonId}"]`)
  if (!testContainer) {
    console.warn('[fetchTestsForLesson] Test container not found for lesson:', lessonId)
    return
  }
  
  console.log('[fetchTestsForLesson] Fetching tests for lesson:', lessonId)
  const url = `${base}/api/assignments?lessionID=${encodeURIComponent(lessonId)}`
  console.log('[fetchTestsForLesson] Request URL:', url)
  
  fetch(url)
    .then(r => {
      console.log('[fetchTestsForLesson] Response status:', r.status, 'Content-Type:', r.headers.get('Content-Type'))
      if (!r.ok) {
        return r.text().then(text => {
          console.error('[fetchTestsForLesson] Error response:', text)
          throw new Error(`HTTP ${r.status}: ${text}`)
        })
      }
      return r.text().then(text => {
        console.log('[fetchTestsForLesson] Raw response:', text)
        try {
          return JSON.parse(text)
        } catch (e) {
          console.error('[fetchTestsForLesson] JSON parse error:', e, 'Response text:', text)
          throw e
        }
      })
    })
    .then(list => {
      console.log('[fetchTestsForLesson] Parsed tests:', list)
      if (!Array.isArray(list)) {
        console.error('[fetchTestsForLesson] Response is not an array:', list, 'Type:', typeof list)
        // Try to render even if not an array (might be empty object or error object)
        renderTestsForLesson(testContainer, [])
        return
      }
      renderTestsForLesson(testContainer, list)
    })
    .catch(err => {
      console.error('[fetchTestsForLesson] Error fetching tests:', err)
      // Show empty state even on error
      renderTestsForLesson(testContainer, [])
    })
}

function renderTestsForLesson(container, list) {
  if (!container) {
    console.warn('[renderTestsForLesson] Container is null')
    return
  }
  
  const lessonId = container.getAttribute('data-lesson-id')
  if (!lessonId) {
    console.warn('[renderTestsForLesson] Lesson ID is missing')
    return
  }
  
  console.log('[renderTestsForLesson] Rendering tests for lesson:', lessonId, 'Count:', list ? list.length : 0)
  
  // If no tests, show a button
  if (!Array.isArray(list) || list.length === 0) {
    console.log('[renderTestsForLesson] No tests found, showing empty button')
    container.innerHTML = `
      <div class="test-empty-button" onclick="openTestModal('${lessonId}')">
        <div class="test-button-header">
          <svg class="test-icon" viewBox="0 0 24 24" width="18" height="18" fill="currentColor">
            <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z"/>
          </svg>
          <span class="test-button-text">Bài test</span>
          <span class="test-empty-badge">0</span>
        </div>
      </div>
    `
    container.style.display = 'block'
    return
  }
  
  // When tests exist, show test list and a button
  console.log('[renderTestsForLesson] Found', list.length, 'tests, rendering list')
  container.innerHTML = ''
  
  const testButton = document.createElement('div')
  testButton.className = 'test-button'
  testButton.onclick = () => openTestModal(lessonId)
  testButton.innerHTML = `
    <div class="test-button-header">
      <svg class="test-icon" viewBox="0 0 24 24" width="18" height="18" fill="currentColor">
        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z"/>
      </svg>
      <span class="test-button-text">Bài test</span>
      <span class="test-count-badge">${list.length}</span>
    </div>
  `
  container.appendChild(testButton)
  
  // Display test container
  container.style.display = 'block'
  console.log('[renderTestsForLesson] Test container displayed')
}

function openTestModal(lessonId) {
  const modal = document.getElementById('testModalOverlay')
  if (!modal) {
    // Create modal
    const newModal = document.createElement('div')
    newModal.id = 'testModalOverlay'
    newModal.className = 'test-modal-overlay'
    newModal.innerHTML = `
      <div class="test-modal-container">
        <div class="test-modal-header">
          <h3 class="test-modal-title">Bài test</h3>
          <button class="test-close-btn" onclick="closeTestModal()">×</button>
        </div>
        <div class="test-modal-content" id="testModalContent">
          <div class="test-loading">Đang tải...</div>
        </div>
      </div>
    `
    document.body.appendChild(newModal)
  }
  
  const modalEl = document.getElementById('testModalOverlay')
  modalEl.style.display = 'flex'
  modalEl.style.visibility = 'visible'
  modalEl.style.opacity = '1'
  
  // Load test list
  const content = document.getElementById('testModalContent')
  content.innerHTML = '<div class="test-loading">Đang tải...</div>'
  
  const base = document.body.getAttribute('data-context') || ''
  fetch(`${base}/api/assignments?lessionID=${encodeURIComponent(lessonId)}`)
    .then(r => r.json())
    .then(list => renderTestModalContent(content, list, lessonId))
    .catch(() => {
      content.innerHTML = '<div class="test-error">Lỗi khi tải danh sách test</div>'
    })
}

function closeTestModal() {
  const modal = document.getElementById('testModalOverlay')
  if (modal) {
    modal.style.display = 'none'
    modal.style.visibility = 'hidden'
    modal.style.opacity = '0'
  }
}

function renderTestModalContent(container, list, lessonId) {
  if (!Array.isArray(list) || list.length === 0) {
    container.innerHTML = `
      <div class="test-empty-state">
        <i class="fas fa-inbox"></i>
        <p>Chưa có bài test cho bài học này</p>
      </div>
    `
    return
  }
  
  const base = document.body.getAttribute('data-context') || ''
  let html = '<div class="test-modal-list">'
  
  list.forEach((a, idx) => {
    const title = a.name || `Bài test ${idx + 1}`
    html += `
      <div class="test-modal-item">
        <div class="test-modal-item-info">
          <span class="test-modal-item-number">${idx + 1}</span>
          <div class="test-modal-item-details">
            <h4 class="test-modal-item-title">${title}</h4>
            ${a.description ? `<p class="test-modal-item-desc">${a.description}</p>` : ''}
          </div>
        </div>
        <a class="test-modal-item-btn" href="${base}/learner/test?assignment=${a.assignmentID}">
          Làm bài
        </a>
      </div>
    `
  })
  
  html += '</div>'
  container.innerHTML = html
}

function renderComments(list) {
  const container = document.getElementById('qaCommentsList')
  if (!container) {
    console.warn('[renderComments] Comments container not found')
    return
  }
  
  console.log('[renderComments] Rendering', list ? list.length : 0, 'comments')
  container.innerHTML = ''
  
  if (!list || list.length === 0) {
    container.innerHTML = '<div class="qa-empty-state">Chưa có bình luận nào. Hãy là người đầu tiên bình luận!</div>'
    updateCommentCount()
    return
  }
  
  const byParent = {}
  list.forEach(c => {
    const p = c.parentID || 'root'
    ;(byParent[p] = byParent[p] || []).push(c)
  })
  
  const renderOne = (c, isReply) => {
    const div = document.createElement('div')
    div.className = isReply ? 'qa-comment qa-reply-comment' : 'qa-comment'
    div.setAttribute('data-comment-id', c.commentID)
    div.setAttribute('data-user-id', c.userID)
    const authorId = c.userID
    const avatar = c.avatarUrl && c.avatarUrl.trim() ? c.avatarUrl : getRandomAvatar()
    const authorName = c.authorName && c.authorName.trim() ? c.authorName : 'Người dùng'
    
    // Format time
    let timeText = 'vừa xong'
    if (c.createAt) {
      const commentTime = new Date(parseInt(c.createAt))
      const now = new Date()
      const diffMs = now - commentTime
      const diffMins = Math.floor(diffMs / 60000)
      const diffHours = Math.floor(diffMs / 3600000)
      const diffDays = Math.floor(diffMs / 86400000)
      
      if (diffMins < 1) {
        timeText = 'vừa xong'
      } else if (diffMins < 60) {
        timeText = `${diffMins} phút trước`
      } else if (diffHours < 24) {
        timeText = `${diffHours} giờ trước`
      } else if (diffDays < 7) {
        timeText = `${diffDays} ngày trước`
      } else {
        timeText = commentTime.toLocaleDateString('vi-VN')
      }
    }
    
    div.innerHTML = `
      <div class="qa-comment-avatar" style="background-image: url('${avatar}')"></div>
      <div class="qa-comment-content">
        <div class="qa-comment-header">
          <span class="qa-comment-author">${authorName}</span>
          <span class="qa-comment-time">${timeText}</span>
        </div>
        <div class="qa-comment-text">${(c.content || '').replace(/\n/g, '<br>')}</div>
        <div class="qa-comment-actions">
          <button class="qa-action-btn qa-like-btn" onclick="toggleLike(this)">Thích</button>
          ${renderActionsForComment(authorId)}
        </div>
      </div>`
    return div
  }
  
  // Sort root comments by time (newest first)
  const rootComments = (byParent['root'] || []).sort((a, b) => {
    const timeA = a.createAt ? parseInt(a.createAt) : 0
    const timeB = b.createAt ? parseInt(b.createAt) : 0
    return timeB - timeA // Newest first
  })
  
  rootComments.forEach(c => {
    const el = renderOne(c, false)
    container.appendChild(el)
    const children = (byParent[c.commentID] || []).sort((a, b) => {
      const timeA = a.createAt ? parseInt(a.createAt) : 0
      const timeB = b.createAt ? parseInt(b.createAt) : 0
      return timeA - timeB // Oldest first for replies
    })
    if (children.length) {
      const replies = document.createElement('div')
      replies.className = 'qa-replies'
      children.forEach(rc => replies.appendChild(renderOne(rc, true)))
      const content = el.querySelector('.qa-comment-content') || el
      content.appendChild(replies)
      updateRepliesToggle(el)
    }
  })
  
  updateCommentCount()
}

document.addEventListener("DOMContentLoaded", () => {
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

  // Initialize replies toggle
  updateAllRepliesToggle()

  // Enhance: inject three-dot menu and Report for static example comments
  enhanceExistingActions()

  // Determine current lesson (prefer active, otherwise first), for fetching DB data on first open
  initializeCurrentLesson()
  
  // Load comments for course on page load
  const courseID = window.currentCourseID || null
  if (courseID) {
    console.log('[DOMContentLoaded] Loading comments for course:', courseID)
    fetchCommentsByCourse(courseID)
  }
  
  // Load tests for all lessons (even if not clicked)
  setTimeout(() => {
    const allTestContainers = document.querySelectorAll('.test-list-container')
    console.log('[DOMContentLoaded] Found', allTestContainers.length, 'test containers')
    allTestContainers.forEach(container => {
      const lessonId = container.getAttribute('data-lesson-id')
      if (lessonId) {
        console.log('[DOMContentLoaded] Loading tests for lesson:', lessonId)
        fetchTestsForLesson(lessonId)
      }
    })
  }, 500) // Delay 500ms to ensure DOM is fully loaded
})

// Function to initialize avatars for existing comments
function initializeAvatars() {
  const existingAvatars = document.querySelectorAll('.qa-comment-avatar:not([style*="background-image"])')
  existingAvatars.forEach((avatar) => {
    avatar.style.backgroundImage = `url('${getRandomAvatar()}')`
  })
}

function initializeCurrentLesson() {
  if (currentLessonId) return
  const active = document.querySelector('.lesson-item.active')
  const first = active || document.querySelector('.lesson-item')
  if (first) {
    currentLessonId = first.getAttribute('data-lesson-id') || null
    // Load tests for the first/active lesson
    if (currentLessonId) {
      fetchTestsForLesson(currentLessonId)
    }
  }
}

// Inject usable menu structure for existing .qa-action-menu (pure "..." button)
function enhanceExistingActions() {
  document.querySelectorAll('.qa-comment').forEach((comment) => {
    const actions = comment.querySelector('.qa-comment-actions')
    if (!actions) return
    // Skip if already enhanced
    if (actions.querySelector('.qa-more')) return
    const oldDot = actions.querySelector('.qa-action-menu')
    if (!oldDot) return
    // Build new menu
    const more = document.createElement('div')
    more.className = 'qa-more'
    more.innerHTML = `
      <button class="qa-more-btn" onclick="toggleMoreMenu(this)">...</button>
      <div class="qa-more-menu">
        <button class="qa-more-item" onclick="reportCommentFromMenu(this)">Report</button>
      </div>`
    // Replace old ... button
    oldDot.replaceWith(more)
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
