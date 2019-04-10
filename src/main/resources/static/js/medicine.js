var config = {
    // 平台
    platform: 'pc',
    // 行打印时间间隔
    printInterval: 60,
    // 列表别名长度
    listAliasLenght: 10
}

// 行高
var lineHeight = $('.result-test').height();

// 输出框
var oResult = $('.result');
// 本次输出，临时变量
var oResultLast;
// 输入栏
var oInputDiv = $('.input-div');
// 输入指示器
var oInputIndicator = $('.input-indicator');
// 输入框
var oInput = $('.input');

// 初始化输入栏，不减1会导致换行
oInputIndicator.text('>>> ');
oInput.width(oInputDiv.width() - oInputIndicator.width() - 1);

// 网络请求标志位
var isPosting = false;

// 控制台数据
var cmdData = {
    getData: function(key) {
        return config.platform === 'pc' ? cmdData[key] || '' : cmdData[key + '_m'] || cmdData[key] || '';
    }
};
// 当前列表对象组
var currResultDivs = [];
// 当前搜索关键字
var currSearch = '';

function launch(_config){
    if(typeof(_config) === 'object'){
        $.extend(config, _config);
    }

    oInputDiv.hide();
    var os = getOS();
    var browser = getBrowserVer();
    printText((!browser === false ? ('--> 平台： ' + os + ' - ' + browser.name + ' ' + browser.version) : 'unknown') + '<br>' + '--> 正在配置本地环境...<br>--> 正在检查网络环境...<br>', function(){
        oInputDiv.hide();

        setTimeout(function() {
            printText('--> 正在连接服务器...<br>', function(){
                $.ajax({
                    url: '/static/json/medicine.json',
                    dataType: 'json',
                    success: function(data){
                        $.extend(cmdData, data);

                        var oInputPrev = oInputDiv.prev();
                        oInputPrev.prev().remove();
                        oInputPrev.remove();

                        printText(cmdData.getData('logo'));
                    }
                })
            });
        }, 150);
    });
}

oInput.bind('keypress', function(event) {
    if (event.keyCode == "13") {
        var indicatorStr = oInputIndicator.text();
        var inputStr = oInput.val();
        oInputDiv.before('<div class="result-last-prepare">' + indicatorStr + inputStr + '<br></div>');
        oInputDiv.hide();
        oInput.val('');

        if(inputStr.toLowerCase().indexOf('lyml') > -1){
            printText(cmdData.getData('lyml'));
        }else if(inputStr && (inputStr.substring(0, 1) === '?' || inputStr.substring(0, 1) === '？')){
            if(inputStr.length === 1){
                printText(cmdData.getData('help'));
            }else{
                var cmdArr = inputStr.substring(1, inputStr.length).split(/[ -.]/);
                if(cmdArr.length == 1){
                    printText(cmdData.getData([cmdArr[0]]) || (cmdArr[0] + ' 不是内部或外部命令'));
                }
            }

        }else if (/^\d+$/.test(inputStr)) {
            var index = inputStr;
            var id = getSelectedId(index);

            if (!id === false) {

                if (isPosting) {
                    return false;
                }
                isPosting = true;
                $.post(
                    '/medicine/details', {
                        id: id
                    },
                    function(data) {
                        if (data.success) {
                            printSearchItem(data.data);
                        } else {
                            printText("接口请求错误");
                        }
                    },
                    'json'
                );
            } else {
                printText("记录选择错误");
            }

        } else {
            currSearch = inputStr;

            if (isPosting) {
                return false;
            }
            isPosting = true;
            $.post(
                '/medicine/searchList', {
                    search: currSearch
                },
                function(data) {
                    if (data.success) {
                        if (data.data.length > 0) {
                            printSearchList(data.data);
                        } else {
                            printText("没有找到记录");
                        }
                    } else {
                        printText("接口请求错误");
                    }
                },
                'json'
            );
        }
    }
});

//打印文字
function printText(text, fn) {
    if (text && text.length > 0) {
        printPrepare();
        oResultLast.append(text);

        printStart(function(){
            printOver();
            if(fn){
                fn();
            }
        });
    } else {
        printOver();
        if (fn) {
            fn();
        }
    }
}

//打印输出详细信息
function printSearchItem(item, fn) {
    printPrepare();

    var str = '名称：<span id="' + item.id + '" class="name">' + item.name + '</span>' + '&nbsp;&nbsp;' +
        '<span class="source1">' + item.source1 + '</span>' + '&nbsp;' +
        '<span class="source2">' + item.source2 + '</span>' + '&nbsp;<br>' +
        '别名：<span class="alias">' + item.alias + '</span>' + '<br>' +
        '引用文献：<span class="reference">' + item.reference + '</span>' + '<br>' +
        '详细信息：<span class="content">' + item.content.replace(/【/g, '<br>【') + '</span>' + '<br>';
    oResultLast.append(str);

    printStart(function(){
        printOver();
        if(fn){
            fn();
        }
    });
}

//打印输出列表
function printSearchList(data, fn) {
    if (data.length > 0) {
        $.each(data, function(index, item) {
            if (index === 0) {
                printPrepare();
            }

            var str = '[' + (index + 1) + '].&nbsp;' +
                '<span index="' + (index + 1) + '" id="' + item.id + '" class="name">' + item.name + '</span>' + '&nbsp;' +
                '<span class="alias">' + getRelativeAlias(item.alias) + '</span>' + '&nbsp;' +
                '<span class="source1">' + item.source1 + '</span>' + '&nbsp;' +
                '<span class="source2">' + item.source2 + '</span>' + '&nbsp;' +
                '<span class="reference">' + item.reference + '</span>' + '&nbsp;';
            oResultLast.append(str + '<br>');
        })
        currResultDivs = [oResultLast];

        printStart(function(){
            printOver();
            if(fn){
                fn();
            }
        });
    }else{
        printOver();
        if(fn){
            fn();
        }
    }
}

//准备打印 - 放入结果div
function printPrepare() {
    oResultLast = $('<div class="result-last-prepare"></div>');
    oInputDiv.before(oResultLast);
}

//开始打印 - 计算打印高度后开始打印
function printStart(fn) {
    var totalHeight = oResultLast.height();
    oResultLast.addClass('result-last');
    oResultLast.height(0);
    oResultLast.show();
    printByLine(totalHeight, fn);
}

//打印结束 - 移除打印标记，恢复输入框，请求状态置为false
function printOver() {
    oResultLast.removeClass('result-last');

    oInputIndicator.text('>>> ');
    oInputDiv.show();
    // 宽度要在show之后调，因为父控件宽度100%
    oInput.width(oInputDiv.width() - oInputIndicator.width() - 1);
    oInput.focus();
    scrollToEnd();
    isPosting = false;
}

//获取选择项的id
function getSelectedId(index) {
    for (var i = 0; i < currResultDivs.length; i++) {
        var o = currResultDivs[i].find('span[index="' + index + '"]');
        if (o.length > 0) {
            return o.attr('id');
        }
    }
    return false;
}

//获得关联的别名
function getRelativeAlias(alias) {

    if (!alias || alias.length === 0) {
        return '';
    }

    if (currSearch && alias.indexOf(currSearch) > -1) {
        var aliasArr = alias.split('，');
        for (var i = 0; aliasArr; i++) {
            if (aliasArr[i].indexOf(currSearch) > -1) {
                return aliasArr[i];
            }
        }
    }

    return alias.length > config.listAliasLenght ? (alias.substring(0, config.listAliasLenght) + '...') : alias;
}

//逐行打印本次输出
var oResultLastCurrHeight = 0;
function printByLine(totalHeight, fn) {
    if (oResultLast.height() === 0) {
        oResultLastCurrHeight = 0;
    }

    if (oResultLastCurrHeight + 1 < totalHeight) {
        setTimeout(function() {
            oResultLastCurrHeight += lineHeight;
            oResultLast.height(oResultLastCurrHeight);
            scrollToEnd();

            printByLine(totalHeight, fn);
        }, oResultLastCurrHeight === 0 ? 0 : config.printInterval);
    } else {
        if (fn) {
            fn();
        }
    }
}

//滚动到底部
function scrollToEnd() {
    oResult.scrollTop(oResult.prop('scrollHeight'));
}