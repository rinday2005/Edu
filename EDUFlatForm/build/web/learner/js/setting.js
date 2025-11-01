// Avatar upload functionality
document.getElementById("avatar-upload").addEventListener("change", (e) => {
  const file = e.target.files[0]
  if (file) {
    const reader = new FileReader()
    reader.onload = (e) => {
      document.getElementById("avatar-preview").src = e.target.result
    }
    reader.readAsDataURL(file)
  }
})

// Password strength checker
document.getElementById("newPassword").addEventListener("input", (e) => {
  const password = e.target.value
  const strengthBar = document.querySelector(".strength-fill")
  const strengthText = document.querySelector(".strength-text")

  let strength = 0
  if (password.length >= 8) strength++
  if (/[A-Z]/.test(password)) strength++
  if (/[a-z]/.test(password)) strength++
  if (/[0-9]/.test(password)) strength++
  if (/[^A-Za-z0-9]/.test(password)) strength++

  const strengthLevels = ["Mật khẩu yếu", "Mật khẩu trung bình", "Mật khẩu tốt", "Mật khẩu rất tốt", "Mật khẩu mạnh"]
  const colors = ["#ef4444", "#f59e0b", "#eab308", "#22c55e", "#10b981"]

  strengthBar.style.width = `${(strength / 5) * 100}%`
  strengthBar.style.backgroundColor = colors[strength - 1] || "#ef4444"
  strengthText.textContent = strengthLevels[strength - 1] || "Mật khẩu yếu"
})

// Toggle password visibility
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

// Form submission handlers
document.getElementById("profile-form").addEventListener("submit", (e) => {
  e.preventDefault()
  alert("Thông tin cá nhân đã được cập nhật thành công!")
})

document.getElementById("password-form").addEventListener("submit", (e) => {
  e.preventDefault()
  const newPassword = document.getElementById("newPassword").value
  const confirmPassword = document.getElementById("confirmPassword").value

  if (newPassword !== confirmPassword) {
    alert("Mật khẩu xác nhận không khớp!")
    return
  }

  alert("Mật khẩu đã được thay đổi thành công!")
  resetPasswordForm()
})

// Reset forms
function resetForm() {
  document.getElementById("profile-form").reset()
}

function resetPasswordForm() {
  document.getElementById("password-form").reset()
  document.querySelector(".strength-fill").style.width = "0%"
  document.querySelector(".strength-text").textContent = "Mật khẩu yếu"
}

// Delete account
function deleteAccount() {
  if (confirm("Bạn có chắc chắn muốn xóa tài khoản? Hành động này không thể hoàn tác!")) {
    alert("Tài khoản sẽ được xóa trong 7 ngày. Bạn có thể hủy bỏ trong thời gian này.")
  }
}
