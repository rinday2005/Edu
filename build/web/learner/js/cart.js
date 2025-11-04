// Cart Page JavaScript

// Initialize theme
function initTheme() {
  const savedTheme = localStorage.getItem("theme") || "dark";
  document.body.classList.toggle("light-mode", savedTheme === "light");
}

function toggleTheme() {
  const isLightMode = document.body.classList.toggle("light-mode");
  const theme = isLightMode ? "light" : "dark";
  localStorage.setItem("theme", theme);
}

// Remove item from cart
function removeFromCart(cartId) {
  if (confirm("Bạn có chắc chắn muốn xóa khóa học này khỏi giỏ hàng?")) {
    // Show loading state
    const cartItem = document.querySelector(`[data-cart-id="${cartId}"]`);
    if (cartItem) {
      cartItem.style.opacity = "0.5";
      cartItem.style.pointerEvents = "none";
    }
    
    // Simulate API call
    setTimeout(() => {
      // Remove from DOM
      if (cartItem) {
        cartItem.remove();
      }
      
      // Update cart summary
      updateCartSummary();
      
      // Show success message
      showNotification("Đã xóa khóa học khỏi giỏ hàng!", "success");
      
      // Check if cart is empty
      checkEmptyCart();
    }, 1000);
  }
}

// View course detail
function viewCourseDetail(courseId) {
  // Dùng đường dẫn tương đối từ root context
  window.location.href = `/EDUFlatForm/CourseServletController?action=detail&id=${courseId}`;
}

// Apply discount code
function applyDiscount() {
  const discountCode = document.getElementById("discount-code").value.trim();
  
  if (!discountCode) {
    showNotification("Vui lòng nhập mã giảm giá!", "warning");
    return;
  }
  
  // Show loading state
  const applyBtn = document.querySelector(".btn-apply-discount");
  const originalText = applyBtn.innerHTML;
  applyBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang áp dụng...';
  applyBtn.disabled = true;
  
  // Simulate API call
  setTimeout(() => {
    // Mock discount codes
    const validCodes = {
      "WELCOME10": 10,
      "STUDENT20": 20,
      "SAVE50": 50
    };
    
    if (validCodes[discountCode.toUpperCase()]) {
      const discountPercent = validCodes[discountCode.toUpperCase()];
      applyDiscountToTotal(discountPercent);
      showNotification(`Áp dụng mã giảm giá thành công! Giảm ${discountPercent}%`, "success");
      
      // Disable input and button
      document.getElementById("discount-code").disabled = true;
      applyBtn.innerHTML = '<i class="fas fa-check"></i> Đã áp dụng';
      applyBtn.style.background = "#28a745";
    } else {
      showNotification("Mã giảm giá không hợp lệ!", "error");
      applyBtn.innerHTML = originalText;
      applyBtn.disabled = false;
    }
  }, 1500);
}

// Apply discount to total
function applyDiscountToTotal(discountPercent) {
  const totalAmountElement = document.querySelector(".total-amount");
  const finalAmountElement = document.querySelector(".final-amount");
  
  if (totalAmountElement && finalAmountElement) {
    const originalAmount = parseFloat(totalAmountElement.textContent.replace(/[^\d]/g, ""));
    const discountAmount = (originalAmount * discountPercent) / 100;
    const finalAmount = originalAmount - discountAmount;
    
    // Update final amount
    finalAmountElement.textContent = formatCurrency(finalAmount);
    
    // Add discount info
    const discountInfo = document.createElement("div");
    discountInfo.className = "summary-row discount-info";
    discountInfo.innerHTML = `
      <span>Giảm giá (${discountPercent}%):</span>
      <span class="summary-value discount-amount">-${formatCurrency(discountAmount)}</span>
    `;
    
    // Insert before final total
    const finalTotalRow = document.querySelector(".final-total");
    finalTotalRow.parentNode.insertBefore(discountInfo, finalTotalRow);
  }
}

// Proceed to checkout
function proceedToCheckout() {
  const cartItems = document.querySelectorAll(".cart-item");

  if (cartItems.length === 0) {
    showNotification("Giỏ hàng của bạn đang trống!", "warning");
    return;
  }
  const checkoutBtn = document.querySelector(".btn-checkout");
  checkoutBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
  checkoutBtn.disabled = true;

  // Gửi dữ liệu sang servlet
const servletURL = `${window.location.origin}/EDUFlatForm/QRServlet`;





  setTimeout(() => {
    window.location.href = servletURL;
  }, 1000);
}



// Continue shopping
function continueShopping() {
  window.location.href = `${window.location.origin}/EDUFlatForm/CourseServletController`;
}

// Update cart summary
function updateCartSummary() {
  const cartItems = document.querySelectorAll(".cart-item");
  const totalItems = cartItems.length;
  
  // Update item count
  const itemCountElement = document.querySelector(".cart-summary strong");
  if (itemCountElement) {
    itemCountElement.textContent = totalItems;
  }
  
  // Update total amount
  let totalAmount = 0;
  cartItems.forEach(item => {
    const priceElement = item.querySelector(".price-amount");
    if (priceElement) {
      const price = parseFloat(priceElement.textContent.replace(/[^\d]/g, ""));
      totalAmount += price;
    }
  });
  
  const totalAmountElement = document.querySelector(".total-amount");
  const finalAmountElement = document.querySelector(".final-amount");
  
  if (totalAmountElement) {
    totalAmountElement.textContent = formatCurrency(totalAmount);
  }
  
  if (finalAmountElement) {
    finalAmountElement.textContent = formatCurrency(totalAmount);
  }
  
  // Update summary text
  const summaryText = document.querySelector(".cart-summary");
  if (summaryText) {
    if (totalItems > 0) {
      summaryText.innerHTML = `Bạn có <strong>${totalItems}</strong> khóa học trong giỏ hàng.`;
    } else {
      summaryText.textContent = "Giỏ hàng của bạn đang trống.";
    }
  }
}

// Check if cart is empty
function checkEmptyCart() {
  const cartItems = document.querySelectorAll(".cart-item");
  
  if (cartItems.length === 0) {
    // Show empty cart message
    const cartContent = document.querySelector(".cart-content");
    cartContent.innerHTML = `
      <div class="empty-cart">
        <div class="empty-cart-icon">
          <i class="fas fa-shopping-cart"></i>
        </div>
        <h2>Giỏ hàng của bạn đang trống</h2>
        <p>Hãy khám phá các khóa học thú vị và thêm vào giỏ hàng!</p>
        <button class="btn-continue-shopping" onclick="continueShopping()">
          <i class="fas fa-arrow-left"></i> Tiếp tục học
        </button>
      </div>
    `;
  }
}

// Format currency
function formatCurrency(amount) {
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND'
  }).format(amount);
}

// Show notification
function showNotification(message, type = "info") {
  // Remove existing notifications
  const existingNotifications = document.querySelectorAll(".notification");
  existingNotifications.forEach(notification => notification.remove());
  
  // Create notification element
  const notification = document.createElement("div");
  notification.className = `notification notification-${type}`;
  notification.innerHTML = `
    <div class="notification-content">
      <i class="fas fa-${getNotificationIcon(type)}"></i>
      <span>${message}</span>
      <button class="notification-close" onclick="this.parentElement.parentElement.remove()">
        <i class="fas fa-times"></i>
      </button>
    </div>
  `;
  
  // Add styles
  notification.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    z-index: 10000;
    background: ${getNotificationColor(type)};
    color: white;
    padding: 1rem 1.5rem;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.15);
    animation: slideInRight 0.3s ease;
    max-width: 400px;
  `;
  
  // Add to page
  document.body.appendChild(notification);
  
  // Auto remove after 5 seconds
  setTimeout(() => {
    if (notification.parentNode) {
      notification.style.animation = "slideOutRight 0.3s ease";
      setTimeout(() => notification.remove(), 300);
    }
  }, 5000);
}

// Get notification icon
function getNotificationIcon(type) {
  const icons = {
    success: "check-circle",
    error: "exclamation-circle",
    warning: "exclamation-triangle",
    info: "info-circle"
  };
  return icons[type] || "info-circle";
}

// Get notification color
function getNotificationColor(type) {
  const colors = {
    success: "#28a745",
    error: "#dc3545",
    warning: "#ffc107",
    info: "#17a2b8"
  };
  return colors[type] || "#17a2b8";
}

// Add CSS animations
const style = document.createElement("style");
style.textContent = `
  @keyframes slideInRight {
    from {
      transform: translateX(100%);
      opacity: 0;
    }
    to {
      transform: translateX(0);
      opacity: 1;
    }
  }
  
  @keyframes slideOutRight {
    from {
      transform: translateX(0);
      opacity: 1;
    }
    to {
      transform: translateX(100%);
      opacity: 0;
    }
  }
  
  .notification-content {
    display: flex;
    align-items: center;
    gap: 0.75rem;
  }
  
  .notification-close {
    background: none;
    border: none;
    color: white;
    cursor: pointer;
    padding: 0;
    margin-left: auto;
  }
  
  .notification-close:hover {
    opacity: 0.8;
  }
`;
document.head.appendChild(style);

// Initialize when DOM is loaded
document.addEventListener("DOMContentLoaded", () => {
  initTheme();
  
  // Add hover effects to cart items
  const cartItems = document.querySelectorAll(".cart-item");
  cartItems.forEach(item => {
    item.addEventListener("mouseenter", function() {
      this.style.transform = "translateY(-2px)";
    });
    
    item.addEventListener("mouseleave", function() {
      this.style.transform = "translateY(0)";
    });
  });
  
  // Add click effect to buttons
  const buttons = document.querySelectorAll("button");
  buttons.forEach(button => {
    button.addEventListener("click", function() {
      this.style.transform = "scale(0.95)";
      setTimeout(() => {
        this.style.transform = "scale(1)";
      }, 150);
    });
  });
  
  // Initialize cart summary
  updateCartSummary();
});
