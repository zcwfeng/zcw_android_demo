var customjs = {};
customjs.os = {};
customjs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
customjs.os.isAndroid = !customjs.os.isIOS;
customjs.callbacks = {}



customjs.callback = function (callbackname, response) {
   var callbackobject = customjs.callbacks[callbackname];
   console.log("customjs.callback callbackname---->"+callbackname);
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
          console.log("customjs.callback response---->"+response);
            var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete customjs.callbacks[callbackname];
       }
   }
}

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


customjs.takeNativeActionWithCallback = function(commandname, parameters, callback) {
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    customjs.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.customjs.os.isAndroid){
        window.customwebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.customwebview.postMessage(JSON.stringify(request))
    }
}





window.customjs = customjs;