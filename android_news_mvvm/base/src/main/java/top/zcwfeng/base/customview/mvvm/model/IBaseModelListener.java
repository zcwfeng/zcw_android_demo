package top.zcwfeng.base.customview.mvvm.model;


public interface IBaseModelListener<DATA> {

     void  onLoadSuccess(BaseMvvmModel model,DATA data,PagingResult ... results);
     void  onLoadFailed(BaseMvvmModel model,String msg,PagingResult ... results);
}
