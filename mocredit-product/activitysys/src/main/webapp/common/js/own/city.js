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
    $.get('area/getChildren/0', function(data){
        var arrList = [];
        for(var i in data.data){
        	var $province = data.data[i];
            $sel1.append("<option value='"+$province.id+"'>"+$province.title+"</option>");
        };
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
            provinceValue = $sel1.val();
            $.get('area/getChildren/'+provinceValue, function(data){
            	for(var i in data.data){
                	var $city = data.data[i];
                    $sel2.append("<option value='"+$city.id+"'>"+$city.title+"</option>");
                };

                if(typeof vcity != undefined){
                    $sel2.val(vcity);
                    $sel2.change();
                }
            });
        }
    }).change();

    return _self;
};
