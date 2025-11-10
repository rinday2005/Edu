/* ===================== CAROUSEL ===================== */
let currentSlideIndex = 0;
let autoSlideInterval = null;

function changeSlide(n) {
  showSlide((currentSlideIndex += n));
  resetAutoSlide();
}

function currentSlide(n) {
  showSlide((currentSlideIndex = n));
  resetAutoSlide();
}

function showSlide(n) {
  const slides = document.querySelectorAll(".carousel-slide");
  const dots = document.querySelectorAll(".dot");
  if (!slides.length) return;

  if (n >= slides.length) currentSlideIndex = 0;
  if (n < 0) currentSlideIndex = slides.length - 1;

  slides.forEach((s) => s.classList.remove("active"));
  dots.forEach((d) => d.classList.remove("active"));

  slides[currentSlideIndex].classList.add("active");
  if (dots[currentSlideIndex]) dots[currentSlideIndex].classList.add("active");
}

function resetAutoSlide() {
  if (autoSlideInterval) clearInterval(autoSlideInterval);
  autoSlideInterval = setInterval(() => changeSlide(1), 5000);
}

function initCarousel() {
  showSlide(0);
  resetAutoSlide();
}

/* ===================== THEME TOGGLE ===================== */
function initTheme() {
  const savedTheme = localStorage.getItem("theme") || "dark";
  document.body.classList.toggle("light-mode", savedTheme === "light");
}

function toggleTheme() {
  const isLightMode = document.body.classList.toggle("light-mode");
  localStorage.setItem("theme", isLightMode ? "light" : "dark");
}

/* ===================== SEARCH + SCROLL ===================== */
function initSearchAndScroll() {
  const searchBtn = document.querySelector(".search-btn");
  const searchInput = document.querySelector(".search-bar input");

  if (searchBtn && searchInput) {
    searchBtn.addEventListener("click", () => {
      const query = searchInput.value.trim();
      if (query) console.log("Searching for:", query);
    });
  }

  // Smooth scroll for anchor links
  document.querySelectorAll('a[href^="#"]').forEach((anchor) => {
    anchor.addEventListener("click", function (e) {
      const href = this.getAttribute("href");
      if (href && href !== "#") {
        e.preventDefault();
        const target = document.querySelector(href);
        if (target) target.scrollIntoView({ behavior: "smooth" });
      }
    });
  });
}

/* ===================== AUTHENTICATION MODAL ===================== */
function closeAuthModal() {
  const modal = document.getElementById("authModal");
  if (modal) modal.style.display = "none";
}

function toggleAuthForm() {
  const form = document.getElementById("authForm");
  if (form) form.style.display = form.style.display === "none" ? "block" : "none";
}

function handleFormSubmit() {
  console.log("[v0] Form submitted");
}

function handleGoogleLogin() {
  console.log("[v0] Google login clicked");
  alert("Google login feature coming soon!");
}

function validateUsername() {
  const input = document.getElementById("usernameInput");
  const error = document.getElementById("usernameError");
  if (input.value.length < 3) {
    error.textContent = "Username phải có ít nhất 3 ký tự";
    return false;
  }
  error.textContent = "";
  return true;
}

function validateEmail() {
  const input = document.getElementById("emailInput");
  const error = document.getElementById("emailError");
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(input.value) && !input.value.match(/^[0-9]{10}$/)) {
    error.textContent = "Email hoặc số điện thoại không hợp lệ";
    return false;
  }
  error.textContent = "";
  return true;
}

function validatePassword() {
  const input = document.getElementById("passwordInput");
  const error = document.getElementById("passwordError");
  if (input.value.length < 6) {
    error.textContent = "Mật khẩu phải có ít nhất 6 ký tự";
    return false;
  }
  error.textContent = "";
  return true;
}

function validateConfirmPassword() {
  const newPass = document.getElementById("passwordInput");
  const confirmPass = document.getElementById("confirmPasswordInput");
  const error = document.getElementById("confirmPasswordError");
  if (newPass.value !== confirmPass.value) {
    error.textContent = "Mật khẩu xác nhận không khớp";
    return false;
  }
  error.textContent = "";
  return true;
}

function togglePasswordVisibility() {
  const input = document.getElementById("passwordInput");
  input.type = input.type === "password" ? "text" : "password";
}

function toggleConfirmPasswordVisibility() {
  const input = document.getElementById("confirmPasswordInput");
  input.type = input.type === "password" ? "text" : "password";
}

function toggleAuthMode(event) {
  event.preventDefault();
  const toggleText = document.getElementById("toggleText");
  const confirmGroup = document.getElementById("confirmPasswordGroup");
  const rememberGroup = document.getElementById("rememberMeGroup");

  confirmGroup.style.display = confirmGroup.style.display === "none" ? "block" : "none";
  rememberGroup.style.display = rememberGroup.style.display === "none" ? "block" : "none";

  toggleText.textContent = toggleText.textContent.includes("chưa có")
    ? "Bạn đã có tài khoản? Đăng nhập"
    : "Bạn chưa có tài khoản? Đăng kí";
}

function handleForgotPassword(event) {
  event.preventDefault();
  alert("Vui lòng kiểm tra email để lấy lại mật khẩu!");
}

/* ===================== NAVIGATION ===================== */
function navigateTo(page) {
  window.location.href = page;
}

/* ===================== USER MENU ===================== */
function toggleUserMenu() {
  const menu = document.getElementById("dropdownMenu");
  if (menu) menu.classList.toggle("show");
}

window.addEventListener("click", (event) => {
  const menu = document.getElementById("dropdownMenu");
  const avatar = document.querySelector(".user-avatar");
  if (menu && avatar && !menu.contains(event.target) && !avatar.contains(event.target)) {
    menu.classList.remove("show");
  }
});

/* ===================== VIEW ALL COURSES ===================== */
// Ensure all courses are displayed by default
function initViewAllCourses() {
  const coursesGrid = document.getElementById("coursesGrid");
  
  if (!coursesGrid) {
    console.log("ViewAllCourses: Courses grid not found, skipping");
    return;
  }

  console.log("ViewAllCourses: Initializing - showing all courses...");

  const allCourses = coursesGrid.querySelectorAll(".pro-course-card");
  console.log("ViewAllCourses: Found", allCourses.length, "courses in DOM");
  
  // Log all course names for debugging
  allCourses.forEach((course, index) => {
    const courseName = course.querySelector("h3")?.textContent || "Unknown";
    console.log("ViewAllCourses: Course", index + 1, "-", courseName);
  });

  // Show all courses by default - remove any hidden classes and ensure they are visible
  allCourses.forEach((course, index) => {
    // Remove hidden class
    course.classList.remove("course-hidden");
    
    // Force display style to ensure visibility
    course.style.setProperty("display", "flex", "important");
    course.style.setProperty("visibility", "visible", "important");
    course.style.setProperty("opacity", "1", "important");
    course.style.setProperty("height", "auto", "important");
    
    // Remove any inline styles that might hide the course
    course.style.removeProperty("max-height");
    course.style.removeProperty("min-height");
    
    console.log("ViewAllCourses: Showing course", index + 1, "-", course.querySelector("h3")?.textContent || "Unknown");
  });

  console.log("ViewAllCourses: All", allCourses.length, "courses are now visible");
}


/* ===================== INIT ===================== */
// Try to initialize immediately (if DOM is loaded)
if (document.readyState === 'loading') {
  document.addEventListener("DOMContentLoaded", () => {
    initTheme();
    initCarousel();
    initSearchAndScroll();
    initViewAllCourses();
    
    // Highlight active nav item
    const navItems = document.querySelectorAll(".nav-item");
    const currentPage = window.location.pathname.split("/").pop() || "home.jsp";
    navItems.forEach((item) => {
      item.classList.remove("active");
      const onClick = item.getAttribute("onclick") || "";
      if (onClick.includes(currentPage)) item.classList.add("active");
    });
  });
} else {
  // DOM already loaded, execute immediately
  initTheme();
  initCarousel();
  initSearchAndScroll();
  initViewAllCourses();
  
  // Highlight active nav item
  const navItems = document.querySelectorAll(".nav-item");
  const currentPage = window.location.pathname.split("/").pop() || "home.jsp";
  navItems.forEach((item) => {
    item.classList.remove("active");
    const onClick = item.getAttribute("onclick") || "";
    if (onClick.includes(currentPage)) item.classList.add("active");
  });
}

/* ===================== MODAL & KEY EVENTS ===================== */
window.onclick = (event) => {
  const modal = document.getElementById("authModal");
  if (event.target === modal) closeAuthModal();
};

document.addEventListener("keydown", (e) => {
  const modal = document.getElementById("authModal");
  if (!modal) return;

  if (modal.style.display === "block") {
    if (e.key === "Escape") closeAuthModal();
    if (e.key === "Enter") {
      const active = document.activeElement;
      const isTyping = active && (active.tagName === "INPUT" || active.tagName === "TEXTAREA");
      if (isTyping) handleFormSubmit();
    }
  }
});

/* ===================== HELPERS ===================== */
function showGroup(el) {
  if (el) el.style.display = "block";
}

function hideGroup(el) {
  if (el) el.style.display = "none";
}
