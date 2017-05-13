document.write("<script language='javascript' src='/static/js/reconnecting/reconnecting-websocket.js'></script>");
var socket = null;
layui.use(['layim', 'jquery', 'laytpl'], function(layim){
	var $ = layui.jquery,laytpl = layui.laytpl;
	//把layim对象添加到window上
	window.layim = layui.layim;
	//屏蔽右键菜单
	$(document).bind("contextmenu",function(e){ return false;});
	//从缓存中获取用户的信息
	function getFriend(friends, id){
		var ele;
	    friends.forEach(function(e) {
	    	e.list.forEach(function(element) {
	    		if(id == element.id) {
	    			ele = element;
	    		}
	    	});
	    });
	    return ele;
	}
	//声明websocket属性
	var index; 
	var im = {
		init: function() {
			if ('WebSocket' in window) {
				var host = window.location.host
				if(window.location.post != ""){
					host = host + ":" + window.location.port;
				}
				var url = 'ws://' + host + '/websocket/'+ getUid();
				socket = new ReconnectingWebSocket(url, null, {debug: true, reconnectInterval: 3000});
				im.startListener();
			} else {
				layer.msg('当前浏览器不支持WebSocket功能，请更换浏览器访问!',{icon: 2,shade: 0.5,time:-1});
			}
		},
		startListener : function() {
			if (socket) {
				socket.onopen = function(event) {
					console.log("连接成功");
					layer.close(index);
				};
				socket.onmessage = function(event) {
					console.log("接收到消息:" + event.data);
					im.handleMessage(event.data);
				};
				socket.onerror = function() {
					socket.close;
				};
				socket.onclose = function() {
					index = layer.msg('你与服务器断开连接，正在尝试重新连接！', {icon: 2,shade: 0.5,time:-1});
				}
			}
		},
		//处理接收到的消息
		handleMessage : function(data) {
			json = eval("(" + data + ")");
			var type = json.type;
			switch(type) {
				//处理好友和群消息
				case "friend":
				case "group": { 
					layim.getMessage(JSON.parse(data));
					break;
				};
				//监测好友在线状态
				case "checkOnline": {
					var style;
					if(json.status == "在线"){
						style = "color:#00EE00;";
					} else {
						style = "color:#FF5722;";
					}
					layim.setChatStatus('<span style="' + style +'">' + json.status + '</span>');
					break;
				};
				//消息盒子
				case "unHandMessage": {
					//消息盒子未处理的消息
					layim.msgbox(json.count);
					break;
				};
				//删除好友消息，
				case "delFriend": {
					var friends = layim.cache().friend;        	
		        	var friend = getFriend(friends, json.uId);
		        	layer.alert("用户'"+friend.username +"'删除了你!", {icon: 1,time:0,title:"删除信息"});
					layim.removeList({
						  type: 'friend' 
						  ,id: json.uId
					});
					break;
				};
				//添加好友请求
				case "addFriend": {
					layer.alert("有新的用户添加你为好友,请查看消息盒子!", {icon: 0,time:0,title:"添加信息"});
					layim.msgbox(1);
					break;
				};
				//同意添加好友时添加dao好友列表中
				case "agreeAddFriend": {
					var group = eval("(" + json.msg + ")");
					layim.addList({
						type: 'friend'
						,avatar: json.mine.avatar
						,username: json.mine.username
						,groupid: group.group
						,id: json.mine.id
						,sign: json.mine.sign
					});
					layer.alert("用户'"+json.mine.username+"'已同意添加你为好友!", {icon: 0,time:0,title:"添加信息"});
					break;
				};
				//请求加群
				case "addGroup": {
					layer.alert("有新的用户申请加群,请查看消息盒子!", {icon: 0,time:0,title:"添加信息"});
					layim.msgbox(1);
					break;
				}
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
	    ,min: false //是否始终最小化主面板，默认false
	    ,notice: true //是否开启桌面消息提醒，默认false
	    ,voice: true //声音提醒，默认开启，声音文件为：default.wav
	    
	    ,msgbox: '/static/html/msgbox.html?uid='+getUid() //消息盒子页面地址，若不开启，剔除该项即可
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
		  //请求未处理的消息
		  socket.send(JSON.stringify({
		    	 type:"unHandMessage",
		    	 mine:null,
		    	 to:null
		  }));
		  //个人信息
	      $(".layui-layim-user").css("cursor","pointer");
	      var mine = layim.cache().mine;
	      $(".layui-layim-user").bind("click", function(){
	    	  layer.open({
	    		  type: 2,
	    		  title: "修改个人信息",
	    		  skin: 'layui-layer-rim',
	    		  area: ['500px', '550px'], 
	    		  content: '/static/html/userinfo.html'
	    	  });
	      })
	  });
	  
	  //监听发送消息
	  layim.on('sendMessage', function(data){
		  var mine = data.mine
	      var To = data.to;
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
		      /*layim.getMessage({
		          system: true
	        	  ,id: res.data.id
	        	  ,type: "group"
	        	  ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
	      	  });*/
	    }
	});
	  
    //外部自定义我的事件
    my_events = {
		//改变用户的群组
        changeGroup: function(othis, e){
            //改变群组模板
            var elemAddTpl = ['<div class="layim-add-box">'
                , '<div class="layim-add-img"><img class="layui-circle" src="{{ d.data.avatar }}"><p>' +
                '{{ d.data.name||"" }}</p></div>'
                , '<div class="layim-add-remark">'
                , '{{# if(d.data.type === "friend" && d.type === "setGroup"){ }}'
                , '<p>选择分组</p>'
                , '{{# } if(d.data.type === "friend"){ }}'
                , '<select class="layui-select" id="LAY_layimGroup">'
                , '{{# layui.each(d.data.group, function(index, item){ }}'
                , '<option value="{{ item.id }}">{{ item.groupname }}</option>'
                , '{{# }); }}'
                , '</select>'
                , '{{# } }}'
                , '{{# if(d.data.type === "group"){ }}'
                , '<p>请输入验证信息</p>'
                , '{{# } if(d.type !== "setGroup"){ }}'
                , '<textarea id="LAY_layimRemark" placeholder="验证信息" class="layui-textarea"></textarea>'
                , '{{# } }}'
                , '</div>'
                , '</div>'].join('');

            var friend_id = othis.parent().attr('data-id');
            $.getJSON('/user/findUser?id=' + friend_id.substring(12), function(res){
                if(0 == res.code){
                    var index = layer.open({
                        type: 1,
                        skin: 'layui-layer-rim', //加上边框
                        area: ['430px', '260px'], //宽高
                        btn:   ['确认', '取消'],
                        title: '移动分组',
                        content: laytpl(elemAddTpl).render({
                            data: {
                                name: res.data.username
                                , avatar: res.data.avatar
                                , group: parent.layui.layim.cache().friend
                                , type: 'friend'
                            }
                            , type: 'setGroup'
                        })
                        , yes: function (index, layero) {
                            var groupElem = layero.find('#LAY_layimGroup');
                            var group_id = groupElem.val(); //群组id
                            $.post('/user/changeGroup', {'groupId' : group_id, 'userId' : res.data.id},
                                function(data) {
                                    if (0 == data.code) {
                                        layer.msg(data.msg, {time: 1500});
                                        //先从旧组移除，然后加入新组
                                        layim.removeList({
                                            type: 'friend'
                                            ,id: res.data.id
                                        });
                                        //加入新组
                                        layim.addList({
                                            type: 'friend'
                                            ,avatar: res.data.avatar
                                            ,username: res.data.username
                                            ,groupid: group_id
                                            ,id: res.data.id
                                            ,sign: res.data.sign
                                        });
                                        layer.close(index);
                                    } else {
                                        layer.msg(data.msg, {time: 2000});
                                    }
                            }, 'json');
                        }
                    });
                }else{
                    layer.msg(res.msg, {time: 2000});
                }
            });
        },
        //删除好友
        removeFriend: function(othis, e){
            var friend_id = othis.parent().attr('data-id').substring(12);
            //询问框
            layer.confirm('确定删除该好友？', {
                btn: ['确定', '取消'],
                title: '友情提示',
                closeBtn: 0,
                icon: 3
            }, function(){
                $.post('/user/removeFriend', {'friendId' : friend_id}, function(res){
                    if(0 == res.code){
                        layer.msg('删除成功!', {icon: 1, time: 1500});
                        layim.removeList({
                            type: 'friend'
                            , id: friend_id
                        });
                        //如果对方在线,通知对方用户删除我
                        var data = '{"type":"delFriend","to":{"id":'+friend_id+'}}';
                        socket.send(data);
                    }else{
                        layer.msg(res.msg, {time: 1500});
                    }
                }, 'json');
            });
        },
        //查看资料
        checkOut: function(othis, e) {
        	var friend_id = othis.parent().attr('data-id').substring(12);
        	var friends = layim.cache().friend;        	
        	var friend = getFriend(friends, friend_id);
        	var params = escape("id="+friend.id+"&username="+friend.username+"&sign="+friend.sign+"&avatar="+friend.avatar+"&email="+friend.email+"&sex="+friend.sex);
        	layer.open({
	    		  type: 2,
	    		  title: "好友信息",
	    		  skin: 'layui-layer-rim',
	    		  area: ['500px', '300px'], 
	    		  scrollbar: false,
	    		  maxWidth: "400px",
	    		  content: '/static/html/friend.html?' + params
	    	});
        },
        //退出群
        leaveOutGroup: function(othis, e) {
        	var groupId = othis.parent().attr("data-id");
        	var index = layer.confirm('确定退出该群？', {
                btn: ['确定', '取消'],
                title: '友情提示',
                closeBtn: 0,
                icon: 3
            }, function(){
            	$.post('/user/leaveOutGroup', {
                	groupId: groupId
                }, function(res){
                	if (res.code == 0) {                		
                		layim.removeList({type: 'group',id: groupId});
                	}
                	layer.msg(res.msg);
                	layer.close(index);
                },"json");
            });
        },
        //右键菜单消息记录
        viewChatLog: function(othis, e) {
        	var friend_id = othis.parent().attr('data-id').substring(12);
        	var friends = layim.cache().friend;        	
        	var friend = getFriend(friends, friend_id);
        	return layer.open({
                type: 2
                ,maxmin: true
                ,title: '与 '+ friend.username +' 的聊天记录'
                ,area: ['45%', '100%']
                ,shade: false
                ,offset: 'rb'
                ,skin: 'layui-box'
                ,anim: 2
                ,id: 'layui-layim-chatlog'
                ,content: layim.cache().base.chatLog + '?id=' + friend.id + '&Type=friend'
            });
        }
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
