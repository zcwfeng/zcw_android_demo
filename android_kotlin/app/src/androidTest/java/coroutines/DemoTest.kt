package coroutines

import junit.framework.TestCase

class DemoTest : TestCase() {
    lateinit var demo:Demo

    public override fun setUp() {
        super.setUp()
        println("println setup")
        demo = Demo()
    }

    public override fun tearDown() {
        println("println tearDown")
    }

    fun testTestCoroutine1() {
//        demo.testCoroutine1()
        demo.testCoroutine2()
    }
}