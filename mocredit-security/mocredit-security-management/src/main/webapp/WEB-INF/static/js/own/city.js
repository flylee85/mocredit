/**
 * jQuery :  省市县联动插件
 * @author   kxt
 * @example  $("#test").province_city();
 */
$.fn.province_city = function(vprovince,vcity,vtown){
    var _self = this;
    //定义3个默认值
    _self.data("province",["请选择", ""]);
    _self.data("city",["请选择", ""]);
    //插入3个空的下拉框
    //分别获取3个下拉框
    var $sel1 = _self.find("select").eq(0);
    var $sel2 = _self.find("select").eq(1);
    //默认省级下拉
    if(_self.data("province")){
        $sel1.append("<option value='"+_self.data("province")[1]+"'>"+_self.data("province")[0]+"</option>");
    }
    $.get('common/js/own/city.xml', function(data){
        var arrList = [];
        $(data).find('province').each(function(){
            var $province = $(this);
            $sel1.append("<option value='"+$province.attr('value')+"'>"+$province.attr('value')+"</option>");
        });
        if(typeof vprovince != undefined){
            $sel1.val(vprovince);
            $sel1.change();
        }

    });
    //默认城市下拉
    if(_self.data("city")){
        $sel2.append("<option value='"+_self.data("city")[1]+"'>"+_self.data("city")[0]+"</option>");
    }
    //省级联动控制
    var index1 = "" ;
    var provinceValue = "";
    var cityValue = "";
    $sel1.change(function(){
        //清空其它2个下拉框
        $sel2[0].options.length=0;
        index1 = this.selectedIndex;
        if(index1 == 0){	//当选择的为 “请选择” 时
            if(_self.data("city")){
                $sel2.append("<option value='"+_self.data("city")[1]+"'>"+_self.data("city")[0]+"</option>");
            }
        } else{
            provinceValue = $('#province').val();
            $.get('common/js/own/city.xml', function(data){
                $(data).find("province[value='"+provinceValue+"'] > city").each(function(){
                    var $city = $(this);
                    $sel2.append("<option value='"+$city.attr('value')+"'>"+$city.attr('value')+"</option>");
                });

                if(typeof vcity != undefined){
                    $sel2.val(vcity);
                    $sel2.change();
                }
            });
        }
    }).change();

    return _self;
};
