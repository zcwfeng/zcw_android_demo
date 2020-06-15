package top.zcwfeng.customui.leakcanarytest

import leakcanary.AppWatcher

class Cat() {}
class Box(var hiddenCat: Cat? = null) {}

class Docker {
    companion object {
        @JvmStatic
        var container: Box? = null
    }
}


fun catTest() {
    var box = Box()
    var schrodingerCat = Cat()
    box.hiddenCat = schrodingerCat
    Docker.container = box
    AppWatcher.objectWatcher.watch(schrodingerCat, "schrodingerCat")
}