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
body{overflow:hidden;background-color:#39c}#particles-js{position:absolute;width:100%;height:100%;background-color:#161617;background-image:url(/static/themes/bg001.jpg);background-repeat:no-repeat;background-size:cover;background-position:50%}
</style>
</head>
<body>
<div id="particles-js"></div>
<script src="/static/layui/layui.js"></script>
<script src="/static/js/websocket.js"></script>
<script>
function getUid() {return ${user.id};}
</script>
</body>
</html>
