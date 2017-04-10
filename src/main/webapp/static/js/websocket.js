var socket = null;
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
			alert('当前浏览器不支持WebSocket功能，请更换浏览器访问。');
		}
	},
	startListener : function() {
		if (socket) {
			socket.onerror = function() {
				console.log("连接失败!");
			};
			socket.onopen = function(event) {
				console.log("连接成功");
			}
			socket.onmessage = function(event) {
				console.log("接收到消息");
				im.handleMessage(event);
			}
			socket.onclose = function() {
				console.log("关闭连接！!");
			}
		}
	},
	handleMessage : function(msg) {
		console.log(msg.data);
		layim.getMessage(JSON.parse(msg.data));
		switch (msg.type) {
		case 'friend':
			layim.getMessage(msg.data);
			break;
		case 'SERVICE_ONLINE_STATUS':
			//更改状态
			layim.setFriendStatus(msg.msg.id, msg.msg.status);
			break;
		case 'SERVICE_MESSAGE_COUNT':
			if(msg.msg != 0)
				layim.msgbox(msg.msg); //设置消息盒子
			break;
		default:
			break;
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

layui.use(['layim', 'jquery'], function(layim){
	var $ = layui.jquery;
	window.layim = layui.layim;
	im.init();
	if(!/^http(s*):\/\//.test(location.href)){
		layer.open({
		  	type: 1,
		  	skin: 'layui-layer-rim', //加上边框
		  	area: ['420px', '240px'], //宽高
		  	content: '<center>请部署到服务器上查看该演示！</center>'
		});
	}
	
  	//演示自动回复
  	var autoReplay = [
    	'您好，我现在有事不在，一会再和您联系。', 
    	'你没发错吧？face[微笑] ',
    	'洗澡中，请勿打扰，偷窥请购票，个体四十，团体八折，订票电话：一般人我不告诉他！face[哈哈] ',
    	'你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
	    'face[威武] face[威武] face[威武] face[威武] ',
	    '<（@￣︶￣@）>',
	    '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
	    'face[黑线]  你慢慢说，别急……',
	    '(*^__^*) face[嘻嘻] ，是贤心吗？'
  	];
  
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
	    ,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
	    ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatLog.html' //聊天记录页面地址，若不开启，剔除该项即可
	  });
	
	  //监听在线状态的切换事件
	  layim.on('online', function(data){
	      console.log(data);
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
	  });
	
	  //监听发送消息
	  layim.on('sendMessage', function(data){
		  var mine = data.mine
	      var To = data.to;
	      console.log(data);
	      socket.send(JSON.stringify({
	    	 type:"chatMessage",
	    	 mine:mine,
	    	 to:To
	      }));
	      if(To.type === 'friend'){
		      layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
	      }
	      //演示自动回复
	      /*setTimeout(function(){
		      var obj = {};
		      if(To.type === 'group'){
		          obj = {
			          username: '模拟群员'+(Math.random()*100|0)
			          ,avatar: layui.cache.dir + 'images/face/'+ (Math.random()*72|0) + '.gif'
			          ,id: To.id
			          ,type: To.type
			          ,content: autoReplay[Math.random()*9|0]
		          }
		      } else {
			        obj = {
			      	    username: To.name
			         	,avatar: To.avatar
			          	,id: To.id
			          	,type: To.type
			          	,content: autoReplay[Math.random()*9|0]
			        }
		        	layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
		      }
	      	layim.getMessage(obj);
	      }, 1000);*/
	  });
	
	  //监听查看群员
	  layim.on('members', function(data){
	      console.log(data);
	  });
	  
	  //监听聊天窗口的切换
	  layim.on('chatChange', function(res){
	      var type = res.data.type;
	      console.log(res.data.id)
	      if(type === 'friend'){
	          //模拟标注好友状态
	      	  //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
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
});
