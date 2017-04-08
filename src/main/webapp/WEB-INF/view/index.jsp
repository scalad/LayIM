<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>LayIM即时通讯</title>
<link rel="stylesheet" href="/static/layui/css/layui.css">
<style>
html{background-color: #333;}
</style>
</head>
<body>
<script src="/static/layui/layui.js"></script>
<script src="/static/js/websocket.js"></script>
<script>
function getUid() {return ${user.id};}
</script>
</body>
</html>
