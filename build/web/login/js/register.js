// ===================== ẨN / HIỆN MẬT KHẨU =====================
function togglePasswordVisibility() {
  const input = document.querySelector('input[name="password"]');
  if (!input) return
  input.type = input.type === "password" ? "text" : "password"
}

function toggleConfirmPasswordVisibility() {
  const input = document.querySelector('input[name="confirmPassword"]');
  if (!input) return
  input.type = input.type === "password" ? "text" : "password"
}

// ===================== VALIDATION CƠ BẢN =====================
function validateForm() {
  const username = document.querySelector('input[name="username"]').value.trim();
  const email = document.querySelector('input[name="email"]').value.trim();
  const phone = document.querySelector('input[name="phone"]').value.trim();
  const password = document.querySelector('input[name="password"]').value;
  const confirmPassword = document.querySelector('input[name="confirmPassword"]').value;

  if (!username || username.length < 3) {
    alert("Tên tài khoản phải có ít nhất 3 ký tự");
    return false;
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    alert("Địa chỉ email không hợp lệ");
    return false;
  }

  if (!/^[0-9]{10,11}$/.test(phone)) {
    alert("Số điện thoại phải có 10-11 chữ số");
    return false;
  }

  if (password.length < 6) {
    alert("Mật khẩu phải có ít nhất 6 ký tự");
    return false;
  }

  if (password !== confirmPassword) {
    alert("Mật khẩu xác nhận không khớp");
    return false;
  }

  return true;
}

// ===================== ĐĂNG KÝ VỚI GOOGLE =====================
function handleGoogleSignup() {
  alert("Đang chuyển hướng đến Google để đăng ký...");
  // Sau này: window.location.href = `${contextPath}/GoogleSignupServlet`
}

// ===================== XỬ LÝ ROLE SELECTION =====================
document.addEventListener("DOMContentLoaded", () => {
  // Xử lý visual feedback cho radio buttons role
  const roleInputs = document.querySelectorAll('input[name="role"]');
  roleInputs.forEach(input => {
    input.addEventListener('change', function() {
      // Xóa class active từ tất cả role options
      document.querySelectorAll('.role-option').forEach(option => {
        option.classList.remove('active');
      });
      // Thêm class active cho option được chọn
      if (this.checked) {
        this.closest('.role-option').classList.add('active');
      }
    });
    
    // Khởi tạo active state cho option được chọn mặc định
    if (input.checked) {
      input.closest('.role-option').classList.add('active');
    }
  });

  // Gắn validation cho form
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", (e) => {
      if (!validateForm()) e.preventDefault();
    });
  }
});
