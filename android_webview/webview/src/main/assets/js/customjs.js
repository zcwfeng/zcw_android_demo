var customjs = {};
customjs.os = {};
customjs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
customjs.os.isAndroid = !customjs.os.isIOS;
customjs.takeNativeAction = function(commandname,parameters){
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.customjs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request))
        window.customwebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.customwebview.postMessage(JSON.stringify(request));

    }
}
window.customjs = customjs;