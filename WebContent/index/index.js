//显示通知的容器
var notify = function(content)
{
	if($(".notify-container").length == 0)
	{
		$("<div/>").addClass("notify-container").appendTo("body");
	}
	var $notifyContainer = $(".notify-container");
	var $notify = $("<div/>").addClass("notify");
	$("<div/>").addClass("content").html(content).appendTo($notify);
	$notify.appendTo($notifyContainer);
	
	$notify.fadeOut(4500);
};

var getFileName = function(path)
{
	var strings = path.split("\\");
	var length = strings.length;
	return strings[length - 1];
};

$(function(){
	//动态显示联系方式
	$(".container .guide span.contact-us").click(function(){
		$(".email-contact").slideDown(800);

	});

	//动态隐藏联系方式
	$(".email-contact .hide").click(function(){
		$(".email-contact").slideUp(500);
	});

	//提交Excel文件之前，进行文件有效性判断
	$("input.submit").click(function(){
		var $form = $(this).parent();
		var fileName = $form.find("input.upload").val();

		if(fileName != ""){
			var suffix = fileName.split(".")[1];

			if(suffix == "xlsx"){
				$(".loading").show();
				$form.submit();
			}
			else{
				alert("只能选择Excel文件");
			}
		}
		else{
			alert("您还没有选择文件");
		}
	});

	$("input.upload").bind("change", function(){
		$(this).parent().parent().children("img").css("opacity", "0.6");
		
		var fileName = getFileName($(this).val());

		notify(fileName + "选择成功");

	});
});