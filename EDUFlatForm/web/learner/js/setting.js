// ==================== 📸 Avatar Upload ====================
document.getElementById("avatar-upload").addEventListener("change", (e) => {
  const file = e.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      document.getElementById("avatar-preview").src = e.target.result;
    };
    reader.readAsDataURL(file);
  }
});

// ==================== 🔐 Password Strength Checker ====================
document.getElementById("newPassword").addEventListener("input", (e) => {
  const password = e.target.value;
  const strengthBar = document.querySelector(".strength-fill");
  const strengthText = document.querySelector(".strength-text");

  let strength = 0;
  if (password.length >= 8) strength++;
  if (/[A-Z]/.test(password)) strength++;
  if (/[a-z]/.test(password)) strength++;
  if (/[0-9]/.test(password)) strength++;
  if (/[^A-Za-z0-9]/.test(password)) strength++;

  const strengthLevels = [
    "Mật khẩu yếu",
    "Mật khẩu trung bình",
    "Mật khẩu tốt",
    "Mật khẩu rất tốt",
    "Mật khẩu mạnh",
  ];
  const colors = ["#ef4444", "#f59e0b", "#eab308", "#22c55e", "#10b981"];

  strengthBar.style.width = `${(strength / 5) * 100}%`;
  strengthBar.style.backgroundColor = colors[strength - 1] || "#ef4444";
  strengthText.textContent = strengthLevels[strength - 1] || "Mật khẩu yếu";
});

// ==================== 👁 Toggle Password Visibility ====================
function togglePassword(inputId) {
  const input = document.getElementById(inputId);
  const button = input.nextElementSibling;
  const icon = button.querySelector("i");

  if (input.type === "password") {
    input.type = "text";
    icon.classList.remove("fa-eye");
    icon.classList.add("fa-eye-slash");
  } else {
    input.type = "password";
    icon.classList.remove("fa-eye-slash");
    icon.classList.add("fa-eye");
  }
}

// ==================== 🧾 Profile Update ====================
// ⚠️ ĐÃ BỎ e.preventDefault() để servlet nhận form submit
document.getElementById("profile-form").addEventListener("submit", () => {
  alert("Đang cập nhật thông tin cá nhân...");
  // Không chặn form, để submit thật về servlet
});

// ==================== 🔑 Password Update ====================
// ⚠️ Giữ kiểm tra xác nhận nhưng KHÔNG chặn submit nếu hợp lệ
document.getElementById("password-form").addEventListener("submit", (e) => {
  const newPassword = document.getElementById("newPassword").value;
  const confirmPassword = document.getElementById("confirmPassword").value;

  if (newPassword !== confirmPassword) {
    e.preventDefault(); // chỉ chặn khi có lỗi
    alert("❌ Mật khẩu xác nhận không khớp!");
    return;
  }

  alert("🔄 Đang thay đổi mật khẩu...");
  // Cho phép form gửi về servlet (không preventDefault)
});

// ==================== 🔁 Reset Helpers ====================
function resetForm() {
  document.getElementById("profile-form").reset();
}

function resetPasswordForm() {
  document.getElementById("password-form").reset();
  document.querySelector(".strength-fill").style.width = "0%";
  document.querySelector(".strength-text").textContent = "Mật khẩu yếu";
}

// ==================== 🗑 Delete Account (Optional Confirm) ====================
function deleteAccount() {
  if (
    confirm("Bạn có chắc chắn muốn xóa tài khoản? Hành động này không thể hoàn tác!")
  ) {
    alert(
      "Tài khoản sẽ được đánh dấu xóa trong 7 ngày. Bạn có thể hủy bỏ trong thời gian này."
    );
  }
}
