var values = ['富强','民主','文明','和谐',
    '自由','平等','公正','法制',
    '爱国','敬业','诚信','友善']

$(document).click(function(e){
    var body = $('body');
    var index = Math.floor(Math.random()*12);
    var obj1 = $('<div class="tip" style="left: '+(e.pageX-12)+'px;top: '+(e.pageY-6)+'px;opacity: 1;">'+values[index]+'</div>');
    // var obj2 = $('<div class="tip" style="left: '+(e.pageX-3)+'px;top: '+(e.pageY-18)+'px;opacity: 1;">↓</div>');
    // var obj3 = $('<div class="tip" style="left: '+(e.pageX)+'px;top: '+(e.pageY-12)+'px;opacity: 1;">←</div>');
    // var obj4 = $('<div class="tip" style="left: '+(e.pageX-12)+'px;top: '+(e.pageY-12)+'px;opacity: 1;">→</div>');
    body.append(obj1);
    // body.append(obj2);
    // body.append(obj3);
    // body.append(obj4);
    time1(obj1,1);
    // time1(obj2,2);
    // time1(obj3,3);
    // time1(obj4,4);
})

function time1(obj,dir){
    var top=parseInt(obj.css('top'));
    var left=parseInt(obj.css('left'));
    var opacity = obj.css('opacity');
    if(opacity<0.1){
        obj.remove();
        return false;
    }
    if(dir==1){
        obj.css("top",top-3);
    }else if(dir==2){
        obj.css("top",top+3);
    }else if(dir==3){
        obj.css("left",left-3);
    }else if(dir==4){
        obj.css("left",left+3);
    }
    obj.css("opacity",opacity-0.03);
    setTimeout(function(){
        time1(obj,dir);
    },20)
}