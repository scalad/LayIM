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
body{overflow:hidden;background-color:#39c}#particles-js{position:absolute;width:100%;height:100%;background-color:#161617;background-image:url(/static/themes/bg1.jpg);background-repeat:no-repeat;background-size:cover;background-position:50%}
.desktop-icon div{margin-bottom:6px}.desktop-app:hover{border:solid 1px #7da2ce;-moz-border-radius:3px;-webkit-border-radius:3px;box-shadow:inset 0 0 1px #fff;-moz-box-shadow:inset 0 0 1px #fff;-webkit-box-shadow:inset 0 0 1px #fff;background:#cfe3fd;background:-moz-linear-gradient(top,#dcebfd,#c2dcfd);background:-webkit-gradient(linear,center top,center bottom,from(#dcebfd),to(#c2dcfd));opacity:.9}.desktop-app{height:88px;padding:3px;width:88px;border:1px solid transparent;cursor:pointer}.desktop-app i.layui-icon{display:block;width:60px;height:60px;margin:0 auto;text-align:center;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;font-size:50px;color:#fff}.desktop-app span.desktop-title{width:68px;height:21px;display:block;margin:5px auto 0;line-height:21px;text-align:center;-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;font-size:12px;color:#fff}.layui-elip{text-overflow:ellipsis;overflow:hidden;white-space:nowrap}.layui-layer-notepaper{background-color:#fff57c!important;resize:none!important}.layui-layer-notepaper
.themes:hover{border:solid 1px #7da2ce;}
</style>
</head>
<body>
<div style="position:absolute;top:0;left:0;z-index:100" class="desktop-icon">
        <div class="desktop-app layui-change" >
            <i class="layui-icon" style="background-color:#b174ee">
                &#xe64a;
            </i>
            <span class="desktop-title">
                   	换屏
            </span>
        </div>
        <div class="desktop-app layui-effect" >
            <i class="layui-icon" style="background-color:#ab3">
                &#xe602;
            </i>
            <span class="desktop-title">
                   	效果
            </span>
        </div>
</div>
<div id="particles-js">
	<canvas class="particles-js-canvas-el"></canvas>
</div>
<script src="/static/layui/layui.js"></script>
<script src="/static/js/websocket.js"></script>
<script src="/static/js/particles.min.js"></script>
<script>
particlesJS.load('particles-js', '/static/json/particles0.json', function() {});
layui.use(['layer', 'jquery'], function(){
	var layer = layui.layer,$ = layui.jquery;
	$(".desktop-icon .layui-change").bind("click",function(){
		var content ='<img class="themes" src="/static/themes/bg1_small.jpg">'
				   + '<img class="themes" src="/static/themes/bg2_small.jpg">'
				   + '<img class="themes" src="/static/themes/bg3_small.jpg">'
		           + '<img class="themes" src="/static/themes/bg4_small.jpg">'
		           + '<img class="themes" src="/static/themes/bg5_small.jpg">' 
		layer.open({
			title:"换屏",
			type: 1,
			skin: 'layui-layer-rim', //加上边框
			area: ['420px', '244px'], //宽高
			content: content
		}); 
		$(".themes").css("cursor","pointer");           
		$(".themes").bind("click",function(){
			var src = $(this).attr("src").replace("_small","");
			$("#particles-js").css('background-image','url("' + src + '")');
		});		
	});
	
	var i = 1;
	//效果
	$(".desktop-icon .layui-effect").click(function(){
		if(i > 3){i = 0;}
		if(i ==3){
			i = 0;
			$(".particles-js-canvas-el").hide();
			return;
		}
		particlesJS.load('particles-js', '/static/json/particles' + i + '.json', function() {});
		i++;
	});
});
function getUid() {return ${user.id};}
</script>
</body>
</html>
