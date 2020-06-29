package top.zcwfeng.jetpack.mvp.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentRepository {
    private LiveData<List<Student>> liveDataAllStudent;
    private StudentDao studentDao;
    public StudentRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        studentDao =  database.studentDao();
    }



    public void insert(Student... students){
        studentDao.insert(students);
    }
    public  void delete(Student student){
        studentDao.delete(student);
    }
    public void update(Student student){
        studentDao.update(student);
    }

    public LiveData<List<Student>> getLiveDataAllStudent() {
        return studentDao.getAllLiveDataStudent();
    }

    public List<Student> getAll(){
        return studentDao.getAll();
    }


}
