/* 
 * QuestionManagement.js — Cách 2: gửi x-www-form-urlencoded (không cần MultipartConfig)
 */

(function() {
  'use strict';

  window.choiceCount = 2;
  window.questionCount = 0;
  window.choiceCounts = {};

  function getAssignmentId() {
    const hiddenInput = document.querySelector('input[name="assignmentId"]');
    if (hiddenInput && hiddenInput.value) return hiddenInput.value;
    const mainContainer = document.querySelector('.main-container');
    if (mainContainer && mainContainer.dataset.assignmentId) {
      return mainContainer.dataset.assignmentId;
    }
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('assignment') || '';
  }

  function getContextPath() {
    const mainContainer = document.querySelector('.main-container');
    if (mainContainer && mainContainer.dataset.contextPath) {
      return mainContainer.dataset.contextPath;
    }
    const scripts = document.getElementsByTagName('script');
    for (let script of scripts) {
      if (script.src && script.src.includes('/instructor/js/')) {
        const match = script.src.match(/^(.*?)\/instructor\/js\//);
        if (match) return match[1];
      }
    }
    return '';
  }

  // ==== Toggle form ====
  window.toggleCreateQuestionForm = function() {
    const form = document.getElementById('createQuestionForm');
    const btn = document.getElementById('toggleCreateBtn');
    if (!form) return;
    const isHidden = form.style.display === 'none' || form.style.display === '';
    if (isHidden) {
      form.style.display = 'block';
      if (btn) btn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
      const container = document.getElementById('questionsContainer');
      if (container && container.children.length === 0) setTimeout(() => addQuestion(), 100);
    } else {
      form.style.display = 'none';
      if (btn) btn.innerHTML = '<i class="fas fa-chevron-down"></i> Hiển thị Form';
    }
  };

  // ==== Add/remove choice ====
  window.addChoice = function() {
    window.choiceCount++;
    const c = document.getElementById('choicesContainer');
    if (!c) return;
    const d = document.createElement('div');
    d.className = 'choice-item';
    d.style.cssText = 'display:flex;gap:10px;margin-bottom:10px;align-items:center;';
    d.innerHTML = `
      <input type="text" name="choice${window.choiceCount}" class="form-control" placeholder="Nhập lựa chọn ${window.choiceCount}..." required>
      <label><input type="checkbox" name="isCorrect${window.choiceCount}" value="true"> Đáp án đúng</label>
      <button type="button" class="btn btn-danger btn-sm" onclick="removeChoice(this)"><i class="fas fa-times"></i></button>`;
    c.appendChild(d);
  };

  window.removeChoice = function(btn) {
    const c = document.getElementById('choicesContainer');
    if (!c) return;
    if (c.querySelectorAll('.choice-item').length > 1) btn.closest('.choice-item').remove();
  };

  window.cancelEdit = function() {
    const ctx = getContextPath();
    const a = getAssignmentId();
    window.location.href = ctx + '/ManageQuestion?action=list&assignment=' + a;
  };

  // ==== Add question ====
  window.addQuestion = function() {
    window.questionCount++;
    const qIdx = window.questionCount;
    window.choiceCounts[qIdx] = 2;
    const c = document.getElementById('questionsContainer');
    if (!c) return;

    const q = document.createElement('div');
    q.className = 'question-item';
    q.dataset.questionIndex = qIdx;
    q.innerHTML = `
      <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:10px;">
        <h4><i class="fas fa-question-circle"></i> Câu hỏi ${qIdx}</h4>
        <button type="button" class="btn btn-danger btn-sm" onclick="removeQuestion(this)">
          <i class="fas fa-trash"></i> Xóa câu hỏi
        </button>
      </div>
      <div><label>Nội dung câu hỏi *</label>
        <textarea name="questionContent_${qIdx}" class="form-control" rows="3" required></textarea>
      </div>
      <div style="margin-top:10px;">
        <label>Các lựa chọn trả lời *</label>
        <div id="choicesContainer_${qIdx}">
          ${[1,2].map(n => `
            <div class="choice-item" style="display:flex;gap:10px;margin-bottom:10px;align-items:center;">
              <input type="text" name="choice_${qIdx}_${n}" class="form-control" placeholder="Nhập lựa chọn ${n}..." required>
              <label><input type="radio" name="isCorrect_${qIdx}" value="${n}" required> Đáp án đúng</label>
              <button type="button" class="btn btn-danger btn-sm" onclick="removeChoiceInQuestion(${qIdx}, this)">
                <i class="fas fa-times"></i>
              </button>
            </div>`).join('')}
        </div>
        <button type="button" class="btn btn-secondary btn-sm" onclick="addChoiceInQuestion(${qIdx})">
          <i class="fas fa-plus"></i> Thêm lựa chọn
        </button>
      </div>`;
    c.appendChild(q);
  };

  window.removeQuestion = function(btn) {
    btn.closest('.question-item')?.remove();
  };

  window.addChoiceInQuestion = function(qi) {
    if (!window.choiceCounts[qi]) window.choiceCounts[qi] = 2;
    window.choiceCounts[qi]++;
    const n = window.choiceCounts[qi];
    const c = document.getElementById(`choicesContainer_${qi}`);
    if (!c) return;
    const d = document.createElement('div');
    d.className = 'choice-item';
    d.style.cssText = 'display:flex;gap:10px;margin-bottom:10px;align-items:center;';
    d.innerHTML = `
      <input type="text" name="choice_${qi}_${n}" class="form-control" placeholder="Nhập lựa chọn ${n}..." required>
      <label><input type="radio" name="isCorrect_${qi}" value="${n}" required> Đáp án đúng</label>
      <button type="button" class="btn btn-danger btn-sm" onclick="removeChoiceInQuestion(${qi}, this)">
        <i class="fas fa-times"></i>
      </button>`;
    c.appendChild(d);
  };

  window.removeChoiceInQuestion = function(qi, btn) {
    const c = document.getElementById(`choicesContainer_${qi}`);
    if (!c) return;
    const items = c.querySelectorAll('.choice-item');
    if (items.length > 2) btn.closest('.choice-item').remove();
    else alert('Cần ít nhất 2 lựa chọn!');
  };

  // ==== Submit ====
  window.submitBatchQuestions = async function(event) {
    event.preventDefault();
    const form = event.currentTarget;
    const btn = form.querySelector('button[type="submit"]');
    const orig = btn.innerHTML;
    btn.disabled = true;
    btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang lưu...';

    try {
      const assignmentId = getAssignmentId();
      const questions = document.querySelectorAll('.question-item');
      if (questions.length === 0) {
        alert('Vui lòng thêm ít nhất một câu hỏi!');
        btn.disabled = false;
        btn.innerHTML = orig;
        return;
      }

      const ctx = getContextPath();
      const servletUrl = ctx + '/ManageQuestion?ajax=true';
      let success = 0, fail = 0;

      for (let i = 0; i < questions.length; i++) {
        const qEl = questions[i];
        const qi = qEl.dataset.questionIndex;
        const content = qEl.querySelector(`textarea[name="questionContent_${qi}"]`).value.trim();
        const correct = qEl.querySelector(`input[name="isCorrect_${qi}"]:checked`);
        if (!content || !correct) { fail++; continue; }

        const choices = [];
        qEl.querySelectorAll(`input[name^="choice_${qi}_"]`).forEach((inp, idx) => {
          const val = inp.value.trim();
          if (val) {
            const match = inp.name.match(/choice_\d+_(\d+)/);
            const num = match ? match[1] : idx + 1;
            choices.push({
              content: val,
              isCorrect: num === correct.value
            });
          }
        });

        // build urlencoded body
        const body = new URLSearchParams();
        body.append('action', 'create');
        body.append('assignmentId', assignmentId);
        body.append('assignment', assignmentId);
        body.append('questionContent', content);
        choices.forEach((c, j) => {
          body.append('choice' + (j + 1), c.content);
          if (c.isCorrect) body.append('isCorrect' + (j + 1), 'true');
        });

        try {
          const res = await fetch(servletUrl, {
            method: 'POST',
            headers: {
              'X-Requested-With': 'XMLHttpRequest',
              'Accept': 'application/json, text/plain, */*',
              'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            body: body.toString()
          });
          const txt = await res.text();
          if (res.ok && txt.includes('"success":true')) success++;
          else fail++;
        } catch (err) {
          console.error('Create failed:', err);
          fail++;
        }
      }

      alert(`✅ Tạo thành công ${success} câu hỏi, ${fail} thất bại.`);
      window.location.reload();
    } catch (e) {
      console.error(e);
      alert('Đã xảy ra lỗi khi lưu câu hỏi.');
    } finally {
      btn.disabled = false;
      btn.innerHTML = orig;
    }
  };

  document.addEventListener('DOMContentLoaded', function() {
    const hasQuestion = document.querySelector('input[name="questionId"]');
    const form = document.getElementById('createQuestionForm');
    if (hasQuestion && form) {
      toggleCreateQuestionForm();
      document.getElementById('questionContent')?.focus();
    }
  });
})();
