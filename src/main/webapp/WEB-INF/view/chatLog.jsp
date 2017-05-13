<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>聊天记录</title>
<link rel="stylesheet" href="/static/layui/css/layui.css">
<style>
body .layim-chat-main{height: auto;} #page{text-align: center;}
</style>
</head>
<body>
	<div id="page"></div>
	<div class="layim-chat-main">
	  <ul id="LAY_view"></ul>
	</div>

	<textarea title="消息模板" id="LAY_tpl" style="display:none;">
		{{# layui.each(d.data, function(index, item){
		  if(item.id == parent.layui.layim.cache().mine.id){ }}
		    <li class="layim-chat-mine"><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite><i>{{ layui.data.date(item.timestamp) }}</i>{{ item.username }}</cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
		  {{# } else { }}
		    <li><div class="layim-chat-user"><img src="{{ item.avatar }}"><cite>{{ item.username }}<i>{{ layui.data.date(item.timestamp) }}</i></cite></div><div class="layim-chat-text">{{ layui.layim.content(item.content) }}</div></li>
		  {{# }
		}); }}
	</textarea>
<script src="/static/layui/layui.js"></script>
<script>
layui.use(['layim', 'laypage'], function(){
    var layim = layui.layim
    ,layer = layui.layer
  	,laytpl = layui.laytpl
  	,$ = layui.jquery
  	,laypage = layui.laypage;
    
	laypage({
  		cont:$("#page"),
  		pages:${pages},
  		curr:1,
  		groups: 3,
	  	jump: function(obj, first){
  	    	//得到了当前页，用于向服务端请求对应数据
  	    	var curr = obj.curr;
	  	    //ajax 加载聊天记录
	  	    $.ajax({
	  	   		url:"${pageContext.request.contextPath}/user/chatLog",
	  	   		dataType:"JSON",
	  	   		type:"POST",
	  	   		data:{"id":${id},"Type":'${Type}',"page":curr},
	  	   		success:function(data) {
	  	   			if (data.data.length != 0) {	  	   				
		  	  		  	var html = laytpl(LAY_tpl.value).render({
		  	  			    data: data.data
		  	  		  	});
		  	  		  	$('#LAY_view').html(html);
	  	   			} else {
	  	   				$('#LAY_view').html("<center>没有数据!</center>");
	  	   			}
	  	   		}
	  	   	});
  	  	}
  	})
});
</script>
</body>
</html>
