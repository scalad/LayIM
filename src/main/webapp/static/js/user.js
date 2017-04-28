layui.use(['jquery', 'layer', 'form', 'upload'], function() {
	var $ = layui.jquery,
	layim = parent.layim,
	form = layui.form,
	layer = layui.layer;
	//屏蔽右键菜单
	$(document).bind("contextmenu",function(e){
        return false;
    });
    //修改头像
    layui.upload({
        url: "/user/updateAvatar"
        ,title: '修改头像'
        ,ext: 'jpg|png|gif'
        ,before: function(input) {
        	console.log("before upload!");
        }
        ,success: function(res, input){
        	console.log(res.data);
            if(0 == res.code){
                $("#LAY_demo_upload").attr('src', res.data.src);
                $("#user_avatar").val(res.data.src);
            }else{
                layer.msg(res.msg, {time:2000});
            }
        }
    });
	
    //从缓存中初始化数据
	$(document).ready(function(){
		var mine = layim.cache().mine;
		$("#username").val(mine.username);
		$("#email").val(mine.email);
		$("#sign").val(mine.sign);
		$("#LAY_demo_upload").attr("src", mine.avatar);
		if (mine.sex == "0") {
			$("input[type='radio']").eq(0).attr("checked",true);
		} else {
			$("input[type='radio']").eq(1).attr("checked",true);
		}
	});
	
});