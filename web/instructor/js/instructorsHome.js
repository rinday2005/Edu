// instructorsHome.js - Vanilla JavaScript for JSP pages (NO ES6 modules)
// Note: Chart.js should be loaded from CDN, not ES6 imports

document.addEventListener("DOMContentLoaded", () => {
  console.log("Instructor Home scripts loaded.")

  // 1. Handle Active state for Sidebar
  const navItems = document.querySelectorAll(".sidebar .nav-item")
  const currentPath = window.location.pathname

  navItems.forEach((item) => {
    const itemHref = item.getAttribute("href")

    // Mark as active if path matches
    if (
      currentPath.endsWith(itemHref) ||
      currentPath.includes(itemHref) ||
      currentPath.includes(itemHref.split("/").pop())
    ) {
      navItems.forEach((i) => i.classList.remove("active"))
      item.classList.add("active")
    }
  })

  // 2. Simple search
  const searchButton = document.querySelector(".search-bar button")
  const searchInput = document.querySelector(".search-bar input")

  if (searchButton && searchInput) {
    searchButton.addEventListener("click", () => {
      const query = searchInput.value.trim()
      if (query) {
        console.log(`Searching for: ${query}`)
      }
    })

    searchInput.addEventListener("keypress", (e) => {
      if (e.key === "Enter") {
        searchButton.click()
      }
    })
  }

  // 3. Card hover effects
  const statCards = document.querySelectorAll(".stat-card")

  statCards.forEach((card) => {
    card.addEventListener("mouseenter", function () {
      this.style.transform = "translateY(-2px)"
      this.style.boxShadow = "0 4px 8px rgba(0,0,0,0.15)"
    })

    card.addEventListener("mouseleave", function () {
      this.style.transform = "translateY(0)"
      this.style.boxShadow = "0 2px 4px rgba(0,0,0,0.1)"
    })
  })

  // 4. Button hover effects
  const buttons = document.querySelectorAll(".btn")

  buttons.forEach((button) => {
    button.addEventListener("mouseenter", function () {
      this.style.transform = "translateY(-1px)"
    })

    button.addEventListener("mouseleave", function () {
      this.style.transform = "translateY(0)"
    })
  })

  // 5. Form validation
  const forms = document.querySelectorAll("form")

  forms.forEach((form) => {
    form.addEventListener("submit", (e) => {
      const requiredFields = form.querySelectorAll("[required]")
      let isValid = true

      requiredFields.forEach((field) => {
        if (!field.value.trim()) {
          field.style.borderColor = "#dc3545"
          isValid = false
        } else {
          field.style.borderColor = ""
        }
      })

      if (!isValid) {
        e.preventDefault()
        alert("Please fill in all required fields")
      }
    })
  })

  // 6. Close modals when clicking outside
  window.addEventListener("click", (event) => {
    const courseModal = document.getElementById("courseFormModal")
    const sessionModal = document.getElementById("sessionFormModal")
    const articleModal = document.getElementById("articleFormModal")
    const detailsModal = document.getElementById("courseDetailsModal")
    const articleDetailsModal = document.getElementById("articleDetailsModal")

    if (event.target === courseModal) {
      hideCourseForm()
    }
    if (event.target === sessionModal) {
      hideSessionForm()
    }
    if (event.target === articleModal) {
      hideArticleForm()
    }
    if (event.target === detailsModal) {
      hideCourseDetails()
    }
    if (event.target === articleDetailsModal) {
      hideArticleDetails()
    }
  })

  // 7. Header dropdown menu
  const userProfileToggle = document.getElementById("userProfileToggle")
  const profileDropdown = document.getElementById("profileDropdown")

  if (userProfileToggle && profileDropdown) {
    userProfileToggle.addEventListener("click", (e) => {
      e.stopPropagation()
      profileDropdown.style.display = profileDropdown.style.display === "block" ? "none" : "block"
    })

    document.addEventListener("click", (e) => {
      if (e.target !== userProfileToggle && !userProfileToggle.contains(e.target)) {
        profileDropdown.style.display = "none"
      }
    })
  }
})

// ===================================
// COURSE MANAGEMENT FUNCTIONS
// ===================================
function showCourseForm() {
  document.getElementById("courseFormModal").style.display = "block"
}

function hideCourseForm() {
  document.getElementById("courseFormModal").style.display = "none"
  const form = document.getElementById("createCourseForm")
  if (form) {
    form.reset()
  }
}

function viewCourseDetails(courseName, courseLevel, coursePrice, courseStatus, courseDescription) {
  document.getElementById("detailCourseName").textContent = courseName
  document.getElementById("detailCourseLevel").textContent = courseLevel
  document.getElementById("detailCoursePrice").textContent = "$" + coursePrice
  document.getElementById("detailCourseDescription").textContent = courseDescription

  const statusBadge =
    courseStatus === "Published"
      ? '<span class="status-badge status-published">Published</span>'
      : '<span class="status-badge status-pending">Pending Review</span>'
  document.getElementById("detailCourseStatus").innerHTML = statusBadge

  document.getElementById("courseDetailsModal").style.display = "block"
}

function hideCourseDetails() {
  document.getElementById("courseDetailsModal").style.display = "none"
}

function deleteCourse(courseId) {
  if (confirm("Are you sure you want to delete this course? This action cannot be undone.")) {
    console.log("[v0] Deleting course:", courseId)
  }
}

function toggleCourse(header) {
  const courseItem = header.closest(".course-item")
  const content = courseItem.querySelector(".course-content")
  const toggle = header.querySelector(".course-toggle i")

  content.classList.toggle("expanded")
  toggle.classList.toggle("fa-chevron-right")
  toggle.classList.toggle("fa-chevron-down")
}

function showSessionForm(btn) {
  document.getElementById("sessionFormModal").style.display = "block"
  document.getElementById("sessionFormTitle").innerHTML = '<i class="fas fa-plus"></i> Add New Chapter'
  document.getElementById("sessionForm").reset()
  btn.dataset.action = "add"
}

function hideSessionForm() {
  document.getElementById("sessionFormModal").style.display = "none"
}

function editSession(btn) {
  const sessionItem = btn.closest(".session-item")
  const sessionInfo = sessionItem.querySelector(".session-info")
  const title = sessionInfo.querySelector("h5").textContent
  const duration = sessionInfo.querySelector("p:nth-of-type(1)").textContent.match(/\d+/)[0]
  const lessons = sessionInfo.querySelector("p:nth-of-type(2)").textContent.match(/\d+/)[0]

  document.getElementById("sessionFormTitle").innerHTML = '<i class="fas fa-edit"></i> Edit Chapter'
  document.getElementById("sessionName").value = title.replace(/Chapter \d+: /, "")
  document.getElementById("sessionDuration").value = duration
  document.getElementById("sessionLessons").value = lessons

  document.getElementById("sessionFormModal").style.display = "block"
  btn.dataset.action = "edit"
}

function deleteSession(btn) {
  if (confirm("Are you sure you want to delete this chapter?")) {
    btn.closest(".session-item").remove()
  }
}

// ===================================
// ARTICLE MANAGEMENT FUNCTIONS
// ===================================
function showArticleForm() {
  document.getElementById("articleFormModal").style.display = "block"
}

function hideArticleForm() {
  document.getElementById("articleFormModal").style.display = "none"
}

function viewArticleDetails(articleID, title, content, createAt, status) {
  document.getElementById("detailTitle").textContent = title
  document.getElementById("detailDate").textContent = createAt
  document.getElementById("detailContent").textContent = content

  const statusBadge =
    status === "Published"
      ? '<span class="status-badge status-published">Published</span>'
      : status === "Draft"
        ? '<span class="status-badge status-draft">Draft</span>'
        : '<span class="status-badge status-pending">Pending</span>'
  document.getElementById("detailStatus").innerHTML = statusBadge

  document.getElementById("articleDetailsModal").style.display = "block"
}

function hideArticleDetails() {
  document.getElementById("articleDetailsModal").style.display = "none"
}

// ===================================
// SETTINGS MANAGEMENT FUNCTIONS
// ===================================
function togglePassword(inputId) {
  const input = document.getElementById(inputId)
  const button = input.nextElementSibling
  const icon = button.querySelector("i")

  if (input.type === "password") {
    input.type = "text"
    icon.classList.remove("fa-eye")
    icon.classList.add("fa-eye-slash")
  } else {
    input.type = "password"
    icon.classList.remove("fa-eye-slash")
    icon.classList.add("fa-eye")
  }
}

function resetPasswordForm() {
  const passwordForm = document.getElementById("password-form")
  if (passwordForm) {
    passwordForm.reset()
  }
  const strengthFill = document.querySelector(".strength-fill")
  if (strengthFill) strengthFill.style.width = "0%"
  const strengthText = document.querySelector(".strength-text")
  if (strengthText) strengthText.textContent = "Weak password"
}

function deleteAccount() {
  if (confirm("Are you sure you want to delete your account? This action cannot be undone!")) {
    alert("Your account will be deleted in 7 days. You can cancel during this time.")
  }
}

function navigateToSettings() {
  const contextPath = document.querySelector("[data-context-path]")?.getAttribute("data-context-path") || ""
  window.location.href = contextPath + "/instructor/jsp/Setting.jsp"
}

// ===================================
// CHARTS (Chart.js from CDN)
// ===================================
document.addEventListener("DOMContentLoaded", () => {
  // Check if Chart.js is loaded from CDN - do not use ES6 imports
  if (typeof Chart === "undefined") {
    console.warn("Chart.js not loaded from CDN, skipping chart initialization")
    return
  }

  // Revenue Chart (Bar Chart)
  const revenueCtx = document.getElementById("revenueChart")
  if (revenueCtx) {
    new Chart(revenueCtx, {
      type: "bar",
      data: {
        labels: ["May", "June", "July", "August", "September", "October"],
        datasets: [
          {
            label: "Revenue (USD)",
            data: [4500, 3200, 5800, 4100, 6900, 7500],
            backgroundColor: "rgba(0, 123, 255, 0.7)",
            borderColor: "rgba(0, 123, 255, 1)",
            borderWidth: 1,
            borderRadius: 5,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
        plugins: {
          legend: { display: false },
          title: { display: false },
        },
      },
    })
  }

  // Enrollment Chart (Line Chart)
  const enrollmentCtx = document.getElementById("enrollmentChart")
  if (enrollmentCtx) {
    new Chart(enrollmentCtx, {
      type: "line",
      data: {
        labels: ["May", "June", "July", "August", "September", "October"],
        datasets: [
          {
            label: "Enrollment Count",
            data: [150, 120, 210, 180, 250, 290],
            backgroundColor: "rgba(40, 167, 69, 0.7)",
            borderColor: "rgba(40, 167, 69, 1)",
            borderWidth: 2,
            tension: 0.3,
            fill: true,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
        plugins: {
          legend: { display: false },
          title: { display: false },
        },
      },
    })
  }
})


document.addEventListener('DOMContentLoaded', function() {
    
    const cancelBtn = document.getElementById('cancelWithdrawBtn');
    const form = document.querySelector('.withdrawal-form-enhanced');
    
    console.log('Nút Hủy:', cancelBtn);
    console.log('Form:', form);
    
    if (cancelBtn && form) {
        cancelBtn.addEventListener('click', function(e) {
            console.log('Nút Hủy được click!');
            e.preventDefault();
            
            // Xóa từng trường
            document.getElementById('withdrawAmount').value = '';
            document.getElementById('bankAccount').value = '';
            document.getElementById('accountHolder').value = '';
            document.getElementById('bankName').selectedIndex = 0;
            
            console.log('Đã xóa xong!');
        });
    } else {
        console.log('KHÔNG tìm thấy nút hoặc form!');
    }
});


