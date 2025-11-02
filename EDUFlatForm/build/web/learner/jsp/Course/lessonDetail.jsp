<jsp:include page="/learner/common/footer.jsp" />
<!-- Added global theme script and fixed typo in course.js path -->
<script src="${pageContext.request.contextPath}/learner/js/theme.js"></script>
<script src="${pageContext.request.contextPath}/learner/js/course.js"></script>

<!-- Added script to handle room navigation -->
<script>
    function navigateToRoom(courseId) {
        window.location.href = '${pageContext.request.contextPath}/course/room.jsp?id=' + courseId;
    }
</script>
</body>
</html>
