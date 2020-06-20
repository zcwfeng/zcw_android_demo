package top.zcwfeng.rxjava.designpattern;
// 被观察者 起点
public interface Observable {

    void addOberver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();

    void pushMessages(String message);
}
