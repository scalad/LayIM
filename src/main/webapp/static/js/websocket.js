var socket = null;
var show;
layui.use(['layim', 'jquery'], function(layim){
	var $ = layui.jquery;
	//把layim对象添加到window上
	window.layim = layui.layim;
	//屏蔽右键菜单
	$(document).bind("contextmenu",function(e){
        return false;
    });
	//确定只有部署到服务器
	if(!/^http(s*):\/\//.test(location.href)){
		layer.open({
		  	type: 1,
		  	skin: 'layui-layer-rim', //加上边框
		  	area: ['420px', '240px'], //宽高
		  	content: '<center>请部署到服务器上查看该演示！</center>'
		});
	}
	//声明websocket属性
	var im = {
		init: function() {
			if ('WebSocket' in window) {
				var uid = getUid();
				if (!uid) {
					console.log('当前用户未登陆，应该跳到login');
				} else {
					var host = window.location.host
					if(window.location.post != ""){
						host = host + ":" + window.location.port;
					}
					var socketUrl = 'ws://' + host + '/websocket/'+uid;
					socket = new WebSocket(socketUrl);
					im.startListener();
				}
			} else {
				layer.msg('当前浏览器不支持WebSocket功能，请更换浏览器访问!');
			}
		},
		startListener : function() {
			if (socket) {
				socket.onerror = function() {
					layer.msg("连接失败!");
				};
				socket.onopen = function(event) {
					console.log("连接成功");
				};
				socket.onmessage = function(event) {
					console.log("接收到消息");
					im.handleMessage(event.data);
				};
				socket.onclose = function() {
					console.log("关闭连接！!");
					im.waitForConnection(function(){
						im.init();
					},5);
				}
			}
		},
		//处理接收到的消息
		handleMessage : function(data) {
			console.log(data);
			json = eval("(" + data + ")");
			var type = json.type;
			if("friend" == type || "group" == type) {				
				layim.getMessage(JSON.parse(data));
			} else if("checkOnline" == type){
				var style;
				if(json.status == "在线"){
					style = "color:#00EE00;";
				} else {
					style = "color:#FF5722;";
				}
				layim.setChatStatus('<span style="' + style +'">' + json.status + '</span>');
			}
		},
		sendData:function(data){
			this.waitForConnection(function () {
				socket.send(data);
		    }, 500);
		},
		waitForConnection : function (callback, interval) {//判断连接是否建立
		    if (socket.readyState === 1) {
		        callback();
		    } else {
		        var that = this;
		        setTimeout(function () {
		            that.waitForConnection(callback, interval);
		        }, interval);
		    }
		}
	}

	//初始化WebSocket对象
	im.init();
	
	//基础配置
	layim.config({
		//主面板最小化后显示的名称
		title: "我的LayIM",
	    //初始化接口
	    init: {
	    	url: '/user/init/' + getUid()
	      	,data: {id : getUid()}
	  	  	,type:'post'
	    }
	    
	    //查看群员接口
	    ,members: {
	      	url: '/user/getMembers'
	      	,data: {}
	    }
	    
	    //上传图片接口
	    ,uploadImage: {
	      	url: '/user/upload/image' //（返回的数据格式见下文）
	      	,type: 'post' //默认post
	    } 
	    
	    //上传文件接口
	    ,uploadFile: {
	      	url: '/user/upload/file' //（返回的数据格式见下文）
	      	,type: 'post' //默认post
	    }
	    
	    //扩展工具栏
	    ,tool: [{
	    	alias: 'code'
	      	,title: '代码'
	      	,icon: '&#xe64e;'
	    }]
	    
	    ,title: '我的LayIM' //自定义主面板最小化时的标题
	    ,brief: false //是否简约模式（若开启则不显示主面板）
	    ,right: '20px' //主面板相对浏览器右侧距离
	    ,minRight: '20px' //聊天面板最小化时相对浏览器右侧距离
	    ,initSkin: '5.jpg' //1-5 设置初始背景
	    //,skin: ['aaa.jpg'] //新增皮肤
	    ,isfriend: true //是否开启好友
	    ,isgroup: true //是否开启群组
	    //,min: true //是否始终最小化主面板，默认false
	    ,notice: true //是否开启桌面消息提醒，默认false
	    //,voice: true //声音提醒，默认开启，声音文件为：default.wav
	    
	    ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
	    ,find: '#' //发现页面地址，若不开启，剔除该项即可
	    ,chatLog: '/user/chatLogIndex' //聊天记录页面地址，若不开启，剔除该项即可
	  });
	
	  //监听在线状态的切换事件
	  layim.on('online', function(data){
		  socket.send(JSON.stringify({
		    	 type:"changOnline",
		    	 mine:null,
		    	 to:null,
		    	 msg:data
		  }));
	  });
	  
	  //监听签名修改
	  layim.on('sign', function(value){
		  $.ajax({
	    		url:"/user/updateSign",
	    		dataType:"JSON",
	    		type:"POST",
	    		data:{"sign":value},
	    		success:function(data) {
    				layer.msg(data.msg);
	    		},
	    		error:function(data) {
	    			layer.msg(data.msg + ",服务器错误,请稍后再试！");
	    		}
	    	});
	  });
	
	  //监听自定义工具栏点击，以添加代码为例
	  layim.on('tool(code)', function(insert){
	      layer.prompt({
	          title: '插入代码'
	          ,formType: 2
	          ,shade: 0
	      }, function(text, index){
	      	  layer.close(index);
	      	  insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
	      });
	  });
	  
	  //监听layim建立就绪
	  layim.on('ready', function(res){
	      layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
	      $(".layui-layim-user").css("cursor","pointer");
	      var mine = layim.cache().mine;
	      console.log(mine);
	      $(".layui-layim-user").bind("click", function(){
	    	  layer.open({
	    		  type: 1,
	    		  title: "个人信息",
	    		  skin: 'layui-layer-rim',
	    		  area: ['50%', '60%'], 
	    		  content: mine.id + mine.username + mine.sign +"<div class='layim-chat-other'><img src='" + mine.avatar + "'/></div>"
	    	  });
	      })
	  });
	  
	  //监听发送消息
	  layim.on('sendMessage', function(data){
		  var mine = data.mine
	      var To = data.to;
	      console.log(data);
	      socket.send(JSON.stringify({
	    	 type:"message",
	    	 mine:mine,
	    	 to:To
	      }));
	      if(To.type === 'friend'){
		      layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
	      }
	  });
	
	  //监听查看群员
	  layim.on('members', function(data){
	      console.log(data);
	  });
	  
	  //监听聊天窗口的切换
	  layim.on('chatChange', function(res){
	      var type = res.data.type;
	      //如果打开的是好友窗口则监测好友的状态
	      if("friend" == type){	    	  
	    	  socket.send(JSON.stringify({
	    		  type:"checkOnline",
	    		  mine:null,
	    		  to:res.data
	    	  }));
	      } else if(type === 'group'){
		      //模拟系统消息
		      layim.getMessage({
		          system: true
	        	  ,id: res.data.id
	        	  ,type: "group"
	        	  ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
	      	  });
	       }
	  });
	  
	  //显示添加好友面板
	  show = function(item) {
		  var mine = layim.cache().mine;
		  var $item = $(item);
		  var img = $item.find("img").attr("src");
		  var username = $item.find("cite").text();
		  var id = $item.attr("layim-data-uid");
		  layim.add({
			  type: 'friend' //friend：申请加好友、group：申请加群
			  ,username: username //好友昵称，若申请加群，参数为：groupname
			  ,avatar: img
			  ,submit: function(group, remark, index){ //一般在此执行Ajax和WS，以通知对方
				  socket.send(JSON.stringify({
		    		  type:"addFriend",
		    		  mine:mine,
		    		  to:null,
		    		  msg:{"group":group,"remark":remark}
		    	  }));
				  layer.close(index);
			  }
		  });
			    
	  }
	  //获取离线消息
	  /*$.ajax({
	  		url:"/user/getOffLineMessage",
	  		dataType:"JSON",
	  		type:"POST",
	  		success:function(data) {
	  			console.log(data.data.length)
	  			for(var i = 0; i < data.data.length; i ++){	  				
	  				layim.getMessage(data.data[i]);
	  				console.log(JSON.stringify(data.data[i]));
	  			}
	  		},
	  		error:function(data) {
	  			layer.msg(data.msg + ",服务器错误,请稍后再试！");
	  		}
	  });*/
});
