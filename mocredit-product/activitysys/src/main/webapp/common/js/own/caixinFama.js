/**
 * Created by Mu on 2015/12/9.
 */
(function ($, w, n) {

    $.caixinFama = function (option) {

        var opts = $.extend('', $.caixinFama.default, option);
        opts.add_node = $(opts.add_node);
        opts.delete_node = $(opts.delete_node);
        opts.save_node = $(opts.save_node);
        opts.file_upload = $(opts.file_upload);
        opts.frame_select = $(opts.frame_select);
        opts.code_select = $(opts.code_select);
        opts.input_text = $(opts.input_text);
        var module_node = $('.caixin-left-inner');
        var modulePic_node = $(".caixin-inner-pic");
        var caixin_inner_txt = module_node.find('.caixin-inner-txt');
        opts.file_form =  $(opts.file_form);
        var endJson = $('#endJson');
        var jsonHidden = $.parseJSON(endJson.attr('data-json'));
        var random = parseInt(10000*Math.random());
        $("#identifier").val(random);
        opts.input_text.keyup(function () {
            var thisNode = $(this);
            caixin_inner_txt.text(thisNode.val());
        });
        
        $('#choosePic').click(function () {
            opts.file_upload.trigger('click');
        });

        opts.file_upload.fileinput({
            uploadUrl: opts.url_save, // server upload action
            language: 'zh',
            uploadAsync: false,
            showUpload: false, // hide upload button
            showRemove: false, // hide remove button
            showPreview: false,
            msgInvalidFileExtension: "{name}" + "{extensions}" ,
            minFileCount: 1,
            maxFileCount: 1,
            showCaption: false,
            uploadExtraData: function(previewId, index){
            	var curOne = parseInt(opts.frame_select.find('option:selected').val());
            	return {"frame_no" : curOne, "identifier" : $("#identifier").val()};
            }
        }).on("filebatchselected", function(event, files) {
            for(var i=0;i<files.length;i++){
                var file = files[i];
                var fileName = file.name;
                var index1 = fileName.lastIndexOf(".");
                var index2= fileName.length;
                var suffix=fileName.substring(index1,index2);
                if(suffix!='.jpg'&& suffix!='.jpeg' && suffix!='.gif'){
                    sendMsg(false, "只能上传.gif,.jpg,.jpeg格式的图片");
                    return false;
                }
                if(file.size > 20000){
                    sendMsg(false, '单个图片不能大于20K');
                    return false;
                }
            }
            opts.file_upload.fileinput("upload");

        }).on("filebatchuploadsuccess", function(event, data, previewId, index) {
        	console.log('File batch upload success');
        	console.log(data.response.data);
            var remsg = data.response.data.split("|");
            if (remsg[0] == "1") {
                sendMsg(true, '文件上传成功');
                modulePic_node.empty().append('<img src="'+remsg[1]+'">');
                $('#imgSrc').val(remsg[1]);
            } else {
                sendMsg(false, '文件上传失败'  + remsg[2]);
                //$("img").attr({ "src": "project/Images/msg_error.png" });
            }
        }).on("fileerror", function(event, data){
        	console.log('fileerror');
    		console.log(data.id);
    		console.log(data.index);
    		console.log(data.file);
    		console.log(data.reader);
    		console.log(data.files);
    	}).on('filebatchuploadcomplete', function(event, files, extra) {
    	    console.log('File batch upload complete');
    	});

/*
        // 图片上传
        opts.file_upload.change(function (e) {
            var $thisfile = $(this)[0].files[0];
            console.log($thisfile);
            var fileType = $thisfile.name.substring($thisfile.name.lastIndexOf('.'));

            switch (fileType) {
                case ".jpg":
                case ".jpeg":
                case ".gif":
                    break;
                default:
                    alert('只能上传.gif,.jpg,.jpeg格式的图片');
                    return false;
                    break;
            }
            if($thisfile.size > 20000){
                alert('单个图片不能大于20K');
                return false;
            }
            opts.file_form.ajaxSubmit({
                type: "post",
                url: opts.url_save,
                success: function (data1) {
                    var remsg = data1.split("|");
                    if (remsg[0] == "1") {
                        sendMsg(true, '文件上传成功');
                        modulePic_node.append('<img src="'+remsg[1]+'">');
                        $('#imgSrc').val(remsg[1]);
                    } else {
                        sendMsg(false, '文件上传失败'  + remsg[2]);
                        //$("img").attr({ "src": "project/Images/msg_error.png" });
                    }
                },
                error: function (msg) {
                    sendMsg(false, '文件上传失败');
                }
            });
            return false;
        });*/
        // 选择帧
        opts.frame_select.change(function () {
            module_node.find('div').empty();
            var $this = $(this);
            var frame = $.grep(jsonHidden.frames, function (n,i) {
                return n.frame_no == $this.val();
            });
            opts.input_text.val(frame[0].text);
            caixin_inner_txt.html(frame[0].text);
            modulePic_node.append('<img src="'+frame[0].pic+'">');
            console.log(frame);
        });
        // 选择二维码插入帧
        opts.code_select.change(function () {
            var curOne = parseInt($(this).find('option:selected').val());
            jsonHidden.code_no = curOne;
            endJson.val(JSON.stringify(jsonHidden)).attr('data-json', JSON.stringify(jsonHidden));
        });
        // 添加帧
        opts.add_node.click(function () {
            $('#imgSrc').val('');
            var curOne = parseInt(opts.frame_select.find('option:selected').val());
            var lastOne = parseInt(opts.frame_select.find('option:last').val());
            if(!isNaN(curOne) && $.grep(jsonHidden.frames, function (n,i) {
                    return n.frame_no == curOne;
                }).length == 0 ){
                sendMsg(false, "请先保存本帧添加的内容！");
                return false;
            }

            if(!lastOne ||lastOne == 0 || lastOne == ''){
                lastOne = 0;
            }
            opts.frame_select.append('<option value="' + ( lastOne + 1 ) + '" selected>第'+( lastOne + 1 )+'帧</option>');
            opts.code_select.append('<option value="' + ( lastOne + 2 ) + '">第'+( lastOne + 2 )+'帧</option>');
            module_node.find('div').empty();
            opts.input_text.val('');

        });
        $('#subject').keyup(function () {
            var txt = $(this).val();
            jsonHidden.subject = txt;
            endJson.val(JSON.stringify(jsonHidden)).attr('data-json', JSON.stringify(jsonHidden));
        });
        // 初始化
        function init(){

            $('#subject').val(jsonHidden.subject);
            $('#isresend').prop('checked', jsonHidden.isresend);
            if(jsonHidden.isresend){
                $('#isresend').next('.icon-unchecked').addClass('checked');
            }
            opts.frame_select.empty();
            opts.code_select.empty();
            if(jsonHidden.frames.length == 0){
                opts.frame_select.append('<option value="1" selected>第1帧</option>');
                opts.code_select.append('<option value="2" selected>第2帧</option>');
            }
            $.each(jsonHidden.frames, function (i, n) {
                console.log(n);
                opts.frame_select.append('<option value="'+ n.frame_no + '">第'+ n.frame_no + '帧</option>');
                opts.code_select.append('<option value="'+ (n.frame_no+1) + '">第'+ (n.frame_no+1) + '帧</option>');
            });
            $('#imgSrc').val(jsonHidden.frames[0].pic);
            opts.frame_select.find('option:first').prop('selected', true);
            opts.frame_select.change();
            opts.code_select.find('option[value="'+jsonHidden.code_no+'"]').prop('selected', true);

        }
        //是否自动补发
        $("#isresend").click(function () {
            jsonHidden.isresend = $(this).prop('checked');
            endJson.val(JSON.stringify(jsonHidden)).attr('data-json', JSON.stringify(jsonHidden));
        });
        init();
        // 删除帧
        opts.delete_node.click(function () {
            var curOne = parseInt(opts.frame_select.find('option:selected').val());
            var thisNode = opts.frame_select.find('option:selected');
            if(!isNaN(curOne) && $.grep(jsonHidden.frames, function (n,i) {
                    return n.frame_no == curOne
                }).length == 0 ){
                sendMsg(false, "本帧未保存,无需删除!");
                return false;
            }
            $.each( jsonHidden.frames, function(i, n){
                if(n.frame_no == thisNode.val()){
                    jsonHidden.frames.splice(i, 1);
                    return false;
                }
            });
            $.each( jsonHidden.frames, function(i, n){
                n.frame_no = i + 1;
            });
            thisNode.prev().prop('selected', true);
            opts.frame_select.change();
            thisNode.remove();
            endJson.val(JSON.stringify(jsonHidden)).attr('data-json', JSON.stringify(jsonHidden));
            init();
        });
        // 保存帧
        opts.save_node.click(function () {
            if(opts.input_text.val() == "" && modulePic_node.find('img').length == 0){
                sendMsg(false, "帧内容不能为空！");
                return false;
            }
            var thisNode = opts.frame_select.find('option:selected');
            $.each( jsonHidden.frames, function(i, n){
                if(n.frame_no == thisNode.val()){
                    jsonHidden.frames.splice(i, 1);
                }
            });

            var thisNode = opts.frame_select.find('option:selected');
            var jsonInner = {
                'frame_no':thisNode.val(),
                'pic':$('#imgSrc').val(),
                'text':opts.input_text.val()
            };

            jsonHidden.frames.push(jsonInner);
            endJson.val(JSON.stringify(jsonHidden)).attr('data-json', JSON.stringify(jsonHidden));

        });
        $('.uilg-caixin-fama span').click(function () {
            var valu = opts.input_text.val() + $(this).text();

            opts.input_text.val(valu);
            caixin_inner_txt.html(valu);
        });
    };
    $.caixinFama.default = {
        add_node : '#addFrame',
        delete_node :'#deleteFrame',
        save_node : '#saveFrame',
        file_upload : '#file-upload',
        frame_select : '#frame-select',
        code_select : '#code-select',
        input_text : '#input-text',
        file_form : '#file-form',
        url_save : 'activitysys/uploadpic'       //保存url
    };





})(jQuery, window, undefined);